package com.sample.api.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Test;

public class LocalDateUtilsTest {

	@Test
	public void toLocalDate() {
		LocalDate actual = LocalDateUtils.toLocalDate(1584186197000L);
		assertThat(actual.isEqual(LocalDate.of(2020, 3, 14))).isTrue();
	}

	@Test
	public void toLocalDateTime() {
		LocalDateTime actual = LocalDateUtils.toLocalDateTime(1584186197000L);
		assertThat(actual.isEqual(LocalDateTime.of(2020, 3, 14, 20, 43, 17))).isTrue();
	}

	@Test
	public void getEpochMilli() {
		long actual = LocalDateUtils.getEpochMilli(LocalDateTime.of(2020, 3, 14, 20, 43, 17));
		assertThat(actual).isEqualTo(1584186197000L);
	}
}