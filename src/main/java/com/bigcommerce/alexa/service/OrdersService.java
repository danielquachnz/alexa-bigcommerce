package com.bigcommerce.alexa.service;

import com.bigcommerce.alexa.model.*;
import com.bigcommerce.alexa.rest.CatalogProductsClient;
import com.bigcommerce.alexa.rest.CustomersAddressesClient;
import com.bigcommerce.alexa.rest.CustomersClient;
import com.bigcommerce.alexa.rest.OrdersClient;
import com.bigcommerce.alexa.rest.request.BillingAddressRequest;
import com.bigcommerce.alexa.rest.request.OrderProductRequest;
import com.bigcommerce.alexa.rest.request.OrderRequest;
import com.google.inject.Inject;

import java.util.Collections;
import java.util.Optional;

public class OrdersService {

	@Inject
	private OrdersClient ordersClient;
	@Inject
	private CatalogProductsClient productsClient;
	@Inject
	private CustomersClient customersClient;
	@Inject
	private CustomersAddressesClient customersAddressesClient;
	@Inject
	private CustomerAddressToBillingAddressRequestMapper addressMapper;

	public String getOrderCountToday() {

		final int total = ordersClient.getOrders().size();

		return total == 1
			? String.format("%d order has been placed", total)
			: String.format("%d orders have been placed", total);
	}

	public String getOrderValueToday() {

		final double total = ordersClient.getOrders()
			.stream()
			.map(Order::getTotalIncTax)
			.map(Double::valueOf)
			.reduce(0.0, Double::sum);

		return String.format(
			"%.2f dollars of orders have been placed",
			total
		);
	}


	public String placeOrder(String productName, String customerName) {

		final Optional<Product> oProduct = productsClient.getProductByName(productName);
		if (oProduct.isEmpty()) {
			return String.format("Product with name %s could not be found", productName);
		}

		final Optional<Customer> oCustomer = customersClient.getCustomerByName(customerName);
		if (oCustomer.isEmpty()) {
			return String.format("Customer with name %s could not be found", customerName);
		}

		final int customerId = oCustomer.get().getId();
		final Optional<CustomerAddress> oCustomerAddress = customersAddressesClient.getCustomerAddressByCustomerId(
			oCustomer.get().getId()
		);
		if (oCustomerAddress.isEmpty()) {
			return String.format("Customer address could not be found for %s", customerName);
		}

		final BillingAddressRequest billingAddressRequest = addressMapper.mapToBillingAddressRequest(
			oCustomerAddress.get(),
			oCustomer.get().getEmail()
		);

		final OrderRequest orderRequest = OrderRequest.builder()
			.products(
				Collections.singletonList(
					OrderProductRequest.builder()
						.productId(oProduct.get().getId())
						.quantity(1)
						.build()
				)
			)
			.customerId(customerId)
			.billingAddress(billingAddressRequest)
			.build();

		final Optional<Order> oOrder = ordersClient.postOrders(orderRequest);
		if (oOrder.isEmpty()) {
			return "There was an issue placing the order";
		}

		return String.format(
			"Order placed with product %s for customer %s %s. The order ID is %d",
			oProduct.get().getName(),
			oCustomer.get().getFirstName(),
			oCustomer.get().getLastName(),
			oOrder.get().getId()
		);
	}
}
