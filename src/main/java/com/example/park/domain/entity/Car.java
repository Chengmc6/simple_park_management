package com.example.park.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 車輌表
 * </p>
 *
 * @author 高明(コウメイ)
 * @since 2025-10-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 車輌ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 車輌番号
     */
    private String carNumber;

    /**
     * 車輌ステータス（0=未使用，1=乗車中）
     */
    private Integer status;

    /**
     * 今使用者ID（空値できる）
     */
    private Long currentUserId;

    /**
     * 削除標識（0=未削除，1=削除した）
     */
    private Boolean isDeleted;

    /**
     * 車輌更新時間
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
