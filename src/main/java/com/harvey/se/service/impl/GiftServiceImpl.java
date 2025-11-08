package com.harvey.se.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harvey.se.dao.GiftMapper;
import com.harvey.se.exception.BadRequestException;
import com.harvey.se.pojo.dto.GiftDto;
import com.harvey.se.pojo.dto.GiftInfoDto;
import com.harvey.se.pojo.dto.UserDto;
import com.harvey.se.pojo.entity.Gift;
import com.harvey.se.pojo.vo.IntRange;
import com.harvey.se.service.GiftService;
import com.harvey.se.service.ServiceUtil;
import com.harvey.se.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-08 06:55
 * @see Gift
 * @see GiftMapper
 * @see GiftService
 */
@Slf4j
@Service
public class GiftServiceImpl extends ServiceImpl<GiftMapper, Gift> implements GiftService {

    @Override
    public void insert(GiftInfoDto giftInfoDto) {
        Gift gift = new Gift(giftInfoDto);
        gift.setId(null);
        int insert = baseMapper.insert(gift);
        if (insert <= 0) {
            log.warn("插入 {} 失败", gift);
        }
    }

    @Override
    public void delete(Long id) {
        boolean removed = super.removeById(id);
        if (!removed) {
            log.warn("删除gift {} 失败", id);
        }
    }

    @Override
    public void update(GiftInfoDto giftInfoDto) {
        Gift gift = new Gift(giftInfoDto);
        boolean updated = super.updateById(gift);
        if (!updated) {
            log.warn("更新 {} 失败", gift);
        }
    }

    @Resource
    private UserService userService;

    @Override
    public void consume(UserDto user, Long giftId) {
        // 1. 查询花费
        Gift gift = new LambdaQueryChainWrapper<>(baseMapper).select(Gift::getCost, Gift::getStorage)
                .eq(Gift::getId, giftId)
                .one();
        if (gift == null) {
            throw new BadRequestException("do not exist gift: " + giftId);
        }
        if (gift.getStorage() < 1) {
            throw new BadRequestException("商品已售罄");
        }
        // 2. 用户point减少
        userService.increasePoint(user.getId(), user.getPoints(), -gift.getCost());
        // 3. 使货物减少
        boolean updated = new LambdaUpdateChainWrapper<>(baseMapper).set(Gift::getStorage, gift.getStorage() - 1)
                .eq(Gift::getId, giftId)
                .update();
        if (!updated) {
            log.warn("货物减少失败");
        }
    }

    @Override
    public GiftInfoDto queryDetail(Long id) {
        return GiftInfoDto.adapte(getById(id));
    }

    @Override
    public List<GiftDto> queryByCost(IntRange intRange, Page<Gift> page) {
        return ServiceUtil.queryAndOrderWithInteger(
                        new LambdaQueryChainWrapper<>(baseMapper),
                        Gift::getCost,
                        intRange,
                        page
                )
                .stream()
                .map(GiftDto::adapte)
                .collect(Collectors.toList());
    }

    @Override
    public List<GiftDto> queryByPage(Page<Gift> page) {
        return new LambdaQueryChainWrapper<>(baseMapper).page(page)
                .getRecords()
                .stream()
                .map(GiftDto::adapte)
                .collect(Collectors.toList());
    }
}