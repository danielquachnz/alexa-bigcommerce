package com.bigcommerce.alexa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@Data
@Builder
public class CustomerAddress {
	private Integer id;
	private String address1;
	private String address2;
	@JsonProperty("address_type")
	private String addressType;
	private String city;
	private String company;
	private String country;
	@JsonProperty("country_code")
	private String countryCode;
	@JsonProperty("customer_id")
	private Integer customerId;
	@JsonProperty("first_name")
	private String firstName;
	@JsonProperty("last_name")
	private String lastName;
	private String phone;
	@JsonProperty("postal_code")
	private String postalCode;
	@JsonProperty("state_or_province")
	private String state;

}
