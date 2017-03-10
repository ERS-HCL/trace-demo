package com.tfg.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tfg.example.domain.LineItem;
import com.tfg.example.domain.ShoppingCart;

@SpringBootApplication
@RestController
@CrossOrigin // So that javascript clients can originate the trace
public class TraceDemoApplication {

	private final Logger LOG = LoggerFactory.getLogger(TraceDemoApplication.class);

	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@RequestMapping("/")
	public String home() {
		LOG.info("you called home");
		return "Hello World";
	}

	@RequestMapping("/cart-checkout")
	public ShoppingCart checkoutOrder() {
		LOG.info("you called home");
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(acceptableMediaTypes);
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<ShoppingCart> cart = new HttpEntity<>((new ShoppingCart(1, Arrays.asList(new LineItem(1, "abc")))),
				headers);
		ShoppingCart response = restTemplate.postForObject("http://localhost:9000/checkout", cart, ShoppingCart.class);
		return response;
	}

	/*
	 * @Bean public AlwaysSampler defaultSampler() { return new AlwaysSampler();
	 * }
	 */
	// PercentageBasedSampler that samples a fixed fraction of spans.

	public static void main(String[] args) {
		SpringApplication.run(TraceDemoApplication.class,
				// The spring application name is used for the Zipkin service
				// name.
				"--spring.application.name='TraceDemo Frontend'",
				// All incoming requests will be sampled. That decision is
				// honored downstream
				"--spring.sleuth.sampler.percentage=1.0", "--server.port=8080");
	}
}
