package com.example.park.advice;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

public class ObjectFieldHandle implements MetaObjectHandler{

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject,"createdAt",LocalDateTime.class,LocalDateTime.now());
        this.strictInsertFill(metaObject,"updatedAt",LocalDateTime.class,LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject,"updatedAt",LocalDateTime.class,LocalDateTime.now());
    }
    
}
