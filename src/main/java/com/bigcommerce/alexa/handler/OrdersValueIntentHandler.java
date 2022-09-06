package com.bigcommerce.alexa.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.Predicates;
import com.bigcommerce.alexa.service.OrdersService;
import com.google.inject.Inject;

import java.util.Map;
import java.util.function.Predicate;

class OrdersValueIntentHandler extends AbstractRequestHandler {

	@Inject
	private OrdersService ordersService;

	@Override
	Predicate<HandlerInput> getHandlerInput() {
		return Predicates.intentName("OrdersValueIntent");
	}

	@Override
	String getText(Map<String, Slot> slotsMap) {
		return ordersService.getOrderValueToday();
	}
}