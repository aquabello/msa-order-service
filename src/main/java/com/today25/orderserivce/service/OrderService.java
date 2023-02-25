package com.today25.orderserivce.service;

import com.today25.orderserivce.dto.OrderDto;
import com.today25.orderserivce.entity.OrderEntity;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDetails);
    OrderDto getOrderByOrderId(String orderId);
    Iterable<OrderEntity> getOrdersByUserId(String userId);
}
