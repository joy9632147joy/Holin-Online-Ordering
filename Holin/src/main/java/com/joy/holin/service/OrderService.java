package com.joy.holin.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joy.holin.dto.OrderDto;
import com.joy.holin.dto.OrderItemDto;
import com.joy.holin.dto.OrderItemRequestDto;
import com.joy.holin.dto.OrderRequestDto;
import com.joy.holin.dto.ProductDto;
import com.joy.holin.entity.Members;
import com.joy.holin.entity.OrderItems;
import com.joy.holin.entity.Orders;
import com.joy.holin.entity.Products;
import com.joy.holin.repo.MemberRepo;
import com.joy.holin.repo.OrderItemsRepo;
import com.joy.holin.repo.OrdersRepo;
import com.joy.holin.repo.ProductsRepo;

@Service
public class OrderService {

    @Autowired
    private OrdersRepo ordersRepo;

    @Autowired
    private OrderItemsRepo orderItemsRepo;

    @Autowired
    private ProductsRepo productsRepo;

    @Autowired
    private MemberRepo membersRepo;

    public OrderDto createOrder(OrderRequestDto orderRequestDto) {
        // 1.查會員
        Members member = membersRepo.findById(orderRequestDto.getMemberId())
                .orElseThrow(() -> new RuntimeException("查無會員"));

        // 2.建立訂單
        Orders order = new Orders();
        order.setMember(member);
        order.setStatus("pending");
        order.setCreatedAt(LocalDateTime.now());

        // 3.計算總價 建立細項
        int totalPrice = 0;
        List<OrderItems> orderItems = new ArrayList<>();
        for (OrderItemRequestDto orderItemDto : orderRequestDto.getOrderItems()) {
            Products product = productsRepo.findById(orderItemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("查無商品"));

            OrderItems item = new OrderItems();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(orderItemDto.getQuantity());
            item.setSnapshotPrice(product.getPrice());

            totalPrice += product.getPrice() * orderItemDto.getQuantity();
            orderItems.add(item);
        }

        order.setTotalPrice(totalPrice);
        order.setOrderItems(orderItems);

        // 4.存檔
        Orders saved = ordersRepo.save(order);

        // 5.回傳
        return convertToDto(saved);

    }

    public List<OrderDto> getOrdersByMember(Long memberId) {
        List<Orders> orders = ordersRepo.findByMember_Id(memberId);
        return orders.stream().map(this::convertToDto).toList();
    }

    private OrderDto convertToDto(Orders order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setMemberId(order.getMember().getId());
        dto.setStatus(order.getStatus());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setCreatedAt(order.getCreatedAt());

        List<OrderItemDto> itemDtos = new ArrayList<>();
        for (OrderItems item : order.getOrderItems()) {
            OrderItemDto itemDto = new OrderItemDto();
            itemDto.setId(item.getId());
            itemDto.setOrderId(item.getOrder().getId());
            itemDto.setProductId(item.getProduct().getId());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setSnapshotPrice(item.getSnapshotPrice());
            itemDtos.add(itemDto);
        }
        dto.setOrderItems(itemDtos);
        return dto;
    }

}
