package com.bigcommerce.alexa.rest;

import com.bigcommerce.alexa.model.Customer;
import com.bigcommerce.alexa.model.Order;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Optional;

public interface CustomersController {

	String PATH = "customers";
	Optional<Customer> getCustomerByName(String name);
}
