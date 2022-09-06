package com.bigcommerce.alexa.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.Predicates;
import com.bigcommerce.alexa.service.ProductService;
import com.google.inject.Inject;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

class ProductByNameIntentHandler extends AbstractRequestHandler {

	@Inject
	private ProductService productService;

	@Override
	Predicate<HandlerInput> getHandlerInput() {
		return Predicates.intentName("ProductByNameIntent");
	}

	@Override
	String getText(Map<String, Slot> slotsMap) {
		final String productName = slotsMap.get("productName").getValue();
		System.out.println(String.format("Product name is %s", productName));
		return productService.getProductByName(productName);
	}
}