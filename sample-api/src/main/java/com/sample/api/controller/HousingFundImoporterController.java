package com.sample.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.api.service.HousingFundImporter;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HousingFundImoporterController {
	private final HousingFundImporter housingFundImporter;

	@PostMapping({"/housing-fund-imoporter"})
	public ResponseEntity<Boolean> imoporter() throws Exception {
		return ResponseEntity.ok(housingFundImporter.migrationToDB());
	}
}
