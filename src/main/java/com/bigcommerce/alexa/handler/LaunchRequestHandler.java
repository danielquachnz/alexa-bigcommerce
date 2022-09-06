package com.bigcommerce.alexa.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.Predicates;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

class LaunchRequestHandler extends AbstractRequestHandler {

	@Override
	Predicate<HandlerInput> getHandlerInput() {
		return Predicates.requestType(LaunchRequest.class);
	}

	@Override
	String getText(Map<String, Slot> slotsMap) {
		return "Welcome to the Alexa Skills Kit Daniel, you can say hello";
	}
}