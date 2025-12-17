package com.example.park.domain.service.impl;

import com.example.park.common.PageResult;
import com.example.park.common.PageUtils;
import com.example.park.common.ResultCode;
import com.example.park.common.SystemException;
import com.example.park.domain.converter.StructMapper;
import com.example.park.domain.dto.CarAddRequestDTO;
import com.example.park.domain.dto.CarDeleteRequestDTO;
import com.example.park.domain.dto.CarPageResponseDTO;
import com.example.park.domain.dto.CarQueryRequestDTO;
import com.example.park.domain.dto.CarUpdateRequestDTO;
import com.example.park.domain.dto.CarUpdateResponseDTO;
import com.example.park.domain.dto.DropRequestDTO;
import com.example.park.domain.dto.RideRequestDTO;
import com.example.park.domain.entity.Car;
import com.example.park.domain.entity.CarUsage;
import com.example.park.domain.mapper.CarMapper;
import com.example.park.domain.mapper.CarUsageMapper;
import com.example.park.domain.service.ICarService;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 高明(コウメイ)
 * @since 2025-10-15
 */
@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements ICarService {

    
    private final CarMapper carMapper;

   
    private final CarUsageMapper carUsageMapper;

    
    private final StructMapper structMapper;

    public CarServiceImpl(CarMapper carMapper,CarUsageMapper carUsageMapper,StructMapper structMapper){
        this.carMapper=carMapper;
        this.carUsageMapper=carUsageMapper;
        this.structMapper=structMapper;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @Override
    public void carAdd(CarAddRequestDTO dto) {
        if (dto==null) {
            throw new SystemException(ResultCode.BAD_REQUEST);
        }
        Car car=structMapper.toEntityDTO(dto);
        carMapper.insert(car);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @Override
    public void carDelete(CarDeleteRequestDTO dto) {
        if (dto==null) {
            throw new SystemException(ResultCode.BAD_REQUEST);
        }
        UpdateWrapper<Car> wrapper=new UpdateWrapper<>();
        wrapper.in("id", dto.getIds())
               .set("is_deleted", 1);
        carMapper.update(null, wrapper);
    }

    @Override
    public PageResult<CarPageResponseDTO> pageInfo(CarQueryRequestDTO dto) {
        if(dto==null){
            throw new SystemException(ResultCode.BAD_REQUEST);
        }
        QueryWrapper<Car> wrapper=new QueryWrapper<>();
        if(StringUtils.hasText(dto.getCarNumber())){
            wrapper.like("car_number",dto.getCarNumber());
        }
        wrapper.last("ORDER BY FIELD(status,0,1),car_number ASC");

        Page<Car> page=new Page<>(dto.getPageNum(),dto.getPageSize());
        Page<Car> carPage=carMapper.selectPage(page, wrapper);

        return PageUtils.build(carPage, car -> structMapper.toDTO(car));
    }

    @Override
    @Transactional
    public CarUpdateResponseDTO carUpdate(CarUpdateRequestDTO dto) {
        if (dto==null) {
            throw new SystemException(ResultCode.BAD_REQUEST);
        }
        Car car=carMapper.selectById(dto.getId());
        if(car.getIsDeleted()){
            throw new SystemException(ResultCode.NOT_FOUND);
        }
        structMapper.patchCar(dto, car);

        carMapper.updateById(car);

        CarUpdateResponseDTO responseDTO=structMapper.toUpdateDTO(car);
        return responseDTO;
    }

    @Override
    @Transactional
    public void ride(RideRequestDTO dto,Long userId) {
        if (dto==null) {
            throw new SystemException(ResultCode.BAD_REQUEST);
        }
        
        Car car=carMapper.selectById(dto.getCarId());
        if(car==null || car.getIsDeleted()){
            throw new SystemException(ResultCode.NOT_FOUND);
        }
        //車輌のステータスを更新する// 2. 【核心】使用 UpdateWrapper 进行原子更新（解决并发问题）：
        //    - 确保车辆当前状态是可用 (status = 0)
        UpdateWrapper<Car> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", dto.getCarId())
                .eq("is_deleted", 0)
                .eq("status", 0) // 确保车辆是可用的 (0:可用, 1:占用中)
                .set("current_user_id", userId) // 使用认证的 userId
                .set("status", 1);

        int rowsAffected = carMapper.update(null, updateWrapper);

        // 检查更新结果：如果影响行数为 0，说明车辆已被占用或状态不符合
        if (rowsAffected == 0) {
            // 重新查询车辆，以便提供更准确的错误信息
            Car currentCar = carMapper.selectById(dto.getCarId());
            if (currentCar != null && currentCar.getStatus() == 1) {
                // 车辆已被占用，抛出特定错误
                // 假设 ResultCode 中有 CAR_ALREADY_IN_USE
                throw new SystemException(ResultCode.ILLEGAL_PERMISSION);
            }
            // 其他原因
            throw new SystemException(ResultCode.BAD_REQUEST);
        }
        //履歴表に記入する
        // 履历表加入使用履历 (使用认证的 userId)
        CarUsage carUsage = structMapper.toEntity(dto);
        // 强制使用当前认证的 userId，防止 DTO 篡改
        carUsage.setUserId(userId);
        carUsageMapper.insert(carUsage);
    }

    @Override
    @Transactional
    public void drop(DropRequestDTO dto,Long userId) {
        if (dto==null) {
            throw new SystemException(ResultCode.BAD_REQUEST);
        }
        
        Car car=carMapper.selectById(dto.getCarId());
        if(car==null || car.getIsDeleted()){
            throw new SystemException(ResultCode.NOT_FOUND);
        }
        //車輌のステータスを更新する
        // 修正后的 Drop 权限检查：确保当前用户是车辆的当前使用者
        if(car.getCurrentUserId() == null || !car.getCurrentUserId().equals(userId)){
            throw new SystemException(ResultCode.ILLEGAL_PERMISSION);
        }

        // 1. 原子更新 Car 表：只有当 current_user_id 是当前用户时才更新
        UpdateWrapper<Car> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", dto.getCarId())
                .eq("status", 1) // 确保车辆状态是占用中
                .eq("current_user_id", userId) // 确保当前用户是占用者
                .set("current_user_id", null)
                .set("status", 0);

        int rowsAffected = carMapper.update(null, updateWrapper);
        if (rowsAffected == 0) {
            // 如果更新失败，说明车辆状态已被改变（比如已经被其他人归还或状态不正确）
            throw new SystemException(ResultCode.BAD_REQUEST);
        }
        //履歴表に記入する
        // 2. 更新 Usage 记录
        // 使用认证的 userId 来查询使用记录，而不是 DTO 中的 userId
        CarUsage usage=carUsageMapper.selectOne(
                new QueryWrapper<CarUsage>()
                        .eq("car_id",dto.getCarId())
                        .eq("user_id",userId) // 使用认证的 userId
                        .isNull("drop_time")
                        .orderByDesc("ride_time")
                        .last("LIMIT 1")
        );

        if (usage == null) {
            // 数据不一致，可能 Car 表更新成功了，但 Usage 表没有对应的 ride 记录
            throw new SystemException(ResultCode.SERVER_ERROR);
        }

        // 3. 更新使用记录
        structMapper.patchDrop(dto,usage);
        carUsageMapper.updateById(usage);
    }

}
