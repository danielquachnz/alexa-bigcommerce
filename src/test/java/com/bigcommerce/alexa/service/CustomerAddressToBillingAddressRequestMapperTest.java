package com.bigcommerce.alexa.service;

import com.bigcommerce.alexa.model.BillingAddressRequest;
import com.bigcommerce.alexa.model.CustomerAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CustomerAddressToBillingAddressRequestMapperTest {
	@InjectMocks
	private CustomerAddressToBillingAddressRequestMapper customerAddressToBillingAddressRequestMapper;

	@Test
	public void mapToBillingAddressRequest() {
		// Given
		final CustomerAddress customerAddress = CustomerAddress.builder()
			.firstName("first")
			.lastName("last")
			.address1("address1")
			.address2("address2")
			.customerId(1)
			.city("city")
			.phone("123")
			.company("company")
			.country("country")
			.countryCode("countryCode")
			.state("state")
			.postalCode("postalCode")
			.build();
		final String email = "email";

		// When
		final BillingAddressRequest result = customerAddressToBillingAddressRequestMapper.mapToBillingAddressRequest(customerAddress, email);

		// Then
		assertThat(result.getEmail()).isEqualTo(email);
		assertThat(result.getFirstName()).isEqualTo(customerAddress.getFirstName());
		assertThat(result.getLastName()).isEqualTo(customerAddress.getLastName());
		assertThat(result.getStreet1()).isEqualTo(customerAddress.getAddress1());
		assertThat(result.getStreet2()).isEqualTo(customerAddress.getAddress2());
		assertThat(result.getCity()).isEqualTo(customerAddress.getCity());
		assertThat(result.getState()).isEqualTo(customerAddress.getState());
		assertThat(result.getCountry()).isEqualTo(customerAddress.getCountry());
		assertThat(result.getCountryIso2()).isEqualTo(customerAddress.getCountryCode());
		assertThat(result.getPhone()).isEqualTo(customerAddress.getPhone());
		assertThat(result.getZip()).isEqualTo(customerAddress.getPostalCode());

	}
}
