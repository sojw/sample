package com.sample.api.service.model;

import java.math.BigDecimal;
import java.util.List;

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
public class FundBankAvgMinMaxDTO {
	private String bank;
	private List<SupportAmount> supportAmount;

	@ToString
	@Getter
	@Setter
	@AllArgsConstructor
	public static class SupportAmount {
		private Integer year;
		private BigDecimal amount;
	}
}