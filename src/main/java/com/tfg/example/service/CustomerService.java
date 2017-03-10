package com.tfg.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.example.domain.ShoppingCart;

@SpringBootApplication
@RestController
public class CustomerService {

	private final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

	@RequestMapping(method = RequestMethod.POST, value = "/saveCart")
	public void saveCart(@RequestBody ShoppingCart cart) throws InterruptedException {
		System.out.println(cart);
		LOG.info("cart state updated : {}",cart.getId());
	}

	public static void main(String[] args) {
		SpringApplication.run(CustomerService.class,
				// The spring application name is used for the Zipkin service
				// name
				"--spring.application.name='Customer Service'", "--server.port=9002");
	}
}
