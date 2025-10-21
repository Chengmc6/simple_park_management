package com.example.park.domain.service;

import com.example.park.domain.dto.UserInfoDTO;
import com.example.park.domain.dto.UserLoginRequestDTO;
import com.example.park.domain.dto.UserLoginResponseDTO;
import com.example.park.domain.dto.UserPasswordChangeDTO;
import com.example.park.domain.dto.UserRegisterDTO;
import com.example.park.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 高明(コウメイ)
 * @since 2025-10-15
 */
public interface IUserService extends IService<User> {
    void register(UserRegisterDTO dto);
    UserLoginResponseDTO login(UserLoginRequestDTO dto);
    UserInfoDTO info(Long userId);
    void changePassword(Long userId,UserPasswordChangeDTO dto);
}
