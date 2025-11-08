package com.harvey.se.service.impl;

import com.harvey.se.pojo.entity.Feedback;
import com.harvey.se.service.FeedbackService;
import com.harvey.se.util.ConstantsInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class FeedbackServiceImplTest {
    @Resource
    private FeedbackService feedbackService;

    @Test
    void saveNew() {
        feedbackService.saveNew(new Feedback(
                null,
                1985757499214168065L,
                "这里要添加一份测试",
                ConstantsInitializer.nowDateTime(),
                false
        ));
    }
}