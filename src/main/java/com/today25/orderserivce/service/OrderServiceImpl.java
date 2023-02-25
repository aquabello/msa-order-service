package com.today25.orderserivce.service;

import com.netflix.discovery.converters.Auto;
import com.today25.orderserivce.dto.OrderDto;
import com.today25.orderserivce.entity.OrderEntity;
import com.today25.orderserivce.repository.OrderRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDetails) {
        orderDetails.setOrderId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrderEntity orderEntity = mapper.map(orderDetails, OrderEntity.class);
        orderEntity.setTotalPrice(orderDetails.getUnitPrice() * orderDetails.getQty());

        OrderEntity savedOrder = orderRepository.save(orderEntity);

        return mapper.map(savedOrder, OrderDto.class);
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {

        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);

        return new ModelMapper().map(orderEntity, OrderDto.class);
    }

    @Override
    public Iterable<OrderEntity> getOrdersByUserId(String userId) {

        Iterable<OrderEntity> orders = orderRepository.findByUserId(userId);
        List<OrderEntity> results = new ArrayList<>();
        orders.forEach( v-> results.add(new ModelMapper().map(v, OrderEntity.class)));

        return results;
    }
}
