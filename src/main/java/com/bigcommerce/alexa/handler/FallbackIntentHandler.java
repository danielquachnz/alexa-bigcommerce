package com.bigcommerce.alexa.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Slot;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static com.amazon.ask.request.Predicates.intentName;

class FallbackIntentHandler extends AbstractRequestHandler {

	@Override
	Predicate<HandlerInput> getHandlerInput() {
		return intentName("AMAZON.FallbackIntent");
	}

	@Override
	String getText(Map<String, Slot> slotsMap) {
		return "Sorry, I didn't understand that";
	}
}