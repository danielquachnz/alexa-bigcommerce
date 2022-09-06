package com.bigcommerce.alexa.rest;

import com.google.inject.AbstractModule;

public class ClientModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(OrdersClient.class).to(OrdersClientImpl.class);
		bind(CustomersClient.class).to(CustomersClientImpl.class);
		bind(CustomersAddressesClient.class).to(CustomersAddressesClientImpl.class);
		bind(CatalogProductsClient.class).to(CatalogProductsClientImpl.class);
	}
}
