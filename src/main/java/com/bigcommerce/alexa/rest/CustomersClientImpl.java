package com.bigcommerce.alexa.rest;

import com.bigcommerce.alexa.model.Customer;
import com.bigcommerce.alexa.rest.response.CustomerResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

class CustomersClientImpl extends AbstractClient implements CustomersClient {

	@Override
	public Optional<Customer> getCustomerByName(String name) {

		final String url = getV3ApiUrl(CustomersClient.PATH)
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

		if (customerResponse.getStatusCode()== HttpStatus.NOT_FOUND){
			return Optional.empty();
		}
		throw new RuntimeException(
			String.format("Unexpected status code %d", customerResponse.getStatusCode().value())
		);
	}
}
