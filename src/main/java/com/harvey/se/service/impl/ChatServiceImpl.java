package com.harvey.se.service.impl;

import com.alibaba.dashscope.app.Application;
import com.alibaba.dashscope.app.ApplicationParam;
import com.alibaba.dashscope.app.ApplicationResult;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.harvey.se.exception.BadRequestException;
import com.harvey.se.pojo.dto.ChatTextPiece;
import com.harvey.se.properties.ChatProperties;
import com.harvey.se.service.ChatService;
import com.harvey.se.util.JacksonUtil;
import com.harvey.se.util.RedisConstants;
import com.harvey.se.util.RedisIdWorker;
import io.reactivex.Flowable;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-09 02:29
 */
@Service
@Slf4j
public class ChatServiceImpl implements ChatService {
    private final ExecutorService executor = Executors.newCachedThreadPool(task -> new Thread(task, "chat-thread"));
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisIdWorker redisIdWorker;
    @Resource
    private JacksonUtil jacksonUtil;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private ChatProperties chatProperties;

    @Override
    public Long chat(Long userId, String message, int chatIndex) {
        if (chatIndex >= chatProperties.getAppId().size()) {
            throw new BadRequestException("Error of chat index, not exist!");
        }
        RLock lock = redissonClient.getLock(RedisConstants.Chat.LOCK_KEY + userId);
        Long chatId = createChatId();
        executor.execute(() -> {
            boolean isLock;
            try {
                isLock = lock.tryLock(-1L, -1L, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (!isLock) {
                push2Redis(chatId, ChatTextPiece.ofQuestionWhileGenerating("不允许重复提问"));
                return;
            }
            try {
                executeChat(chatId, message, chatIndex);
            } catch (NoApiKeyException | InputRequiredException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        });
        return chatId;
    }

    private void executeChat(Long chatId, String message, int appIndex)
            throws NoApiKeyException, InputRequiredException {
        Flowable<ApplicationResult> result = flowChat(message, appIndex);
        AtomicInteger pieceIdGenerator = new AtomicInteger(0);
        try {
            log.info("Starting chat: {}", chatId);
            result.blockingForEach(data -> {
                String textPiece = data.getOutput().getText();
                if (textPiece == null || textPiece.isEmpty()) {
                    return; // 忽略
                }
                int pieceId = pieceIdGenerator.incrementAndGet();
                push2Redis(chatId, new ChatTextPiece(pieceId, textPiece));
            });
        } catch (Exception error) {
            push2Redis(chatId, ChatTextPiece.ofServiceError("... ERROR[服务端发生异常!]"));
            log.error("error on chat " + chatId, error);
            throw error;
        }
        push2Redis(chatId, ChatTextPiece.ofSuccessfullyFinished("文本正常结束"));
        log.info("chat {} is completed, total chunks: {}", chatId, pieceIdGenerator.get());

    }

    public Flowable<ApplicationResult> flowChat(String message, int appIndex)
            throws NoApiKeyException, InputRequiredException {
        ApplicationParam param = ApplicationParam.builder()
                .apiKey(chatProperties.getApiKey())
                .appId(chatProperties.getAppId().get(appIndex)) // ???
                .prompt(message)
                .incrementalOutput(true)
                .build();
        Application application = new Application();
        return application.streamCall(param);
    }


    public long createChatId() {
        return redisIdWorker.nextId(RedisConstants.Chat.ID_GENERATOR);
    }

    public void push2Redis(Long chatId, ChatTextPiece text) {
        String key = RedisConstants.Chat.PIECE_QUEUE_KEY + chatId;
        Long nowSize = stringRedisTemplate.opsForList().rightPush(key, jacksonUtil.toJsonStr(text));
        log.info("now size of {} is {}", chatId, nowSize);
    }

    /**
     * @param limit 一般情况下result.size == limit, 数据库中不足时尽可能返回
     */
    @Override
    public List<ChatTextPiece> pullPieces(Long chatId, Integer limit) {
        limit = limit == null ? 20 : Math.min(20, limit);
        if (limit <= 0) {
            return List.of();
        }
        String key = RedisConstants.Chat.PIECE_QUEUE_KEY + chatId;
        //noinspection rawtypes
        DefaultRedisScript<List> script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource(RedisConstants.Chat.MULTIPLY_LEFT_POP_LUA));
        script.setResultType(List.class);
        //noinspection unchecked
        List<String> range = (List<String>) stringRedisTemplate.execute(
                script,
                List.of()/*不要传null*/,
                key,
                String.valueOf(limit)
        );
        if (range == null) {
            return List.of();
        }
        List<ChatTextPiece> pieces = range.stream()
                .map(s -> jacksonUtil.toBean(s, ChatTextPiece.class))
                .collect(Collectors.toList());
        if (pieces.isEmpty()) {
            return pieces;
        }
        if (pieces.get(pieces.size() - 1).endSign()) {
            stringRedisTemplate.delete(key);
        }
        return pieces;
    }

}
