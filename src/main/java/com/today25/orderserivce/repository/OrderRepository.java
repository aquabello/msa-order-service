package com.today25.orderserivce.repository;

import com.today25.orderserivce.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    OrderEntity findByOrderId(String userId);
    Iterable<OrderEntity> findByUserId(String userId);
}
