package com.example.park.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.example.park.advice.ObjectFieldHandle;

@Configuration
public class SystemConfig {

    //パスワード暗号化と検証
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //ユーザーログイン検証規則を設定する
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
            .csrf(csrf -> csrf.disable())//JWTには、cookieが必要ではないので、CSRF保護は禁じることができる
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//JWTつかうので、sessionも必要ではない
            .authorizeHttpRequests(auth -> auth.
                requestMatchers("/user/login","/user/register").permitAll()//ユーザーはログインページと新規ページがアクセスできるように設定する
                .anyRequest().authenticated()
            )
            .addFilterBefore(null, null)
            .build();
    }

    //ユーザーログインリクエスト管理
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    public MetaObjectHandler metaObjectHandler(){
        return new ObjectFieldHandle();
    }
}
