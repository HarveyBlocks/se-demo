package com.harvey.se.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * TODO
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-09 14:26
 */
@Data
@Component
@ConfigurationProperties(prefix = "h-se.chat")
public class ChatProperties {
    private String apiKey;
    private List<String> appId;
}
