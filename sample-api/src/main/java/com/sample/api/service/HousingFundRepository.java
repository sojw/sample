package com.sample.api.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.api.service.entity.HousingFund;

@Repository
public interface HousingFundRepository extends JpaRepository<HousingFund, Long> {

	List<HousingFund> findByFundYear(Integer fundYear);

	List<HousingFund> findByBankSeq(Long bankSeq);
}