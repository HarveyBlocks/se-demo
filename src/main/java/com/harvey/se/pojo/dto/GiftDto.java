package com.harvey.se.pojo.dto;

import com.harvey.se.pojo.entity.Gift;
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
 * @date 2025-11-08 01:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "礼品粗略信息")
public class GiftDto {
    @ApiModelProperty(value = "主键, 插入时系统自动分配")
    private Long id;
    @ApiModelProperty(value = "花费积分")
    private Integer cost;
    @ApiModelProperty(value = "主题, 主要描述, 简短描述")
    private String title;
    @ApiModelProperty(value = "库存, 如果库存为0, 请显示(\"售罄\")")
    private Integer storage;
    @ApiModelProperty(value = "用于概略图")
    private String pictureUrl;

    public GiftDto(Gift entity) {
        this(
                entity.getId(),
                entity.getCost(),
                entity.getTitle(),
                entity.getStorage(),
                entity.getPictureUrl1()
        );
    }
}
