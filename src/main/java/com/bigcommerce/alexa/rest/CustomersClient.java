package com.bigcommerce.alexa.rest;

import com.bigcommerce.alexa.model.Customer;

import java.util.Optional;

public interface CustomersClient {

	String PATH = "customers";
	Optional<Customer> getCustomerByName(String name);
}
