package com.bigcommerce.alexa.rest;


import com.bigcommerce.alexa.config.BigCommerceConfig;
import com.bigcommerce.alexa.model.*;
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
public class CatalogProductsClientImplTest {
	@Mock
	private RestTemplate restTemplate;
	@Mock
	private BigCommerceConfig bigCommerceConfig;

	@InjectMocks
	private CatalogProductsClientImpl catalogProductsClient;

	@Mock
	private ResponseEntity<ProductResponse> productResponseResponseEntity;
	@Mock
	private ProductResponse productResponse;
	@Mock
	private Product product;
	private static final String URL = "https://somewebsite.com";

	@Test
	public void getProductByName() {
		// Given
		final String name = "name";
		when(bigCommerceConfig.getApiUrl()).thenReturn(URL);
		when(restTemplate.exchange(
			eq(URL.concat("/v3/").concat(CatalogProductsClient.PATH).concat("?name:like=").concat(name)),
			eq(HttpMethod.GET),
			any(HttpEntity.class),
			eq(ProductResponse.class))
		).thenReturn(productResponseResponseEntity);
		when(productResponseResponseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
		when(productResponseResponseEntity.getBody()).thenReturn(productResponse);
		when(productResponse.getData()).thenReturn(Collections.singletonList(product));

		// When
		Optional<Product> result = catalogProductsClient.getProductByName(name);

		// Then
		assertThat(result).isEqualTo(Optional.of(product));
	}

	@Test(expected = RuntimeException.class)
	public void getProductByNameInvalidStatus() {
		// Given
		final String name = "name";
		when(bigCommerceConfig.getApiUrl()).thenReturn(URL);
		when(restTemplate.exchange(
			eq(URL.concat("/v3/").concat(CatalogProductsClient.PATH).concat("?name:like=").concat(name)),
			eq(HttpMethod.GET),
			any(HttpEntity.class),
			eq(ProductResponse.class))
		).thenReturn(productResponseResponseEntity);
		when(productResponseResponseEntity.getStatusCode()).thenReturn(HttpStatus.NOT_FOUND);

		// When
		// Then
		catalogProductsClient.getProductByName(name);
	}
}
