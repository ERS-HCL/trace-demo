package com.tfg.example.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tfg.example.domain.ShoppingCart;

@SpringBootApplication
@RestController
public class CartService {

	private final Logger LOG = LoggerFactory.getLogger(CartService.class);

	@Autowired
	Tracer tracer;
	
	@RequestMapping(method = RequestMethod.POST, value = "/checkout")
	public ShoppingCart checkoutOrder(@RequestBody ShoppingCart cart) throws InterruptedException {
		
		Span newSpan = this.tracer.createSpan("orderCheckout");
		try {
			
			System.out.println(cart);
			LOG.info("order chechout for cart id: {}",cart.getId());
			Map<String, String> params = new HashMap<String, String>();
		    params.put("cartId", Long.toString(cart.getId()));
		    // ...
		 	// You can tag a span
		 	this.tracer.addTag("cartId", Long.toString(cart.getId()));
		    // Place order
			String orderId = restTemplate().getForObject("http://localhost:9001/placeOrder/{cartId}", String.class,params);
			this.tracer.addTag("orderId", orderId);
			newSpan.logEvent("order creation complete");
			cart.setCheckedOut(true);
			cart.setOrderId(orderId);
			List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
			acceptableMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
			// Introduce Sleep
		/*	newSpan.logEvent("Added delay start");
			Thread.sleep(500);
			newSpan.logEvent("Added delay end");*/
			// Save cart state
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(acceptableMediaTypes);
			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
			restTemplate().postForObject("http://localhost:9002/saveCart", cart, ShoppingCart.class);
			
			
			// ...
			// You can log an event on a span
			newSpan.logEvent("cart checkout complete");
		} finally {
			// Once done remember to close the span. This will allow collecting
			// the span to send it to Zipkin
			this.tracer.close(newSpan);
		}
		
		
		return cart;
	}

	/** Sleuth automatically adds trace interceptors when in the classpath */
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(CartService.class,
				// The spring application name is used for the Zipkin service
				// name
				"--spring.application.name='Checkout Service'", "--server.port=9000");
	}
}
