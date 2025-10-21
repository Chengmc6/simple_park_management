package com.example.park.domain.service.impl;

import com.example.park.common.CustomerUserDetails;
import com.example.park.common.JwtUtils;
import com.example.park.common.ResultCode;
import com.example.park.common.SystemException;
import com.example.park.domain.dto.UserInfoDTO;
import com.example.park.domain.dto.UserLoginRequestDTO;
import com.example.park.domain.dto.UserLoginResponseDTO;
import com.example.park.domain.dto.UserPasswordChangeDTO;
import com.example.park.domain.dto.UserRegisterDTO;
import com.example.park.domain.entity.User;
import com.example.park.domain.mapper.StructMapper;
import com.example.park.domain.mapper.UserMapper;
import com.example.park.domain.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StructMapper structMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void register(UserRegisterDTO dto) {

        if(dto==null){
            throw new SystemException(ResultCode.SERVER_ERROR);
        }

        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("username", dto.getUsername());
        if(userMapper.selectCount(wrapper)>0){
            throw new SystemException(ResultCode.USERNAME_EXISTS);
        }
        //パスワードを暗号化する
        String encodePwd=passwordEncoder.encode(dto.getPassword());
        User user=new User();
        user.setUsername(dto.getUsername());
        user.setPassword(encodePwd);
        user.setRole(0);
        user.setIsDeleted(false);

        userMapper.insert(user);
    }

    @Override
    public UserLoginResponseDTO login(UserLoginRequestDTO dto) {

        if(dto==null){
            throw new SystemException(ResultCode.SERVER_ERROR);
        }
        //ユーザーデータの認証
        UsernamePasswordAuthenticationToken authenticationToken=
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        
        Authentication authentication=authenticationManager.authenticate(authenticationToken);
        //認証成功したら、認証対象からユーザーデータを取る
        CustomerUserDetails userDetails=(CustomerUserDetails)authentication.getPrincipal();
        //tokenを生成する
        String token=JwtUtils.getToken(userDetails.getId(), userDetails.getUsername(),userDetails.getRole());

        return new UserLoginResponseDTO(userDetails.getId(),userDetails.getUsername(),token);
    }

    @Override
    public UserInfoDTO info(Long userId) {
        User user=userMapper.selectById(userId);
        if(user==null){
            throw new SystemException(ResultCode.USER_NOT_FOUND);
        }
        UserInfoDTO dto=structMapper.toInfoDTO(user);
        return dto;
    }

    @Override
    public void changePassword(Long userId,UserPasswordChangeDTO dto) {
        if (dto==null || userId==null) {
            throw new SystemException(ResultCode.SERVER_ERROR);
        }

        User user=userMapper.selectById(userId);
        if(user==null){
            throw new SystemException(ResultCode.USER_NOT_FOUND);
        }

        if(!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())){
            throw new SystemException(ResultCode.PASSWORD_ERROR);
        }

        if(passwordEncoder.matches(dto.getNewPassword(), user.getPassword())){
            throw new SystemException(ResultCode.PASSWORD_SAME);
        }
        //新しいパスワードを暗号化する
        String newEnPassword=passwordEncoder.encode(dto.getNewPassword());
        User updatUser=new User();
        updatUser.setPassword(newEnPassword);
        updatUser.setId(userId);
        userMapper.updateById(updatUser);
    }

}
