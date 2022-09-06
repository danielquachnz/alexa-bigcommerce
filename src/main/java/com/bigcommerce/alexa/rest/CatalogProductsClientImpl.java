package com.bigcommerce.alexa.rest;

import com.bigcommerce.alexa.model.Product;
import com.bigcommerce.alexa.model.ProductResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

class CatalogProductsClientImpl extends AbstractClient implements CatalogProductsClient {

	@Override
	public Optional<Product> getProductByName(String name) {

		final String url = getV3ApiUrl(CatalogProductsClient.PATH).concat("?name:like=".concat(name));
		final ResponseEntity<ProductResponse> productResponse = restTemplate.exchange(
			url,
			HttpMethod.GET,
			getApiTokenHeader(),
			ProductResponse.class
		);

		if (productResponse.getStatusCode().is2xxSuccessful()){
			final List<Product> products = productResponse.getBody().getData();

			return Optional.ofNullable(
				products == null || products.isEmpty() ? null : products.get(0)
			);
		}

		throw new RuntimeException(
			String.format("Unexpected status code %d", productResponse.getStatusCode().value())
		);
	}
}
