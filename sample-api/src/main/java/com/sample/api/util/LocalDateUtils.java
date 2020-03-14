package com.sample.api.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import lombok.experimental.UtilityClass;


@UtilityClass
public class LocalDateUtils {

	private static final ZoneId ZONE_ID = ZoneId.of(ZoneId.SHORT_IDS.get("JST"));

	public static LocalDate toLocalDate(Long epochMilli) {
		return Instant.ofEpochMilli(epochMilli).atZone(ZONE_ID).toLocalDate();
	}

	public static LocalDateTime toLocalDateTime(Long epochMilli) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), ZONE_ID);
	}

	public static long getEpochMilli(LocalDateTime localDateTime) {
		return localDateTime.atZone(ZONE_ID).toInstant().toEpochMilli();
	}
}
