package com.harvey.se.pojo.dto;

import com.harvey.se.pojo.entity.HotWord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-08 00:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户咨询过程中的热词")
public class HotWordDto {
    @ApiModelProperty(value = "主键, 插入时系统自动分配")
    private Long id;

    @ApiModelProperty(value = "热词")
    private String word;

    @ApiModelProperty(value = "频率")
    private Integer frequency;

    public HotWordDto(HotWord entity) {
        this(entity.getId(), entity.getWord(), entity.getFrequency());
    }

}
