package com.forpawchain;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.forpawchain.repository.AdoptRepository;
import com.forpawchain.repository.DoctorLicenseRepository;
import com.forpawchain.repository.PetInfoRepository;
import com.forpawchain.repository.PetRegRepository;
import com.forpawchain.repository.PetRepository;
import com.forpawchain.repository.UserRepository;
import com.forpawchain.service.AuthenticationServiceImpl;
import com.forpawchain.service.Web3ServiceImpl;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class ForpawchainApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForpawchainApplication.class, args);

		String date = "2023-1-28";
		String[] dateList = date.split("-");

		String year = dateList[0];
		String month = dateList[1];
		String day = dateList[2];

		val past: LocalDate = LocalDate.of(year.toInt(), month.toInt(), day.toInt())
		val now: LocalDate = LocalDate.now()
		val days: Long = ChronoUnit.DAYS.between(past, now)
	}

	 // @Bean
	 // public TestDataInit testDataInit(DoctorLicenseRepository doctorLicenseRepository, PetRepository petRepository,
	 // 	UserRepository userRepository, AdoptRepository adoptRepository, PetRegRepository petRegRepository,
	 // 	PetInfoRepository petInfoRepository, AuthenticationServiceImpl authenticationService, Web3ServiceImpl web3Service) {
	 // 	return new TestDataInit(doctorLicenseRepository, petRepository, userRepository, adoptRepository,
	 // 		petRegRepository, petInfoRepository, authenticationService, web3Service);
	 // }
}
