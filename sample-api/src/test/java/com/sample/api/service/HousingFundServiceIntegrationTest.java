package com.sample.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sample.api.IntegrationTest;
import com.sample.api.service.model.FundBankAvgMinMaxDTO;
import com.sample.api.service.model.FundTopBankDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HousingFundServiceIntegrationTest extends IntegrationTest {
	@Autowired
	private HousingFundService housingFundService;

	@Test
	public void findFundTopBank() throws Exception {
		FundTopBankDTO result = housingFundService.findFundTopBank();
		assertThat(result).isNotNull();
		assertThat(result.getBank()).isEqualTo("주택도시기금1)");
		assertThat(result.getYear()).isEqualTo(2016);
	}

	@Test
	public void findMaxMinAvgOfFundBank() throws Exception {
		FundBankAvgMinMaxDTO result = housingFundService.findMaxMinAvgOfFundBank("외환은행");
		log.debug("result === {}", result);
		assertThat(result).isNotNull();
		assertThat(result.getBank()).isEqualTo("외환은행");
		assertThat(result.getSupportAmount().get(0).getYear()).isEqualTo(2017);
		assertThat(result.getSupportAmount().get(0).getAmount()).isEqualTo(BigDecimal.valueOf(0L));
		assertThat(result.getSupportAmount().get(1).getYear()).isEqualTo(2015);
		assertThat(result.getSupportAmount().get(1).getAmount()).isEqualTo(BigDecimal.valueOf(1702L));
	}
}
