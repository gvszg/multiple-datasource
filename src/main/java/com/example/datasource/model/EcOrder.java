package com.example.datasource.model;

import lombok.*;

import java.math.BigDecimal;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class EcOrder {
    private Long id;
    private Long userId;
    private BigDecimal totalPrice;
    private Long createTime;
    private Long updateTime;
}
