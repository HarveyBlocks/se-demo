package com.harvey.se.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.harvey.se.pojo.dto.HotWordDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * TODO 热词
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-11-08 00:48
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`tb_hot_word`")
@AllArgsConstructor
@NoArgsConstructor
public class HotWord {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String word;

    private Integer frequency;

    public HotWord(HotWordDto dto) {
        this(dto.getId(), dto.getWord(), dto.getFrequency());
    }
}
