package com.springassignment.orderservice.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDto {
    private List<Long> bookId;
    private BigDecimal orderAmount;
}


