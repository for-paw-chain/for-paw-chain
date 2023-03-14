package com.forpawchain;

import static com.forpawchain.domain.entity.Social.*;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.forpawchain.domain.entity.AdoptEntity;
import com.forpawchain.domain.entity.DoctorLicenseEntity;
import com.forpawchain.domain.entity.PetEntity;
import com.forpawchain.domain.entity.PetInfoEntity;
import com.forpawchain.domain.entity.PetRegEntity;
import com.forpawchain.domain.entity.Sex;
import com.forpawchain.domain.entity.Social;
import com.forpawchain.domain.entity.UserEntity;
import com.forpawchain.repository.AdoptRepository;
import com.forpawchain.repository.DoctorLicenseRepository;
import com.forpawchain.repository.PetInfoRepository;
import com.forpawchain.repository.PetRegRepository;
import com.forpawchain.repository.PetRepository;
import com.forpawchain.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestDataInit {

	private final DoctorLicenseRepository doctorLicenseRepository;
	private final PetRepository petRepository;
	private final UserRepository userRepository;
	private final AdoptRepository adoptRepository;
	private final PetRegRepository petRegRepository;
	private final PetInfoRepository petInfoRepository;

	/**
	 * 초기 데이터 추가
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void initData() {
		// 의사 면허 정보 추가 (실제 서비스에서는 정부 db로 대체됨)
		doctorLicenseRepository.save(new DoctorLicenseEntity("김의사", "1234561234567", "01012341234", 1));

		// 유저 추가
		UserEntity userEntity = UserEntity.builder()
			.id("123")    // 소셜 로그인 아이디
			.social(KAKAO)
			.name("김싸피")
			.del(false)
			.build();

		userRepository.save(userEntity);
		//
		// // 동물 정부 데이터 추가
		// PetRegEntity petRegEntity = PetRegEntity.builder()
		// 	.pid("410000000000000")
		// 	.sex(Sex.FEMALE)
		// 	.spayed(false)
		// 	.name("멍뭉이")
		// 	.type("type")
		// 	.kind("kind")
		// 	.build();
		//
		// petRegRepository.save(petRegEntity);

		// 동물 추가
		// PetEntity petEntity = PetEntity.builder()
		// 	.pid("410000000000000")
		// 	.ca("12312")
		// 	.lost(false)
		// 	// .petInfo()
		// 	// .petReg()
		// 	// .adopt()
		// 	// .authList()
		// 	.build();

		PetEntity petEntity = new PetEntity();
		petEntity.setPid("410000000000000");
		petEntity.setCa("23123");
		petEntity.setLost(false);


		// System.out.println("----------------");
		System.out.println("----------------");
		// System.out.println(petRepository.findByPid("410000000000000").getCa());
		// System.out.println(adoptRepository.findByPid("410000000000000").getPid());

		// 유기견 추가
		AdoptEntity adoptEntity = new AdoptEntity();
		adoptEntity.setPid("410000000000000");
		adoptEntity.setUid(1);
		adoptEntity.setProfile1("https://images.mypetlife.co.kr/content/uploads/2021/10/22152410/IMG_2087-scaled-e1634883900174-1024x739.jpg");

		// AdoptEntity adoptEntity = AdoptEntity.builder()
		// 	.pid("410000000000000")
		// 	.uid(1)
		// 	.profile1(
		// 		"https://images.mypetlife.co.kr/content/uploads/2021/10/22152410/IMG_2087-scaled-e1634883900174-1024x739.jpg")
		// 	.pet(petEntity)
		// 	.build();

		// petEntity.setAdopt(adoptEntity);
		System.out.println("----------------");
		// adoptEntity.setPet(petEntity);
		System.out.println("----------------");
		adoptEntity.setUser(userRepository.findByUid(1));
		System.out.println("----------------");

		petRepository.save(petEntity);
		System.out.println("----------------");
		adoptRepository.save(adoptEntity);


		// PetInfoEntity petInfoEntity = PetInfoEntity.builder()
		// 	.pid("410000000000000")
		// 	.build();
		//
		// petInfoRepository.save(petInfoEntity);
	}
}
