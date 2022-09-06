package com.bigcommerce.alexa.handler;

import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.google.inject.Inject;

import java.util.Arrays;
import java.util.List;

public class HandlerService {
	@Inject
	private LaunchRequestHandler launchRequestHandler;
	@Inject
	private OrdersCountIntentHandler ordersCountIntentHandler;
	@Inject
	private OrdersValueIntentHandler ordersValueIntentHandler;
	@Inject
	private ProductByNameIntentHandler productByNameIntentHandler;
	@Inject
	private PlaceOrderIntentHandler placeOrderIntentHandler;

	public List<RequestHandler> getHandlers() {
		return Arrays.asList(
			launchRequestHandler,
			ordersCountIntentHandler,
			ordersValueIntentHandler,
			productByNameIntentHandler,
			placeOrderIntentHandler
		);
	}
}
