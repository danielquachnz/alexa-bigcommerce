package com.bigcommerce.alexa.rest;

import com.google.inject.AbstractModule;

public class ControllerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(OrdersController.class).to(OrdersControllerImpl.class);
		bind(CustomersController.class).to(CustomersControllerImpl.class);
		bind(CustomersAddressesController.class).to(CustomersAddressesControllerImpl.class);
		bind(CatalogProductsController.class).to(CatalogProductsControllerImpl.class);
	}
}
