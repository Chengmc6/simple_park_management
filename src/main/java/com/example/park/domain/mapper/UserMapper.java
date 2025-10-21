package com.example.park.domain.mapper;

import com.example.park.domain.dto.SimpleUserDTO;
import com.example.park.domain.entity.User;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 高明(コウメイ)
 * @since 2025-10-15
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM user WHERE username=#{username} and is_deleted=0")
    User selectByUsername(@Param("username") String username);
    ////ユーザー名を取得
    List<SimpleUserDTO> selectByIds(@Param("list") List<Long> ids);
}
