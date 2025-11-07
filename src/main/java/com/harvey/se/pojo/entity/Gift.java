package com.harvey.se.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.harvey.se.pojo.dto.GiftInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * TODO 咨询内容
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-08 00:45
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`tb_gift`")
@AllArgsConstructor
@NoArgsConstructor
public class Gift {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Integer cost;
    private String title;
    private String description;
    private Integer storage;
    @TableField("picture_url1")
    private String pictureUrl1;
    @TableField("picture_url2")
    private String pictureUrl2;
    @TableField("picture_url3")
    private String pictureUrl3;

    public Gift(GiftInfoDto dto) {
        this(
                dto.getId(),
                dto.getCost(),
                dto.getTitle(),
                dto.getDescription(),
                dto.getStorage(),
                dto.getPictureUrl1(),
                dto.getPictureUrl2(),
                dto.getPictureUrl3()
        );
    }
}
