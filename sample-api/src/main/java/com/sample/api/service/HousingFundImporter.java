package com.sample.api.service;

import java.io.Reader;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sample.api.service.model.BankDTO;
import com.sample.api.service.model.HousingFundDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HousingFundImporter {
	@Autowired
	private ResourceLoaderSupporter resourceLoaderUtil;
	@Autowired
	private BankService bankService;
	@Autowired
	private HousingFundService housingFundService;

	@Transactional
	public boolean migrationToDB() throws Exception {
		try (Reader reader = resourceLoaderUtil.getReader("classpath:data.csv")) {
			CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader());

			List<Long> bankSeqList = createBank(parser);

			int dateDataSize = 2;
			int columSize = dateDataSize + bankSeqList.size();

			parser.getRecords().forEach(record -> {
				int fundYear = Integer.parseInt(record.get("연도"));
				int fundMonth = Integer.parseInt(record.get("월"));

				for (int i = dateDataSize, j = 0; i < columSize; i++, j++) {
					HousingFundDTO housingFundDTO = HousingFundDTO.builder()
						.bankSeq(bankSeqList.get(j))
						.fundAmount(new BigDecimal(record.get(i).replaceAll("\\,", StringUtils.EMPTY)))
						.fundYear(fundYear)
						.fundMonth(fundMonth).build();
					housingFundService.save(housingFundDTO);
				}
			});
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}

		return true;
	}

	private List<Long> createBank(CSVParser parser) {
		List<Long> bankSeqList = parser.getHeaderNames().stream().skip(2)
			.filter(StringUtils::isNotBlank)
			.map(item -> {
				return bankService
					.save(BankDTO.builder().bankName(StringUtils.remove(item, "(억원)").trim()).build());
			}).collect(Collectors.toList());
		return bankSeqList;
	}
}