package com.tfg.example.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class OrderService {

	private final Logger LOG = LoggerFactory.getLogger(OrderService.class);

	public static void main(String[] args) {
		SpringApplication.run(OrderService.class,
				// The spring application name is used for the Zipkin service
				// name
				"--spring.application.name='Order Service'", "--server.port=9001");
	}

	@RequestMapping("/placeOrder/{cartId}")
	public String placeOrder(@PathVariable("cartId") String cartId) {
		LOG.info("placing order for cartID: {}",cartId);
		UUID orderId = UUID.randomUUID();
		return orderId.toString();
	}
}
