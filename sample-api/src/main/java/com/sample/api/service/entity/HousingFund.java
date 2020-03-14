package com.sample.api.service.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class HousingFund {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long housingFundSeq;

	@Column
	private Long bankSeq;

	@Column
	private Integer fundYear;

	@Column
	private Integer fundMonth;

	@Column
	private BigDecimal fundAmount;
}