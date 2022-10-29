package com.bigcommerce.alexa.service;

import com.bigcommerce.alexa.model.*;
import com.bigcommerce.alexa.rest.CatalogProductsClient;
import com.bigcommerce.alexa.rest.CustomersAddressesClient;
import com.bigcommerce.alexa.rest.CustomersClient;
import com.bigcommerce.alexa.rest.OrdersClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrdersServiceTest {

	@Mock
	private OrdersClient ordersClient;
	@Mock
	private CatalogProductsClient productsClient;
	@Mock
	private CustomersClient customersClient;
	@Mock
	private CustomersAddressesClient customersAddressesClient;
	@Mock
	private CustomerAddressToBillingAddressRequestMapper addressMapper;

	@InjectMocks
	private OrdersService ordersService;

	@Mock
	private Product product;
	@Mock
	private Customer customer;
	@Mock
	private CustomerAddress customerAddress;
	@Mock
	private BillingAddressRequest billingAddressRequest;
	@Mock
	private Order order;

	private static final String PRODUCT_NAME = "product name";
	private static final String CUSTOMER_NAME = "customer name";
	private static final int CUSTOMER_ID = 123;
	private static final String CUSTOMER_EMAIL = "customer@email.com";
	private static final int PRODUCT_ID = 5;

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
		when(ordersClient.getOrders()).thenReturn(Arrays.asList(order1, order2));

		// When
		final String message = ordersService.getOrderValueToday();

		// Then
		assertThat(message).isEqualTo("33.33 dollars of orders have been placed");
	}

	@Test
	public void placeOrderProductNotFound() {
		// Given
		when(productsClient.getProductByName(PRODUCT_NAME)).thenReturn(Optional.empty());

		// When
		final String result = ordersService.placeOrder(PRODUCT_NAME, CUSTOMER_NAME);

		// Then
		assertThat(result).isEqualTo(String.format("Product with name %s could not be found", PRODUCT_NAME));
	}

	@Test
	public void placeOrderCustomerNotFound() {
		// Given
		when(productsClient.getProductByName(PRODUCT_NAME)).thenReturn(Optional.of(product));
		when(customersClient.getCustomerByName(CUSTOMER_NAME)).thenReturn(Optional.empty());

		// When
		final String result = ordersService.placeOrder(PRODUCT_NAME, CUSTOMER_NAME);

		// Then
		assertThat(result).isEqualTo(String.format("Customer with name %s could not be found", CUSTOMER_NAME));
	}

	@Test
	public void placeOrderCustomerAddressNotFound() {
		// Given
		when(productsClient.getProductByName(PRODUCT_NAME)).thenReturn(Optional.of(product));
		when(customersClient.getCustomerByName(CUSTOMER_NAME)).thenReturn(Optional.of(customer));
		when(customer.getId()).thenReturn(CUSTOMER_ID);
		when(customersAddressesClient.getCustomerAddressByCustomerId(CUSTOMER_ID)).thenReturn(Optional.empty());

		// When
		final String result = ordersService.placeOrder(PRODUCT_NAME, CUSTOMER_NAME);

		// Then
		assertThat(result).isEqualTo(String.format("Customer address could not be found for %s", CUSTOMER_NAME));
	}

	@Test
	public void placeOrderReturnsEmptyOrder() {
		// Given
		when(productsClient.getProductByName(PRODUCT_NAME)).thenReturn(Optional.of(product));
		when(customersClient.getCustomerByName(CUSTOMER_NAME)).thenReturn(Optional.of(customer));

		when(customer.getId()).thenReturn(CUSTOMER_ID);
		when(customersAddressesClient.getCustomerAddressByCustomerId(CUSTOMER_ID)).thenReturn(Optional.of(customerAddress));
		when(customer.getEmail()).thenReturn(CUSTOMER_EMAIL);
		when(addressMapper.mapToBillingAddressRequest(customerAddress, CUSTOMER_EMAIL)).thenReturn(billingAddressRequest);

		when(product.getId()).thenReturn(PRODUCT_ID);
		when(ordersClient.postOrders(any(OrderRequest.class))).thenReturn(Optional.empty());

		// When
		final String result = ordersService.placeOrder(PRODUCT_NAME, CUSTOMER_NAME);

		// Then
		assertThat(result).isEqualTo("There was an issue placing the order");
	}

	@Test
	public void placeOrder() {
		// Given
		when(productsClient.getProductByName(PRODUCT_NAME)).thenReturn(Optional.of(product));
		when(customersClient.getCustomerByName(CUSTOMER_NAME)).thenReturn(Optional.of(customer));

		when(customer.getId()).thenReturn(CUSTOMER_ID);
		when(customersAddressesClient.getCustomerAddressByCustomerId(CUSTOMER_ID)).thenReturn(Optional.of(customerAddress));
		when(customer.getEmail()).thenReturn(CUSTOMER_EMAIL);
		when(addressMapper.mapToBillingAddressRequest(customerAddress, CUSTOMER_EMAIL)).thenReturn(billingAddressRequest);

		when(product.getId()).thenReturn(PRODUCT_ID);
		when(ordersClient.postOrders(any(OrderRequest.class))).thenReturn(Optional.of(order));


		when(product.getName()).thenReturn("ProductName");
		when(customer.getFirstName()).thenReturn("Daniel");
		when(customer.getLastName()).thenReturn("Quach");
		when(order.getId()).thenReturn(321);

		// When
		final String result = ordersService.placeOrder(PRODUCT_NAME, CUSTOMER_NAME);

		// Then
		assertThat(result)
			.isEqualTo("Order placed with product ProductName for customer Daniel Quach. The order ID is 321");
	}
}
