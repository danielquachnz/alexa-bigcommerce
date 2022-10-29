package com.bigcommerce.alexa.rest;

import com.bigcommerce.alexa.model.CustomerAddress;
import com.bigcommerce.alexa.rest.request.CustomerAddressResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

class CustomersAddressesClientImpl extends AbstractClient implements CustomersAddressesClient {

	@Override
	public Optional<CustomerAddress> getCustomerAddressByCustomerId(int customerId) {
		final String url = String.format(
			"%s%s%d",
			getV3ApiUrl(CustomersAddressesClient.PATH),
			"?customer_id:in=",
			customerId
		);
		final ResponseEntity<CustomerAddressResponse> addressResponse = restTemplate.exchange(
			url,
			HttpMethod.GET,
			getApiTokenHeader(),
			CustomerAddressResponse.class
		);

		if (addressResponse.getStatusCode().is2xxSuccessful()){
			return Optional.ofNullable(addressResponse.getBody())
				.map(CustomerAddressResponse::getData)
				.map(c -> c.get(0));
		}

		throw new RuntimeException(
			String.format("Unexpected status code %d", addressResponse.getStatusCode().value())
		);
	}
}
