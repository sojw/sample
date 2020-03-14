package com.sample.api.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.sample.api.service.entity.Bank;
import com.sample.api.service.model.BankDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class BankServiceTest {
	@InjectMocks
	private BankService bankService;

	@Mock
	private BankRepository bankRepository;

	@Test
	public void saveAndFind() {

		BankDTO requestBankDTO = BankDTO.builder().build();

		Bank expectBank = BankDTO.to(requestBankDTO);
		expectBank.setBankSeq(123L);

		when(bankRepository.save(any(Bank.class))).thenReturn(expectBank);

		Long newSeq = bankService.save(requestBankDTO);
		log.debug("newSeq === {}", newSeq);
		verify(bankRepository).save(any(Bank.class));
	}
}
