package com.sample.api.config;

import java.util.Objects;

import io.undertow.server.HandlerWrapper;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.GracefulShutdownHandler;

public class UndertowShutdownHandlerWrapper implements HandlerWrapper {

	private GracefulShutdownHandler gracefulShutdownHandler;

	@Override
	public HttpHandler wrap(final HttpHandler handler) {
		if (Objects.isNull(gracefulShutdownHandler)) {
			this.gracefulShutdownHandler = new GracefulShutdownHandler(handler);
		}

		return gracefulShutdownHandler;
	}

	public GracefulShutdownHandler getGracefulShutdownHandler() {
		return gracefulShutdownHandler;
	}
}