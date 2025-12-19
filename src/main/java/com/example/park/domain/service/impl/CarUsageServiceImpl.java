package com.example.park.domain.service.impl;

import com.example.park.common.PageResult;
import com.example.park.common.PageUtils;
import com.example.park.common.ResultCode;
import com.example.park.common.SystemException;
import com.example.park.domain.dto.UsageRequestDTO;
import com.example.park.domain.dto.UsageResponseVO;
import com.example.park.domain.entity.CarUsage;
import com.example.park.domain.mapper.CarUsageMapper;
import com.example.park.domain.service.ICarUsageService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 高明(コウメイ)
 * @since 2025-10-15
 */
@Service
public class CarUsageServiceImpl extends ServiceImpl<CarUsageMapper, CarUsage> implements ICarUsageService {

    
    private final CarUsageMapper carUsageMapper;

    public CarUsageServiceImpl(CarUsageMapper carUsageMapper){
        this.carUsageMapper=carUsageMapper;
    }
    /**
     * 查询指定车辆的使用履历（分页）
     * 指定された車両の使用履歴をページングして取得する
     */
    @Override
    public PageResult<UsageResponseVO> history(UsageRequestDTO dto) {
        
        if(dto.getCarId() == null){
            throw new SystemException(ResultCode.NOT_FOUND);
        }
        // 1. 构造分页对象（页码 + 每页条数）
        // ページングオブジェクトを作成（ページ番号＋ページサイズ）
        Page<UsageResponseVO> page=new Page<>(dto.getPageNum(),dto.getPageSize());

        Page<UsageResponseVO> resultPage=carUsageMapper.selectUsageWithUser(page, dto.getCarId());

        return PageUtils.build(resultPage,vo->vo);
    }

}
