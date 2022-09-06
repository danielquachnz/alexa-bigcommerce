package com.bigcommerce.alexa.service;

import com.bigcommerce.alexa.model.Product;
import com.bigcommerce.alexa.rest.CatalogProductsController;
import com.google.inject.Inject;

import java.util.Optional;

public class ProductService {

	@Inject
	private CatalogProductsController productsController;

	public String getProductByName(String name) {
		final Optional<Product> oProduct = productsController.getProductByName(name);

		if (oProduct.isEmpty()) {
			return String.format("Product with name %s could not be found", name);
		}

		final Product product = oProduct.get();

		return String.format(
			"Found product with name %s",
			product.getName()
		);
	}
}
