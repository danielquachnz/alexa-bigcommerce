package com.bigcommerce.alexa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@Data
@Builder
public class OrderProductRequest {
	@JsonProperty("product_id")
	private Integer productId;
	private Integer quantity;
}
