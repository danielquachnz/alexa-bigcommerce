package com.bigcommerce.alexa.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

abstract class AbstractRequestHandler implements RequestHandler {

	abstract Predicate<HandlerInput> getHandlerInput();
	abstract String getText(Map<String, Slot> slotsMap);

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(getHandlerInput());
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		final Request request = input.getRequestEnvelope().getRequest();

		final Map<String, Slot> slotsMap = request instanceof IntentRequest
			?((IntentRequest) request).getIntent().getSlots()
			: Collections.emptyMap();
		return input.getResponseBuilder()
			.withSpeech(getText(slotsMap))
			.build();
	}
}