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
 * 
 * </p>
 *
 * @author 高明(コウメイ)
 * @since 2025-10-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ユーザーID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * ユーザー名
     */
    private String username;

    /**
     * パスワード
     */
    private String password;

    /**
     * ロール（0=普通ユーザー，1=管理員）
     */
    private Integer role;

    /**
     * 削除標識（0=未削除，1=削除した）
     */
    private Boolean isDeleted;

    /**
     * 生成日時
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;


}
