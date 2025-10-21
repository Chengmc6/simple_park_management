package com.example.park.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<R> {
    private Long total;
    private Long pageNum;
    private Long pageSize;
    private Long totalPages;
    private List<R> records;
}
