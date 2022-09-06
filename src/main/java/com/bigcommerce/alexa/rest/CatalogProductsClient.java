package com.bigcommerce.alexa.rest;

import com.bigcommerce.alexa.model.Product;

import java.util.Optional;

public interface CatalogProductsClient {

	String PATH = "catalog/products";
	Optional<Product> getProductByName(String name);
}
