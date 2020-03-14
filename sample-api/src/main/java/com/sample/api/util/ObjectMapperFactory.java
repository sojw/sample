/*
 *
 *  * Copyright (c) 2018 LINE Corporation. All rights reserved.
 *  * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.sample.api.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class ObjectMapperFactory {
	public static final String ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSS";
	public static final DateTimeFormatter ISO8601_FORMATTER = DateTimeFormatter.ofPattern(ISO8601);

	/**
	 * Mapper.
	 *
	 * @return the object mapper
	 */
	public static ObjectMapper mapper() {
		return new ObjectMapper() {
			private static final long serialVersionUID = 7973322802508899534L;
			{
				JavaTimeModule javaTimeModule = new JavaTimeModule();
				javaTimeModule.addSerializer(LocalDateTime.class, new CustomLocalDateTimeSerializer());
				javaTimeModule.addSerializer(LocalDate.class, new CustomLocalDateSerializer());
				javaTimeModule.addDeserializer(LocalDateTime.class, new CustomLocalDateTimeDeserializer());
				javaTimeModule.addDeserializer(LocalDate.class, new CustomLocalDateDeserializer());

				registerModules(javaTimeModule);
				registerModules(new Jdk8Module());
				registerModules(new ParameterNamesModule());
				enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); //date -> timestamp로 보여주려면 주석처리하고 기본값을 사용해야함.
				enable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
				disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
				disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
				disable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE);
				disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
				disable(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS);
				enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				//				setDateFormat(new SimpleDateFormat(DATE_PATTERN));
			}
		};
	}

	public static class CustomLocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
		@Override
		public void serialize(final LocalDateTime value, final JsonGenerator gen, final SerializerProvider serializers)
			throws IOException {
			try {
				long ldt = value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
				gen.writeNumber(ldt);
			} catch (Exception e) {
				log.error("", e, e);
				gen.writeNull();
			}
		}
	}

	public static class CustomLocalDateSerializer extends JsonSerializer<LocalDate> {
		@Override
		public void serialize(final LocalDate value, final JsonGenerator gen, final SerializerProvider serializers)
			throws IOException {
			try {
				long ldt = Timestamp.valueOf(value.atStartOfDay()).getTime();
				gen.writeNumber(ldt);
			} catch (Exception e) {
				log.error("", e, e);
				gen.writeNull();
			}
		}
	}

	public static class CustomLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
		@Override
		public LocalDateTime deserialize(final JsonParser parser, final DeserializationContext context)
			throws IOException {
			try {
				return LocalDateUtils.toLocalDateTime(parser.getLongValue());
			} catch (Exception e) {
				log.error("", e, e);
				return null;
			}
		}
	}

	public static class CustomLocalDateDeserializer extends JsonDeserializer<LocalDate> {
		@Override
		public LocalDate deserialize(final JsonParser parser, final DeserializationContext context)
			throws IOException {
			try {
				return LocalDateUtils.toLocalDate(parser.getLongValue());
			} catch (Exception e) {
				log.error("", e, e);
				return null;
			}
		}
	}

	public static class CustomYearMonthSerializer extends JsonSerializer<LocalDate> {
		@Override
		public void serialize(final LocalDate value, final JsonGenerator gen, final SerializerProvider serializers)
			throws IOException {
			try {
				long ldt = Timestamp.valueOf(value.atStartOfDay()).getTime();
				gen.writeNumber(ldt);
			} catch (Exception e) {
				log.error("", e, e);
				gen.writeNull();
			}
		}
	}

	public static class IntegerDeserializer extends JsonDeserializer<Integer> {
		@Override
		public Integer deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext)
			throws IOException {
			try {
				String value = jsonParser.getValueAsString();
				if (StringUtils.isBlank(value)) {
					return null;
				}
				return Integer.valueOf(jsonParser.getValueAsString());
			} catch (Exception e) {
				log.info("Integer deserialize fail :: {}", jsonParser.getText());
				try {
					return new Double(jsonParser.getValueAsString()).intValue();
				} catch (Exception e1) {}
			}
			return null;
		}
	}

	public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

		@Override
		public void serialize(final LocalDateTime localDateTime, final JsonGenerator jsonGenerator,
			final SerializerProvider serializerProvider) throws IOException {
			jsonGenerator.writeString(localDateTime.format(ISO8601_FORMATTER));
		}
	}

	public static class BigDecimalConverter extends JsonDeserializer<BigDecimal> {
		@Override
		public BigDecimal deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext)
			throws IOException {
			try {
				String value = jsonParser.getValueAsString();
				if (StringUtils.isBlank(value)) {
					return null;
				}
				return new BigDecimal(value);
			} catch (Exception e) {
				log.info("BigDecimal deserialize fail :: {}", jsonParser.getText());
				return BigDecimal.ZERO;
			}
		}
	}
}
