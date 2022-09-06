package com.bigcommerce.alexa.rest;

import com.bigcommerce.alexa.model.Order;
import com.bigcommerce.alexa.model.OrderRequest;

import java.util.List;
import java.util.Optional;

public interface OrdersClient {

	String PATH = "orders";
	List<Order> getOrders();
	Optional<Order> postOrders(OrderRequest orderRequest);
}
