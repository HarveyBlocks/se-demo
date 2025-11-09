package com.harvey.se.service;

import com.harvey.se.pojo.dto.ChatTextPiece;

import java.util.List;

/**
 * TODO
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-09 02:29
 */
public interface ChatService {

    List<ChatTextPiece> pullPieces(Long chatId, Integer limit);


    Long chat(Long userId, String message, int chatIndex) throws InterruptedException;
}
