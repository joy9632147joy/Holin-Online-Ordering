package com.joy.holin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joy.holin.dto.OrderDto;
import com.joy.holin.dto.OrderRequestDto;
import com.joy.holin.service.OrderService;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDto requestDto) {

        try {
            OrderDto order = orderService.createOrder(requestDto);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<?> getOrdersByMember(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(orderService.getOrdersByMember(memberId));
    }

}
