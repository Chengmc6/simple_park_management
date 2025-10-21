package com.example.park.common;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class PageUtils {

    public static <T,R> PageResult<R> build(Page<T> page,Function<T,R> mapper){
        List<R> dtoList=page.getRecords().stream()
                            .map(mapper)
                            .collect(Collectors.toList());
        return new PageResult<>(
                page.getTotal(),
                page.getCurrent(),
                page.getSize(),
                page.getPages(),
                dtoList
        );
    }
}
