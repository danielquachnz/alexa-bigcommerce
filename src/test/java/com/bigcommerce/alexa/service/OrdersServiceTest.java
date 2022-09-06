package com.bigcommerce.alexa.service;

import com.bigcommerce.alexa.model.Order;
import com.bigcommerce.alexa.rest.OrdersClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrdersServiceTest {

	@Mock
	private OrdersClient ordersClient;

	@InjectMocks
	private OrdersService ordersService;

	@Test
	public void getOrderCountSingle() {
		// Given
		when(ordersClient.getOrders())
			.thenReturn(Collections.singletonList(mock(Order.class)));

		// When
		final String message = ordersService.getOrderCountToday();

		// Then
		assertThat(message).isEqualTo("1 order has been placed");
	}

	@Test
	public void getOrderCountMultiple() {
		// Given
		when(ordersClient.getOrders())
			.thenReturn(Arrays.asList(mock(Order.class), mock(Order.class)));

		// When
		final String message = ordersService.getOrderCountToday();

		// Then
		assertThat(message).isEqualTo("2 orders have been placed");
	}


	@Test
	public void getOrderValue() {
		// Given
		final Order order1 = mock(Order.class);
		when(order1.getTotalIncTax()).thenReturn("11.11");
		final Order order2 = mock(Order.class);
		when(order2.getTotalIncTax()).thenReturn("22.22");
		when(ordersClient.getOrders())
			.thenReturn(Arrays.asList(order1, order2));

		// When
		final String message = ordersService.getOrderValueToday();

		// Then
		assertThat(message).isEqualTo("33.33 dollars of orders has been placed");
	}
}
