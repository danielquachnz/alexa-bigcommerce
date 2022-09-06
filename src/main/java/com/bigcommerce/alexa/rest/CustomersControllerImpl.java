package com.bigcommerce.alexa.rest;

import com.bigcommerce.alexa.model.Customer;
import com.bigcommerce.alexa.model.CustomerResponse;
import com.bigcommerce.alexa.model.Order;
import org.joda.time.DateTime;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class CustomersControllerImpl extends AbstractController implements CustomersController {

	@Override
	public Optional<Customer> getCustomerByName(String name) {

		final String url = getV3ApiUrl(CustomersController.PATH)
			.concat("?name:like=".concat(name));
		final ResponseEntity<CustomerResponse> customerResponse = restTemplate.exchange(
			url,
			HttpMethod.GET,
			getApiTokenHeader(),
			CustomerResponse.class
		);

		if (customerResponse.getStatusCode().is2xxSuccessful()){
			return Optional.ofNullable(customerResponse.getBody())
				.map(CustomerResponse::getData)
				.map(c -> c.get(0));
		}

		throw new RuntimeException(
			String.format("Unexpected status code %d", customerResponse.getStatusCode().value())
		);
	}
}
