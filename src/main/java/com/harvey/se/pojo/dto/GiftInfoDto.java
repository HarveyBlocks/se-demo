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
 * @date 2025-11-08 01:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "礼品详细信息")
public class GiftInfoDto {
    @ApiModelProperty(value = "主键, 插入时系统自动分配")
    private Long id;
    @ApiModelProperty(value = "花费积分", required = true)
    private Integer cost;
    @ApiModelProperty(value = "主题, 主要描述, 简短描述", required = true)
    private String title;
    @ApiModelProperty(value = "详细描述", required = true)
    private String description;
    @ApiModelProperty(value = "库存, 如果库存为0, 请显示(\"售罄\")", required = true)
    private Integer storage;
    @ApiModelProperty(value = "用于详情图片的URL1", required = true)
    private String pictureUrl1;
    @ApiModelProperty(value = "用于详情图片的URL2", required = true)
    private String pictureUrl2;
    @ApiModelProperty(value = "用于详情图片的URL3", required = true)
    private String pictureUrl3;

    public GiftInfoDto(Gift entity) {
        this(
                entity.getId(),
                entity.getCost(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getStorage(),
                entity.getPictureUrl1(),
                entity.getPictureUrl2(),
                entity.getPictureUrl3()
        );
    }
}

