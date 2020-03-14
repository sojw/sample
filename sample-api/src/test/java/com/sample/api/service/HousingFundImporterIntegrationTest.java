package com.sample.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sample.api.IntegrationTest;

public class HousingFundImporterIntegrationTest extends IntegrationTest {
	@Autowired
	private HousingFundImporter housingFundImporter;

	@Test
	public void migrationToDB() throws Exception {
		boolean result = housingFundImporter.migrationToDB();
		assertThat(result).isTrue();
	}
}
