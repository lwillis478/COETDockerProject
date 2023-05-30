package com.daftgoods.daftgoodsservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class DaftGoodsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaftGoodsServiceApplication.class, args);
	}

	@Bean
	public Logger getLogger()
	{
		return LoggerFactory.getLogger(DaftGoodsServiceApplication.class);
	}

	@Bean
	public BCryptPasswordEncoder getHasher()
	{
		return new BCryptPasswordEncoder(10);
	}

}
