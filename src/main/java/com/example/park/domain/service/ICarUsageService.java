package com.example.park.domain.service;

import com.example.park.common.PageResult;
import com.example.park.domain.dto.UsageRequestDTO;
import com.example.park.domain.dto.UsageResponseVO;
import com.example.park.domain.entity.CarUsage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 高明(コウメイ)
 * @since 2025-10-15
 */
public interface ICarUsageService extends IService<CarUsage> {
    PageResult<UsageResponseVO> history(UsageRequestDTO dto);
}
