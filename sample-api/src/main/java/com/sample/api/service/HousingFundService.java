package com.sample.api.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sample.api.service.entity.HousingFund;
import com.sample.api.service.model.BankDTO;
import com.sample.api.service.model.FundBankAvgMinMaxDTO;
import com.sample.api.service.model.FundBankAvgMinMaxDTO.SupportAmount;
import com.sample.api.service.model.FundTopBankDTO;
import com.sample.api.service.model.FundYearAmountDTO;
import com.sample.api.service.model.HousingFundDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HousingFundService {
	private final BankService bankService;
	private final HousingFundRepository housingFundRepository;

	public HousingFundDTO findById(final Long housingFundSeq) {
		return HousingFundDTO.of(housingFundRepository.findById(housingFundSeq).orElse(null));
	}

	@Transactional
	public Long save(final HousingFundDTO housingFundDTO) {
		HousingFund saved = housingFundRepository.save(HousingFundDTO.to(housingFundDTO));
		return Objects.isNull(saved) ? null : saved.getBankSeq();
	}

	public List<HousingFundDTO> findAll() {
		List<HousingFund> allList = housingFundRepository.findAll();
		return allList.stream().map(HousingFundDTO::of).collect(Collectors.toList());
	}

	public List<FundYearAmountDTO> findStatByFundYear() {
		List<HousingFund> allList = housingFundRepository.findAll();
		List<FundYearAmountDTO> result = allList.stream()
			.collect(Collectors.groupingBy(HousingFund::getFundYear))
			.entrySet().stream().map(entryByYear -> {

				BigDecimal amountSum = entryByYear.getValue().stream().map(HousingFund::getFundAmount)
					.reduce(BigDecimal.ZERO, BigDecimal::add);

				List<Map<String, BigDecimal>> detailList = entryByYear.getValue().stream()
					.collect(Collectors.groupingBy(HousingFund::getBankSeq)).entrySet()
					.stream().map(detail -> {

						Map<String, BigDecimal> detailMap = Maps.newHashMap();

						BankDTO bankDTO = bankService.findById(detail.getKey());
						detailMap.put(bankDTO.getBankName(), detail.getValue().stream().map(HousingFund::getFundAmount)
							.reduce(BigDecimal.ZERO, BigDecimal::add));

						return detailMap;
					}).collect(Collectors.toList());

				return FundYearAmountDTO.builder()
					.year(entryByYear.getKey())
					.totalAmount(amountSum)
					.detailAmount(detailList)
					.build();
			}).collect(Collectors.toList());

		return result;

	}

	public FundTopBankDTO findFundTopBank() {
		List<HousingFund> housingFundList = housingFundRepository.findAll();

		HousingFundDTO fundTopBank = housingFundList.stream()
			.collect(Collectors.groupingBy(HousingFund::getFundYear, Collectors.groupingBy(HousingFund::getBankSeq)))
			.entrySet().stream().map(entryByYear -> {

				return entryByYear.getValue().entrySet().stream().map(fundYear -> {

					HousingFund housingFund = fundYear.getValue().stream().findFirst().get();
					BigDecimal amountSum = fundYear.getValue().stream().map(HousingFund::getFundAmount)
						.reduce(BigDecimal.ZERO, BigDecimal::add);

					return HousingFundDTO.builder().bankSeq(housingFund.getBankSeq()).fundAmount(amountSum)
						.fundYear(housingFund.getFundYear()).build();
				}).max(Comparator.comparing(HousingFundDTO::getFundAmount)).get();
			}).findFirst().get();

		if (Objects.isNull(fundTopBank)) {
			return null;
		}

		BankDTO bankDTO = bankService.findById(fundTopBank.getBankSeq());
		return new FundTopBankDTO(fundTopBank.getFundYear(), bankDTO.getBankName());
	}

	public FundBankAvgMinMaxDTO findMaxMinAvgOfFundBank(String bankName) {
		BankDTO bankDTO = bankService.findByBankName(bankName);

		List<HousingFund> housingFundList = housingFundRepository.findByBankSeq(bankDTO.getBankSeq());

		List<Pair<Integer, BigDecimal>> averageList = housingFundList.stream()
			.collect(Collectors.groupingBy(HousingFund::getFundYear))
			.entrySet().stream().map(entryByYear -> {

				Double average = entryByYear.getValue().stream()
					.map(HousingFund::getFundAmount)
					.mapToDouble(BigDecimal::doubleValue)
					.average().getAsDouble();
				return Pair.of(entryByYear.getKey(), BigDecimal.valueOf(average));
			}).sorted(Comparator.comparing(Pair::getSecond)).collect(Collectors.toList());

		Pair<Integer, BigDecimal> min = averageList.get(0);
		Pair<Integer, BigDecimal> max = averageList.get(averageList.size() - 1);

		return new FundBankAvgMinMaxDTO(bankName, Lists.newArrayList(min, max).stream()
			.map(item -> new SupportAmount(item.getFirst(), item.getSecond().setScale(0, RoundingMode.HALF_EVEN)))
			.collect(Collectors.toList()));
	}
}