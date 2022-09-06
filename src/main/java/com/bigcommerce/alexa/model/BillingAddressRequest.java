package com.bigcommerce.alexa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@Data
@Builder
public class BillingAddressRequest {
	@JsonProperty("first_name")
	private String firstName;
	@JsonProperty("last_name")
	private String lastName;
	private String company;
	@JsonProperty("street_1")
	private String street1;
	@JsonProperty("street_2")
	private String street2;
	private String city;
	@JsonProperty("state")
	private String state;
	private String zip;
	private String country;
	@JsonProperty("country_iso2")
	private String countryIso2;
	private String phone;
	private String email;

}
