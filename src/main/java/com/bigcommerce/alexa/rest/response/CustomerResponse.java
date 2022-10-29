package com.bigcommerce.alexa.rest.response;

import com.bigcommerce.alexa.model.Customer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@Data
public class CustomerResponse {
	private List<Customer> data;
}
