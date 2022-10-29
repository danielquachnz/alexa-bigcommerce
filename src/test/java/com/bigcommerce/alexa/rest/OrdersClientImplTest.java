package com.bigcommerce.alexa.rest;


import com.bigcommerce.alexa.config.BigCommerceConfig;
import com.bigcommerce.alexa.model.BillingAddressRequest;
import com.bigcommerce.alexa.model.Order;
import com.bigcommerce.alexa.model.OrderRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrdersClientImplTest {
	@Mock
	private RestTemplate restTemplate;
	@Mock
	private BigCommerceConfig bigCommerceConfig;

	@InjectMocks
	private OrdersClientImpl ordersClient;

	@Mock
	private ResponseEntity<Order[]> arrayResponse;
	@Mock
	private ResponseEntity<Order> orderResponse;
	@Mock
	private Order order;
	@Captor
	private ArgumentCaptor<HttpEntity<OrderRequest>> httpEntityArgumentCaptor;

	private static final String URL = "https://somewebsite.com";

	@Test
	public void getOrders() {
		// Given
		when(bigCommerceConfig.getApiUrl()).thenReturn(URL);
		when(restTemplate.exchange(
			eq(URL.concat("/v2/").concat(OrdersClient.PATH)),
			eq(HttpMethod.GET),
			any(HttpEntity.class),
			eq(Order[].class))
		).thenReturn(arrayResponse);
		when(arrayResponse.getStatusCode()).thenReturn(HttpStatus.OK);
		when(arrayResponse.getBody()).thenReturn(new Order[]{order});

		// When
		List<Order> result = ordersClient.getOrders();

		// Then
		assertThat(result).containsExactly(order);
	}


	@Test(expected = RuntimeException.class)
	public void getOrdersInvalidStatus() {
		// Given
		when(bigCommerceConfig.getApiUrl()).thenReturn(URL);
		when(restTemplate.exchange(
			eq(URL.concat("/v2/").concat(OrdersClient.PATH)),
			eq(HttpMethod.GET),
			any(HttpEntity.class),
			eq(Order[].class))
		).thenReturn(arrayResponse);
		when(arrayResponse.getStatusCode()).thenReturn(HttpStatus.NOT_FOUND);

		// When
		// Then
		ordersClient.getOrders();
	}

	@Test
	public void postOrders() {
		// Given
		when(bigCommerceConfig.getApiUrl()).thenReturn(URL);
		when(restTemplate.exchange(
			eq(URL.concat("/v2/").concat(OrdersClient.PATH)),
			eq(HttpMethod.POST),
			httpEntityArgumentCaptor.capture(),
			eq(Order.class))
		).thenReturn(orderResponse);
		when(orderResponse.getBody()).thenReturn(order);

		// When
		Optional<Order> result = ordersClient.postOrders(
			OrderRequest.builder()
				.products(Collections.emptyList())
				.customerId(1)
				.billingAddress(BillingAddressRequest.builder().build())
				.build()
		);

		// Then
		assertThat(result).isEqualTo(Optional.of(order));
	}
}
