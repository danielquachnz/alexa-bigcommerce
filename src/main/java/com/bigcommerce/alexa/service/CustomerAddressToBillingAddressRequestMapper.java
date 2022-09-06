package com.bigcommerce.alexa.service;

import com.bigcommerce.alexa.model.*;
import com.bigcommerce.alexa.rest.CatalogProductsController;
import com.bigcommerce.alexa.rest.CustomersAddressesController;
import com.bigcommerce.alexa.rest.CustomersController;
import com.bigcommerce.alexa.rest.OrdersController;
import com.google.inject.Inject;

import java.util.List;
import java.util.Optional;

public class CustomerAddressToBillingAddressRequestMapper {

	public BillingAddressRequest mapToBillingAddressRequest(CustomerAddress customerAddress, String email) {
		return BillingAddressRequest.builder()
			.firstName(customerAddress.getFirstName())
			.lastName(customerAddress.getLastName())
			.company(customerAddress.getCompany())
			.street1(customerAddress.getAddress1())
			.street2(customerAddress.getAddress2())
			.city(customerAddress.getCity())
			.state(customerAddress.getState())
			.zip(customerAddress.getPostalCode())
			.country(customerAddress.getCountry())
			.countryIso2(customerAddress.getCountryCode())
			.phone(customerAddress.getPhone())
			.email(email)
			.build();
	}
}
