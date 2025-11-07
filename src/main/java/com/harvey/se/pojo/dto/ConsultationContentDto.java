package com.harvey.se.pojo.dto;

import com.harvey.se.pojo.entity.ConsultationContent;
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
 * @date 2025-11-08 00:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户的咨询信息")
public class ConsultationContentDto {
    public static final ConsultationContentDto DEFAULT =
            new ConsultationContentDto(null, 0, null, "任意", "任意", "任意", "任意", "无");

    @ApiModelProperty(value = "持有这个咨询信息的用户, 系统自动分配, 但其为null时表示此信息为默认信息")
    private Long userId;

    @ApiModelProperty(value = "购车预算下限, 默认0, 单位元(因为考虑车的价值, 精细到分没必要)")
    private Integer lowerBound;

    @ApiModelProperty(value = "购车预算上限, 默认无限,单位元(因为考虑车的价值, 精细到分没必要)")
    private Integer upperBound;

    @ApiModelProperty(value = "偏好车型, 默认(任意)", example = "任意, SUV、轿车、MPV等")
    private String preferredCarModel;

    @ApiModelProperty(value = "主要使用场景, 默认(任意)", example = "任意, 通勤、家庭、商务等")
    private String mainUseCase;

    @ApiModelProperty(value = "燃料类型偏好, 默认(任意)", example = "任意, 燃油, 电动, 混动等")
    private String preferredFuelType;

    @ApiModelProperty(value = "品牌偏好, 默认(任意)")
    private String preferredBrand;

    @ApiModelProperty(value = "其他要求, 默认(无)")
    private String otherRequirements;

    public ConsultationContentDto(ConsultationContentWithUserEntityDto withUserDto) {
        this(
                withUserDto.getUserId(),
                withUserDto.getLowerBound(),
                withUserDto.getUpperBound(),
                withUserDto.getPreferredCarModel(),
                withUserDto.getMainUseCase(),
                withUserDto.getPreferredFuelType(),
                withUserDto.getPreferredBrand(),
                withUserDto.getOtherRequirements()
        );
    }

    public ConsultationContentDto(ConsultationContent entity) {
        this(
                entity.getUserId(),
                entity.getLowerBound(),
                entity.getUpperBound(),
                entity.getPreferredCarModel(),
                entity.getMainUseCase(),
                entity.getPreferredFuelType(),
                entity.getPreferredBrand(),
                entity.getOtherRequirements()
        );
    }
}
