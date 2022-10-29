package com.bigcommerce.alexa.rest;

import com.bigcommerce.alexa.config.BigCommerceConfig;
import com.bigcommerce.alexa.util.UrlUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

abstract class AbstractClient {
	private static final String API_KEY_HEADER = "X-Auth-Token";

	@Inject
	protected RestTemplate restTemplate;
	@Inject
	private BigCommerceConfig bigCommerceConfig;
	private final ObjectMapper objectMapper = new ObjectMapper();

	protected HttpEntity<String> getApiTokenHeader() {
		final HttpHeaders headers = new HttpHeaders();
		headers.add(API_KEY_HEADER, bigCommerceConfig.getApiToken());
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		return new HttpEntity<>(headers);
	}

	protected HttpEntity<String> getHttpEntityWithBody(Object requestBody) {
		final HttpHeaders headers = new HttpHeaders();
		headers.add(API_KEY_HEADER, bigCommerceConfig.getApiToken());
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		try {
			final String jsonRequest = objectMapper.writeValueAsString(requestBody);
			return new HttpEntity<>(jsonRequest, headers);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Could not parse JSON request", e);
		}
	}

	protected String getV2ApiUrl(String path) {
		return UrlUtil.createUrl(bigCommerceConfig.getApiUrl(), "v2", path);
	}

	protected String getV3ApiUrl(String path) {
		return UrlUtil.createUrl(bigCommerceConfig.getApiUrl(), "v3", path);
	}

}
