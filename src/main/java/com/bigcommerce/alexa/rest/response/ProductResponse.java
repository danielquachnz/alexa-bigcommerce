package com.bigcommerce.alexa.rest.response;

import com.bigcommerce.alexa.model.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.List;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@Data
public class ProductResponse {
	private List<Product> data;
}
