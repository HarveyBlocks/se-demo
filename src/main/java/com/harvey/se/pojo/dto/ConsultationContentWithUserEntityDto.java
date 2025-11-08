package com.harvey.se.pojo.dto;

import com.harvey.se.pojo.entity.ConsultationContent;
import com.harvey.se.pojo.entity.User;
import com.harvey.se.pojo.enums.UserRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 用户对车的咨询和用户本身的信息
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-08 04:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户的信息及其咨询信息")
public class ConsultationContentWithUserEntityDto {
    @ApiModelProperty(value = "持有这个咨询信息的用户, 系统自动分配, 但其为null时表示此信息为默认信息")
    private Long userId;
    /**
     * 手机号码
     */
    @ApiModelProperty(value = "用户电话号码")
    private String phone;
    /**
     * 昵称，默认是随机字符
     */
    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户积分")
    private Integer points;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "记录创建时间, 由系统决定")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "记录更新时间, 由系统决定")
    private LocalDateTime updateTime;
    /**
     * 角色,权限
     */
    @ApiModelProperty(value = "用户权限", example = "0为root, 1为普通用户, 2为被拉入黑名单的用户, 3为vip(暂无)")
    private UserRole role;
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


    public static ConsultationContentWithUserEntityDto combine(
            ConsultationContent consultationContent, User user) {
        return Objects.equals(consultationContent.getUserId(), user.getId()) ? new ConsultationContentWithUserEntityDto(
                user.getId(),
                user.getPhone(),
                user.getNickname(),
                user.getPoints(),
                user.getCreateTime(),
                user.getUpdateTime(),
                user.getRole(),
                consultationContent.getLowerBound(),
                consultationContent.getUpperBound(),
                consultationContent.getPreferredCarModel(),
                consultationContent.getMainUseCase(),
                consultationContent.getPreferredFuelType(),
                consultationContent.getPreferredBrand(),
                consultationContent.getOtherRequirements()
        ) : null;
    }

    public static ConsultationContentWithUserEntityDto combine(
            ConsultationContentDto consultationContent, UserInfoDto user) {
        return Objects.equals(consultationContent.getUserId(), user.getId()) ? new ConsultationContentWithUserEntityDto(
                user.getId(),
                user.getPhone(),
                user.getNickname(),
                user.getPoints(),
                user.getCreateTime(),
                user.getUpdateTime(),
                user.getRole(),
                consultationContent.getLowerBound(),
                consultationContent.getUpperBound(),
                consultationContent.getPreferredCarModel(),
                consultationContent.getMainUseCase(),
                consultationContent.getPreferredFuelType(),
                consultationContent.getPreferredBrand(),
                consultationContent.getOtherRequirements()
        ) : null;
    }
}
