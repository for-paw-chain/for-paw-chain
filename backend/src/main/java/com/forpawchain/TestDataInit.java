package com.forpawchain;

import static com.forpawchain.domain.entity.Sex.*;
import static com.forpawchain.domain.entity.Social.*;
import static com.forpawchain.domain.entity.Type.*;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.forpawchain.domain.entity.AdoptEntity;
import com.forpawchain.domain.entity.DoctorLicenseEntity;
import com.forpawchain.domain.entity.PetEntity;
import com.forpawchain.domain.entity.PetInfoEntity;
import com.forpawchain.domain.entity.PetRegEntity;
import com.forpawchain.domain.entity.Sex;
import com.forpawchain.domain.entity.Social;
import com.forpawchain.domain.entity.Type;
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
		doctorLicenseRepository.save(new DoctorLicenseEntity(1L, "김의사", "1234561234567", "01012341234", 1));

		// 유저 추가
		UserEntity userEntity = UserEntity.builder()
			.id("123")    // 소셜 로그인 아이디
			.social(KAKAO)
			.name("김싸피")
			.del(false)
			.build();

		userRepository.save(userEntity);

		// 동물 정보 50개 추가
		for (int i = 0; i < 50; i++) {
			// 동물 정부 데이터 추가
			PetRegEntity petRegEntity = null;
			if (i % 4 == 0) {
				petRegEntity = PetRegEntity.builder()
					.pid("4100000000000" + Integer.toString(i))
					.sex(FEMALE)
					.spayed(false)
					.name("멍뭉이" + Integer.toString(i))
					.type(DOG)
					.kind("푸들")
					.build();
			} else if (i % 4 == 1) {
				petRegEntity = PetRegEntity.builder()
					.pid("4100000000000" + Integer.toString(i))
					.sex(FEMALE)
					.spayed(true)
					.name("야옹이" + Integer.toString(i))
					.type(CAT)
					.kind("푸들")
					.build();
			} else if (i % 4 == 2) {
				petRegEntity = PetRegEntity.builder()
					.pid("4100000000000" + Integer.toString(i))
					.sex(MALE)
					.spayed(false)
					.name("돼지" + Integer.toString(i))
					.type(ETC)
					.kind("리본돼지")
					.build();
			} else if (i % 4 == 3) {
				petRegEntity = PetRegEntity.builder()
					.pid("4100000000000" + Integer.toString(i))
					.sex(MALE)
					.spayed(true)
					.name("멍뭉이" + Integer.toString(i))
					.type(DOG)
					.kind("리트리버")
					.build();
			}


			petRegRepository.save(petRegEntity);

			// 동물(Pet) 추가
			PetEntity petEntity = PetEntity.builder()
				.pid("4100000000000" + Integer.toString(i))
				.ca("12312")
				.lost(false)
				// .petInfo()
				// .petReg()
				// .adopt()
				// .authList()
				.build();

			petRepository.save(petEntity);

			// 유기견 추가
			if (i % 3 == 1) {
				AdoptEntity adoptEntity = AdoptEntity.builder()
					.pid("4100000000000" + Integer.toString(i))
					.uid(1L)
					.profile1(
						"https://images.mypetlife.co.kr/content/uploads/2021/10/22152410/IMG_2087-scaled-e1634883900174-1024x739.jpg")
					.pet(petEntity)
					.user(userRepository.findByUid(1))
					.build();

				petEntity.updatePetLost(true);

				adoptRepository.save(adoptEntity);
			}

			petRepository.save(petEntity);



		}

		// PetInfoEntity petInfoEntity = PetInfoEntity.builder()
		// 	.pid("410000000000000")
		// 	.build();
		//
		// petInfoRepository.save(petInfoEntity);
	}
}
