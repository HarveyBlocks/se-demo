package com.harvey.se.pojo.dto;

import com.harvey.se.exception.BadRequestException;
import com.harvey.se.exception.ResourceNotFountException;
import com.harvey.se.pojo.entity.User;
import com.harvey.se.pojo.enums.UserRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用于管理端, 信息更详细
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-05 11:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用于管理端, 信息更详细")
public class UserInfoDto {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键, 更新依据")
    private Long id;

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


    public static UserInfoDto adapte(User entity) {
        if (entity == null) {
            throw new ResourceNotFountException("请求不存在的资源");
        }
        return new UserInfoDto(
                entity.getId(),
                entity.getPhone(),
                entity.getNickname(),
                entity.getPoints(),
                entity.getCreateTime(),
                entity.getUpdateTime(),
                entity.getRole()
        );
    }

    public static UserInfoDto adapte(ConsultationContentWithUserEntityDto withConsultationContentDto) {
        if (withConsultationContentDto == null) {
            throw new BadRequestException("请求的参数不存在");
        }
        return new UserInfoDto(
                withConsultationContentDto.getUserId(),
                withConsultationContentDto.getPhone(),
                withConsultationContentDto.getNickname(),
                withConsultationContentDto.getPoints(),
                withConsultationContentDto.getCreateTime(),
                withConsultationContentDto.getUpdateTime(),
                withConsultationContentDto.getRole()
        );
    }
}
