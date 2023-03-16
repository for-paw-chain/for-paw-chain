package com.forpawchain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.forpawchain.domain.entity.PetRegEntity;
import com.forpawchain.repository.AdoptRepository;
import com.forpawchain.repository.DoctorLicenseRepository;
import com.forpawchain.repository.PetInfoRepository;
import com.forpawchain.repository.PetRegRepository;
import com.forpawchain.repository.PetRepository;
import com.forpawchain.repository.UserRepository;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	@Bean
	public TestDataInit testDataInit(DoctorLicenseRepository doctorLicenseRepository, PetRepository petRepository,
		UserRepository userRepository, AdoptRepository adoptRepository, PetRegRepository petRegRepository, PetInfoRepository petInfoRepository) {
		return new TestDataInit(doctorLicenseRepository, petRepository, userRepository, adoptRepository, petRegRepository, petInfoRepository);
	}
}
