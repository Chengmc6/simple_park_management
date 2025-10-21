package com.example.park.domain.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 車輌履歴表
 * </p>
 *
 * @author 高明(コウメイ)
 * @since 2025-10-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CarUsage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 使用記録ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 車輌ID
     */
    private Long carId;

    /**
     * 使用者ID
     */
    private Long userId;

    /**
     * 乗車時間
     */
    private LocalDateTime rideTime;

    /**
     * 乗車時のアルコール度数
     */
    private BigDecimal rideAlcoholLevel;

    /**
     * 降車時間（未降車の時はNULL）
     */
    private LocalDateTime dropTime;

    /**
     * 降車時のアルコール度数（未降車の時はNULL）
     */
    private BigDecimal dropAlcoholLevel;


}
