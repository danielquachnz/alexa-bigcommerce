package com.bigcommerce.alexa.handler;

import com.amazon.ask.dispatcher.exception.ExceptionHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;

import java.util.Optional;

class GenericExceptionHandler implements ExceptionHandler {

	@Override
	public boolean canHandle(HandlerInput input, Throwable throwable) {
		return true;
	}

	@Override
	public Optional<Response> handle(HandlerInput input, Throwable throwable) {
		System.out.println(throwable.getMessage());
		return input.getResponseBuilder()
			.withSpeech("Unexpected error returned")
			.build();
	}
}
