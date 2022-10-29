package com.bigcommerce.alexa.rest;


import com.bigcommerce.alexa.config.BigCommerceConfig;
import com.bigcommerce.alexa.model.CustomerAddress;
import com.bigcommerce.alexa.model.CustomerAddressResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomersAddressesClientImplTest {
	@Mock
	private RestTemplate restTemplate;
	@Mock
	private BigCommerceConfig bigCommerceConfig;

	@InjectMocks
	private CustomersAddressesClientImpl customersAddressesClient;

	@Mock
	private ResponseEntity<CustomerAddressResponse> customerAddressResponseEntity;
	@Mock
	private CustomerAddressResponse customerAddressResponse;
	@Mock
	private CustomerAddress customerAddress;

	private static final String URL = "https://somewebsite.com";

	@Test
	public void getCustomerAddressByCustomerId() {
		// Given
		final int customerId = 1;
		when(bigCommerceConfig.getApiUrl()).thenReturn(URL);
		when(restTemplate.exchange(
			eq(URL.concat("/v3/").concat(CustomersAddressesClient.PATH).concat("?customer_id:in=").concat(Integer.toString(customerId))),
			eq(HttpMethod.GET),
			any(HttpEntity.class),
			eq(CustomerAddressResponse.class))
		).thenReturn(customerAddressResponseEntity);
		when(customerAddressResponseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
		when(customerAddressResponseEntity.getBody()).thenReturn(customerAddressResponse);
		when(customerAddressResponse.getData()).thenReturn(Collections.singletonList(customerAddress));

		// When
		Optional<CustomerAddress> result = customersAddressesClient.getCustomerAddressByCustomerId(customerId);

		// Then
		assertThat(result).isEqualTo(Optional.of(customerAddress));
	}


	@Test(expected = RuntimeException.class)
	public void getCustomerAddressByCustomerIdInvalidStatus() {
		// Given
		final int customerId = 1;
		when(bigCommerceConfig.getApiUrl()).thenReturn(URL);
		when(restTemplate.exchange(
			eq(URL.concat("/v3/").concat(CustomersAddressesClient.PATH).concat("?customer_id:in=").concat(Integer.toString(customerId))),
			eq(HttpMethod.GET),
			any(HttpEntity.class),
			eq(CustomerAddressResponse.class))
		).thenReturn(customerAddressResponseEntity);
		when(customerAddressResponseEntity.getStatusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);

		// When
		// Then
		customersAddressesClient.getCustomerAddressByCustomerId(customerId);
	}
}
