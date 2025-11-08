package com.harvey.se.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.harvey.se.pojo.dto.GiftDto;
import com.harvey.se.pojo.dto.GiftInfoDto;
import com.harvey.se.pojo.dto.UserDto;
import com.harvey.se.pojo.entity.Gift;
import com.harvey.se.pojo.vo.IntRange;

import java.util.List;

/**
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-08 06:51
 */
public interface GiftService extends IService<Gift> {
    void insert(GiftInfoDto giftInfoDto);

    void delete(Long id);

    void update(GiftInfoDto giftInfoDto);

    void consume(UserDto user, Long giftId);

    GiftInfoDto queryDetail(Long id);

    List<GiftDto> queryByCost(IntRange intRange, Page<Gift> page);

    List<GiftDto> queryByPage(Page<Gift> page);
}
