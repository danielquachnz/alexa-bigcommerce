package com.bigcommerce.alexa.rest;


import com.bigcommerce.alexa.config.BigCommerceConfig;
import com.bigcommerce.alexa.model.Customer;
import com.bigcommerce.alexa.rest.response.CustomerResponse;
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
public class CustomersClientImplTest {
	@Mock
	private RestTemplate restTemplate;
	@Mock
	private BigCommerceConfig bigCommerceConfig;

	@InjectMocks
	private CustomersClientImpl customersClient;

	@Mock
	private ResponseEntity<CustomerResponse> customerResponseEntity;
	@Mock
	private CustomerResponse customerResponse;
	@Mock
	private Customer customer;

	private static final String URL = "https://somewebsite.com";

	@Test
	public void getCustomerByName() {
		// Given
		final String name = "name";
		when(bigCommerceConfig.getApiUrl()).thenReturn(URL);
		when(restTemplate.exchange(
			eq(URL.concat("/v3/").concat(CustomersClient.PATH).concat("?name:like=").concat(name)),
			eq(HttpMethod.GET),
			any(HttpEntity.class),
			eq(CustomerResponse.class))
		).thenReturn(customerResponseEntity);
		when(customerResponseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
		when(customerResponseEntity.getBody()).thenReturn(customerResponse);
		when(customerResponse.getData()).thenReturn(Collections.singletonList(customer));

		// When
		Optional<Customer> result = customersClient.getCustomerByName(name);

		// Then
		assertThat(result).isEqualTo(Optional.of(customer));
	}


	@Test(expected = RuntimeException.class)
	public void getCustomerByNameInvalidStatus() {
		// Given
		final String name = "name";
		when(bigCommerceConfig.getApiUrl()).thenReturn(URL);
		when(restTemplate.exchange(
			eq(URL.concat("/v3/").concat(CustomersClient.PATH).concat("?name:like=").concat(name)),
			eq(HttpMethod.GET),
			any(HttpEntity.class),
			eq(CustomerResponse.class))
		).thenReturn(customerResponseEntity);
		when(customerResponseEntity.getStatusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);

		// When
		// Then
		customersClient.getCustomerByName(name);
	}
}
