/*
 *
 *  * Copyright (c) 2018 LINE Corporation. All rights reserved.
 *  * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.sample.api.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	private static final String BASE_PACKAGE = "com.sample.api.controller";

//	@Value("${swagger.host}")
//	private String host;
//
//	@Value("${swagger.path}")
//	private String path;

	@Bean
	public Docket apiVersion2(final TypeResolver typeResolver) {
		return docketVersionBuilder(1, 1, typeResolver);
	}

	private Docket docketVersionBuilder(final int version, final int order, final TypeResolver typeResolver) {
		Set<String> defaultProduces = Sets.newHashSet(MediaType.APPLICATION_JSON_UTF8_VALUE);

		List<Parameter> defaultParams = Lists.newArrayList();

		return new Docket(DocumentationType.SWAGGER_2)
			.alternateTypeRules(
				AlternateTypeRules.newRule(typeResolver.resolve(List.class, LocalDate.class),
					typeResolver.resolve(List.class, String.class)))
			.alternateTypeRules(
				AlternateTypeRules.newRule(typeResolver.resolve(List.class, LocalDateTime.class),
					typeResolver.resolve(List.class, String.class)))
			.produces(defaultProduces)
			.globalOperationParameters(defaultParams)
			.useDefaultResponseMessages(false)
			.groupName(order + ". SAMPLE API v" + version)
			.select()
			.apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
			.paths(PathSelectors.ant("/**")).build()
//			.host(host)
			.apiInfo(apiInfo())
			.enableUrlTemplating(false);
	}

	/**
	 * Ui config.
	 *
	 * @return the ui configuration
	 */
	@Bean
	UiConfiguration uiConfig() {
		return UiConfigurationBuilder.builder()
			.validatorUrl("")
			.build();
	}

	/**
	 * Api info.
	 *
	 * @return the api info
	 */
	private ApiInfo apiInfo() {
		ApiInfoBuilder builder = new ApiInfoBuilder();
		return builder.title("SAMPLE API")
			.description("")
			.version("1.0")
			.build();
	}
}