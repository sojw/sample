package com.sample.api.service.model;

import java.util.Objects;

import com.sample.api.service.entity.Bank;
import com.sample.api.util.ModelMapperUtils;

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
public class BankDTO {
	private Long bankSeq;
	private String bankName;

	public static BankDTO of(Bank bank) {
		return Objects.isNull(bank) ? null : ModelMapperUtils.getMapper().map(bank, BankDTO.class);
	}

	public static Bank to(BankDTO bank) {
		return Objects.isNull(bank) ? null : ModelMapperUtils.getMapper().map(bank, Bank.class);
	}
}