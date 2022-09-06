package com.bigcommerce.alexa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@Data
public class Order {
	private Integer id;

	@JsonProperty("customer_id")
	private int customerId;

	@JsonProperty("total_inc_tax")
	private String totalIncTax;

	@JsonProperty("total_ex_tax")
	private String totalExTax;
}
