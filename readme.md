

# sample-api

- host : http://localhost:8080
- framework
  - java 8
  - spring, springboot
  - mysql
- build
  - maven clean install
- run
  - java -j sample-api-1.0.0-SNAPSHOT.jar.jar


## api list

- swagger : /swagger-ui.html#/

- /housing-fund-imoporter
  - csv file을 parsing.
  - header정보를 토대로 bank 정보 생성.
  - 나머지 record 정보에서 연도/월 정보와 은행 펀딩총액 정보를 분리해서, 연산
  - 연도/월 정보 + 개별 은행 펀딩 총액 정보로 월별 펀딩 정보를 저장.


- /fund-bank
  - csv file의 header정보를 토대로 bank 생성 된 은행 정보를 반환.


- /fund-amount-sum
  - HousingFund table의 모든 data를 list반환
  - 연도(fundYear)로 stream grouping 처리
  - (1) 전체 총액
  - (2) 금융기관별 총액

- /fund-top-bank
  - HousingFund table의 모든 data를 list반환
  - 연도(fundYear) + 금융기관code(bankSeq)로 stream grouping 처리
  - 해당 연도의 금융기관의 펀딩총액을 list로 변환.
  - 가장 큰 펀딩총액을 stream max로 반환.


- /fund-bank-avg
  - 외환은행의 모든 data를 list반환
  - 연도별 펀딩 평균값 list 생성
  - 평균값 기준으로 연도별 펀딩 평균값 list sort처리
  - list 가장 처음(min), 가장 마지막(max) element를 반환


## database

- Bank table
  
```
CREATE TABLE `Bank` (
  `bankSeq` bigint(11) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `bankName` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`bankSeq`),
  KEY `idx_bankName` (`bankName`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;
```

- HousingFund table

```
CREATE TABLE `HousingFund` (
  `housingFundSeq` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `bankSeq` bigint(20) unsigned NOT NULL,
  `fundYear` int(4) NOT NULL,
  `fundMonth` int(4) NOT NULL,
  `fundAmount` double NOT NULL,
  PRIMARY KEY (`housingFundSeq`),
  KEY `idx_fundYear` (`fundYear`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;
```

  
