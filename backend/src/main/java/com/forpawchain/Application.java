package com.forpawchain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.forpawchain.repository.DoctorLicenseRepository;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	@Bean
	public TestDataInit testDataInit(DoctorLicenseRepository doctorLicenseRepository) {
		return new TestDataInit(doctorLicenseRepository);
	}

}
