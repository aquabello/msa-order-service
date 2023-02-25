package com.today25.orderserivce.controller;

import com.today25.orderserivce.dto.OrderDto;
import com.today25.orderserivce.entity.OrderEntity;
import com.today25.orderserivce.service.OrderService;
import com.today25.orderserivce.vo.RequestOrder;
import com.today25.orderserivce.vo.ResponseOrder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order-service")
public class OrderServiceController {

    private Environment env;
    private OrderService orderService;

    @Autowired
    public OrderServiceController(Environment env, OrderService orderService) {
        this.env = env;
        this.orderService = orderService;
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the OrderService";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("order-request") String header) {
      log.info(header);
      return "called OrderService message";
    }

    @GetMapping("/health_check")
    public String check(HttpServletRequest request) {
        log.info("Server post={}", request.getServerPort());

        return String.format("Hi there this is Order Check: Server Post -> %s"
                , env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId,  @RequestBody RequestOrder requestOrder) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = mapper.map(requestOrder, OrderDto.class);
        orderDto.setUserId(userId);

        OrderDto createdOrder = orderService.createOrder(orderDto);

        ResponseOrder responseOrder = mapper.map(createdOrder, ResponseOrder.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable("userId") String userId) {
        Iterable<OrderEntity> orders = orderService.getOrdersByUserId(userId);
        List<ResponseOrder> results = new ArrayList<>();
        orders.forEach( v -> results.add(new ModelMapper().map( v, ResponseOrder.class)));

        return ResponseEntity.status(HttpStatus.OK).body(results);
    }
}
