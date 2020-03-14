package com.sample.api.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.sample.api.SampleApiConfigMarker;
import com.sample.api.service.EntityMarker;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing
@EntityScan(basePackageClasses = EntityMarker.class)
@EnableJpaRepositories(basePackageClasses = EntityMarker.class, transactionManagerRef = "sampleApiTransactionManager", entityManagerFactoryRef = "sampleApiEntityManager")
@ComponentScan(basePackageClasses = SampleApiConfigMarker.class)
public class DataSourceConfig {
	private static final String PERSISTENT_UNIT = "SAMPLE-API";
//	private static final String DRIVER_CLASSNAME = "net.sf.log4jdbc.sql.jdbcapi.DriverSpy";

//	@Primary
//	@Bean(name = "sampleApiDataSource")
//	@ConfigurationProperties(prefix = "spring.datasource.hikari")
//	public DataSource dataSource() throws Exception {
//		return DataSourceBuilder.create()
//			.type(HikariDataSource.class)
////			.driverClassName(DRIVER_CLASSNAME)
//			.build();
//	}

	@Primary
	@Bean(name = "sampleApiDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public DataSource dataSource() throws Exception {
		 return new HikariDataSource();
	}
	
	@Primary
	@Bean(name = "sampleApiEntityManager")
	public LocalContainerEntityManagerFactoryBean pfmEntityManager(EntityManagerFactoryBuilder builder,
		@Qualifier("sampleApiDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource)
			.packages(EntityMarker.class)
			.persistenceUnit(PERSISTENT_UNIT)
			.build();
	}

	@Primary
	@Bean(name = "sampleApiTransactionManager")
	public PlatformTransactionManager pfmTransactionManager(
		@Qualifier("sampleApiEntityManager") EntityManagerFactory pfmEntityManager) {
		return new JpaTransactionManager(pfmEntityManager);
	}
}