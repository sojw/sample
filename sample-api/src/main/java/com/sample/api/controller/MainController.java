package com.sample.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
	private final String OK = "ok";

	@GetMapping({"/"})
	public ResponseEntity<String> main() {
		return ResponseEntity.ok(OK);
	}
}
