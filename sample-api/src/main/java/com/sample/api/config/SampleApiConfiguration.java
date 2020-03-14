package com.sample.api.config;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.api.SampleApiConfigMarker;
import com.sample.api.util.ObjectMapperFactory;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackageClasses = SampleApiConfigMarker.class)
public class SampleApiConfiguration implements WebMvcConfigurer, ResourceLoaderAware {

	private ResourceLoader resourceLoader;

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@Bean
	public ObjectMapper objectMapper() {
		return ObjectMapperFactory.mapper();
	}

	@Override
	public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
		//@formatter:off
		configurer
		    .favorParameter(true)
		    .useRegisteredExtensionsOnly(false)
		    .defaultContentType(MediaType.APPLICATION_JSON)
			.mediaType("json", MediaType.APPLICATION_JSON);
		//@formatter:on
	}
}
