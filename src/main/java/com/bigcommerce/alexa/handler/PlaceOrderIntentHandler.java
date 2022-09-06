package com.bigcommerce.alexa.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.Predicates;
import com.bigcommerce.alexa.service.OrdersService;
import com.bigcommerce.alexa.service.ProductService;
import com.google.inject.Inject;

import java.util.Map;
import java.util.function.Predicate;

class PlaceOrderIntentHandler extends AbstractRequestHandler {

	@Inject
	private OrdersService ordersService;

	@Override
	Predicate<HandlerInput> getHandlerInput() {
		return Predicates.intentName("PlaceOrderIntent");
	}

	@Override
	String getText(Map<String, Slot> slotsMap) {
		System.out.println(String.format(
			"=== product and customer ===", slotsMap.get("productAndCustomer").getValue()
		));
		final String[] params = slotsMap.get("productAndCustomer").getValue().split(" for ");
		if (params.length != 2) {
			throw new RuntimeException("Require both a product and a customer");
		}
		final String productName = params[0];
		final String customerName = params[1];
		System.out.println(String.format(
			"Placing order for %s for customer %s", productName, customerName
		));
		return ordersService.placeOrder(productName, customerName);
	}
}