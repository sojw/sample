package com.sample.api.service.model;

import java.math.BigDecimal;
import java.util.Objects;

import com.sample.api.service.entity.HousingFund;
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
public class HousingFundDTO {
	private Long housingFundSeq;
	private Long bankSeq;
	private Integer fundYear;
	private Integer fundMonth;
	private BigDecimal fundAmount;

	public static HousingFundDTO of(HousingFund housingFund) {
		return Objects.isNull(housingFund) ? null : ModelMapperUtils.getMapper().map(housingFund, HousingFundDTO.class);
	}

	public static HousingFund to(HousingFundDTO housingFundDTO) {
		return Objects.isNull(housingFundDTO) ? null
			: ModelMapperUtils.getMapper().map(housingFundDTO, HousingFund.class);
	}
}