package com.bigcommerce.alexa.rest;

import com.bigcommerce.alexa.model.CustomerAddress;

import java.util.Optional;

public interface CustomersAddressesClient {

	String PATH = "customers/addresses";
	Optional<CustomerAddress> getCustomerAddressByCustomerId(int customerId);
}
