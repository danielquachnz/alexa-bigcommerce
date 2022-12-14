package com.bigcommerce.alexa.rest.request;

import com.bigcommerce.alexa.model.CustomerAddress;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.List;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@Data
public class CustomerAddressResponse {
	private List<CustomerAddress> data;
}
