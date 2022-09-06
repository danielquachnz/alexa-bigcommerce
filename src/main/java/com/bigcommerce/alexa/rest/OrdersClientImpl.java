package com.bigcommerce.alexa.rest;

import com.bigcommerce.alexa.model.Order;
import com.bigcommerce.alexa.model.OrderRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class OrdersClientImpl extends AbstractClient implements OrdersClient {

	public List<Order> getOrders() {

		final ResponseEntity<Order[]> orders = restTemplate.exchange(
			getV2ApiUrl(OrdersClient.PATH),
			HttpMethod.GET,
			getApiTokenHeader(),
			Order[].class
		);

		if (orders.getStatusCode().is2xxSuccessful()){
			return Arrays.asList(
				Optional.ofNullable(orders.getBody()).orElse(new Order[0])
			);
		}

		throw new RuntimeException(
			String.format("Unexpected status code %d", orders.getStatusCode().value())
		);
	}

	@Override
	public Optional<Order> postOrders(OrderRequest orderRequest) {

		final ResponseEntity<Order> order = restTemplate.exchange(
			getV2ApiUrl(OrdersClient.PATH),
			HttpMethod.POST,
			getHttpEntityWithBody(orderRequest),
			Order.class
		);
		return Optional.ofNullable(order.getBody());
	}
}
