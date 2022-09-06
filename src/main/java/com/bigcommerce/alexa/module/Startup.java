package com.bigcommerce.alexa.module;

import com.bigcommerce.alexa.rest.ClientModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Startup {
	private static Startup STARTUP = null;
	private static Injector INJECTOR = null;

	public static Startup getInstance() {
		if (STARTUP == null) {
			STARTUP = new Startup();
			getInjector();
		}
		return STARTUP;
	}

	public static Injector getInjector() {
		if (INJECTOR == null) {
			INJECTOR = Guice.createInjector(
				new AlexaModule(),
				new ClientModule()
			);
			INJECTOR.getInstance(Startup.class);
		}
		return INJECTOR;
	}
}
