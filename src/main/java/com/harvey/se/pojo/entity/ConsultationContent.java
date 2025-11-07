package com.harvey.se.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.harvey.se.pojo.dto.ConsultationContentDto;
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
@TableName("`tb_consultation_content`")
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationContent {
    @TableField("user_id")
    @TableId(type = IdType.INPUT)
    private Long userId;
    @TableField("lower_bound")
    private Integer lowerBound;
    @TableField("upper_bound")
    private Integer upperBound;
    @TableField("preferred_car_model")
    private String preferredCarModel;
    @TableField("main_use_case")
    private String mainUseCase;
    @TableField("preferred_fuel_type")
    private String preferredFuelType;
    @TableField("preferred_brand")
    private String preferredBrand;
    @TableField("other_requirements")
    private String otherRequirements;


    public ConsultationContent(ConsultationContentDto dto) {
        this(
                dto.getUserId(),
                dto.getLowerBound(),
                dto.getUpperBound(),
                dto.getPreferredCarModel(),
                dto.getMainUseCase(),
                dto.getPreferredFuelType(),
                dto.getPreferredBrand(),
                dto.getOtherRequirements()
        );
    }


}
