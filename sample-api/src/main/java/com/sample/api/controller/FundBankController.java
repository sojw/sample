package com.sample.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.api.service.BankService;
import com.sample.api.service.HousingFundService;
import com.sample.api.service.model.BankDTO;
import com.sample.api.service.model.FundBankAvgMinMaxDTO;
import com.sample.api.service.model.FundTopBankDTO;
import com.sample.api.service.model.FundYearAmountDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FundBankController {
	private final BankService bankService;
	private final HousingFundService housingFundService;

	@GetMapping({"/fund-bank"})
	public ResponseEntity<List<BankDTO>> findAll() throws Exception {
		return ResponseEntity.ok(bankService.findAll());
	}

	@GetMapping({"/fund-amount-sum"})
	public ResponseEntity<List<FundYearAmountDTO>> findStatByFundYear() throws Exception {
		return ResponseEntity.ok(housingFundService.findStatByFundYear());
	}

	@GetMapping({"/fund-top-bank"})
	public ResponseEntity<FundTopBankDTO> findFundTopBank() throws Exception {
		return ResponseEntity.ok(housingFundService.findFundTopBank());
	}

	@GetMapping({"/fund-bank-avg"})
	public ResponseEntity<FundBankAvgMinMaxDTO> findMaxMinAvgOfFundBank() throws Exception {
		return ResponseEntity.ok(housingFundService.findMaxMinAvgOfFundBank("외환은행"));
	}
}