package com.bigcommerce.alexa.config;

import org.apache.commons.lang3.StringUtils;

public class BigCommerceConfig {
	private String apiUrl;
	private String apiToken;

	public String getApiToken() {
		if (StringUtils.isBlank(apiToken)) {
			apiToken = System.getenv("API_TOKEN");
			if (StringUtils.isBlank(apiToken)) {
				throw new RuntimeException("API token should not be blank");
			}
		}
		return apiToken;
	}

	public String getApiUrl() {
		if (StringUtils.isBlank(apiUrl)) {
			apiUrl = System.getenv("API_URL");
			if (StringUtils.isBlank(apiUrl)) {
			  throw new RuntimeException("API URL should not be blank");
			}
		}

	  return apiUrl;
	}
}
