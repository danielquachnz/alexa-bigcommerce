package com.bigcommerce.alexa.handler;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import com.amazon.ask.builder.StandardSkillBuilder;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.bigcommerce.alexa.module.Startup;
import com.google.inject.Injector;

public class AlexaStreamHandler extends SkillStreamHandler {

	private static Skill getSkill() {
		Injector injector = Startup.getInjector();

		StandardSkillBuilder builder = Skills.standard()
			.addExceptionHandler(new GenericExceptionHandler());
		for (final RequestHandler handler: injector.getInstance(HandlerService.class).getHandlers()) {
			builder = builder.addRequestHandler(handler);

		}
		return builder.build();
	}

	public AlexaStreamHandler() {
		super(getSkill());
	}
}