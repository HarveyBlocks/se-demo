package com.harvey.se.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harvey.se.dao.ConsultationContentMapper;
import com.harvey.se.pojo.entity.ConsultationContent;
import com.harvey.se.service.ConsultationContentService;
import org.springframework.stereotype.Service;


/**
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-08 06:55
 * @see ConsultationContent
 * @see ConsultationContentMapper
 * @see ConsultationContentService
 */
@Service
public class ConsultationContentServiceImpl extends
        ServiceImpl<ConsultationContentMapper, ConsultationContent> implements ConsultationContentService {

}