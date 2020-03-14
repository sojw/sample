

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
csv file을 parsing.
header정보를 토대로 bank 정보 생성.
나머지 record 정보에서 연도/월 정보와 은행 펀딩총액 정보를 분리해서, 연산
연도/월 정보 + 개별 은행 펀딩 총액 정보로 월별 펀딩 정보를 저장.


- /fund-bank
csv file의 header정보를 토대로 bank 생성 된 은행 정보를 반환.


- /fund-amount-sum


- /fund-top-bank


- /fund-bank-avg



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

  
