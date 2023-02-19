package com.today25.orderserivce;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderServiceController {

    Environment env;

    @Autowired
    public OrderServiceController(Environment env) {
        this.env = env;
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

    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        log.info("Server post={}", request.getServerPort());

        return String.format("Hi there this is Order Check: Server Post -> %s"
                , env.getProperty("local.server.port"));
    }
}
