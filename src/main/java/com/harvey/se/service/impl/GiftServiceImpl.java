package com.harvey.se.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harvey.se.dao.GiftMapper;
import com.harvey.se.pojo.entity.Gift;
import com.harvey.se.service.GiftService;
import org.springframework.stereotype.Service;


/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-08 06:55
 * @see Gift
 * @see GiftMapper
 * @see GiftService
 */
@Service
public class GiftServiceImpl extends ServiceImpl<GiftMapper, Gift> implements GiftService {

}