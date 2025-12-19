package com.example.park.domain.mapper;

import com.example.park.domain.dto.UsageResponseVO;
import com.example.park.domain.entity.CarUsage;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 高明(コウメイ)
 * @since 2025-10-15
 */
public interface CarUsageMapper extends BaseMapper<CarUsage> {
    Page<UsageResponseVO> selectUsageWithUser(Page<?> page,@Param("carId") Long carId);
}
