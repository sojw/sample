package com.sample.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sample.api.IntegrationTest;
import com.sample.api.service.BankService;
import com.sample.api.service.model.BankDTO;

public class BankServiceIntegrationTest extends IntegrationTest {
	@Autowired
	private BankService bankService;

	@Test
	public void saveAndFind() {
		BankDTO dummyBank = BankDTO.builder().bankName("test bank").build();
		Long dummyBankSeq = bankService.save(dummyBank);
		BankDTO selectBank = bankService.findById(dummyBankSeq);
		assertThat(dummyBankSeq).isEqualTo(selectBank.getBankSeq());
		assertThat(bankService.findAll().size()).isGreaterThan(0);
	}
}
