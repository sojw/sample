package com.sample.api.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.api.service.entity.Bank;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
	public Bank findByBankName(String bankName);
}