package com.sample.api.config;

import java.util.Objects;

import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationClosedEventListener {
	private final UndertowShutdownHandlerWrapper undertowShutdownHandlerWrapper;

	@EventListener
	public void processContextClosedEvent(final ContextClosedEvent event) throws InterruptedException {
		if (Objects.isNull(undertowShutdownHandlerWrapper)
			|| Objects.isNull(undertowShutdownHandlerWrapper.getGracefulShutdownHandler())) {
			return;
		}

		log.info("undertow shutdown,,,,,,,,,,,,,,,,,,");
		undertowShutdownHandlerWrapper.getGracefulShutdownHandler().shutdown();
		undertowShutdownHandlerWrapper.getGracefulShutdownHandler().awaitShutdown(5000);
		log.info("undertow shutdown done.");
	}
}