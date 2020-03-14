package com.sample.api.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sample.api.service.entity.Bank;
import com.sample.api.service.model.BankDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankService {
	private final BankRepository bankRepository;

	public BankDTO findById(final Long bankSeq) {
		return BankDTO.of(bankRepository.findById(bankSeq).orElse(null));
	}

	public BankDTO findByBankName(final String bankName) {
		return BankDTO.of(bankRepository.findByBankName(bankName));
	}

	@Transactional
	public Long save(final BankDTO bankDTO) {
		Bank saved = bankRepository.save(BankDTO.to(bankDTO));
		return Objects.isNull(saved) ? null : saved.getBankSeq();
	}

	public List<BankDTO> findAll() {
		List<Bank> allList = bankRepository.findAll();
		return allList.stream().map(BankDTO::of).collect(Collectors.toList());
	}
}