package com.joy.holin.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderRequestDto {
    private Long memberId;
    private List<OrderItemRequestDto> orderItems;
}
