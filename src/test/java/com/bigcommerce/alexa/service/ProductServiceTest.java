package com.bigcommerce.alexa.service;

import com.bigcommerce.alexa.model.Product;
import com.bigcommerce.alexa.rest.CatalogProductsClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

	@Mock
	private CatalogProductsClient productsClient;
	@Mock
	private Product product;

	@InjectMocks
	private ProductService productService;

	@Test
	public void getProductByName() {
		final String name = "name";
		when(productsClient.getProductByName(name)).thenReturn(Optional.of(product));
		when(product.getName()).thenReturn("full name");

		// When
		// Then
		assertThat(productService.getProductByName(name))
			.isEqualTo("Found product with name full name");
	}

	@Test
	public void getProductByNameNotFound() {
		// Given
		final String name = "name";
		when(productsClient.getProductByName(name)).thenReturn(Optional.empty());

		// When
		// Then
		assertThat(productService.getProductByName(name))
			.isEqualTo(String.format("Product with name %s could not be found", name));
	}
}
