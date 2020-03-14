package com.sample.api.config;

import org.springframework.boot.web.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UndertowConfig {

    @Bean
    public WebServerFactoryCustomizer<UndertowServletWebServerFactory> undertowCustomizer() {
        return factory -> factory.addDeploymentInfoCustomizers(undertowDeploymentInfoCustomizer());
    }

    @Bean
    public UndertowDeploymentInfoCustomizer undertowDeploymentInfoCustomizer() {
        return deploymentInfo -> deploymentInfo.addOuterHandlerChainWrapper(undertowShutdownHandlerWrapper());
    }

    @Bean
    public UndertowShutdownHandlerWrapper undertowShutdownHandlerWrapper() {
        return new UndertowShutdownHandlerWrapper();
    }
}