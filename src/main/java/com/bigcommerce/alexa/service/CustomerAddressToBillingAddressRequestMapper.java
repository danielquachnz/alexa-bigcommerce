package com.bigcommerce.alexa.service;

import com.bigcommerce.alexa.model.*;

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
