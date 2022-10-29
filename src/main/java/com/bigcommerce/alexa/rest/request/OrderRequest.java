package com.bigcommerce.alexa.rest.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@Data
@Builder
public class OrderRequest {
	@NonNull
	private List<OrderProductRequest> products;

	@NonNull
	@JsonProperty("customer_id")
	private Integer customerId;

	@NonNull
	@JsonProperty("billing_address")
	private BillingAddressRequest billingAddress;
}
