package com.sample.api.service.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FundYearAmountDTO {
	private Integer year;
	private BigDecimal totalAmount;
	private List<Map<String, BigDecimal>> detailAmount;
}