package com.forpawchain;

import static com.forpawchain.domain.entity.Social.*;

import com.forpawchain.domain.entity.*;
import com.forpawchain.repository.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class TestDataInit {

	private final DoctorLicenseRepository doctorLicenseRepository;
	private final PetRepository petRepository;
	private final UserRepository userRepository;
	private final AdoptRepository adoptRepository;
	private final PetRegRepository petRegRepository;
	private final PetInfoRepository petInfoRepository;
	private final AuthenticationRepository authenticationRepository;

	/**
	 * 초기 데이터 추가
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void initData() {
		// 의사 면허 정보 추가 (실제 서비스에서는 정부 db로 대체됨)
//		doctorLicenseRepository.save(new DoctorLicenseEntity(1L, "김의사", "1234561234567", "01012341234", 1));
//
//		// 유저 추가
//		UserEntity userEntity = UserEntity.builder()
//			.id("123")    // 소셜 로그인 아이디
//			.social(KAKAO)
//			.name("김싸피")
//			.del(false)
//			.build();
//
//		userRepository.save(userEntity);
//
//		// 동물 정보 20개 추가
//		for (int i = 0; i < 20; i++) {
//			// 동물 정부 데이터 추가
//			PetRegEntity petRegEntity = PetRegEntity.builder()
//				.pid("4100000000000" + Integer.toString(i))
//				.sex(Sex.FEMALE)
//				.spayed(false)
//				.name("멍뭉이" + Integer.toString(i))
//				.type(Type.DOG)
//				.kind("kind")
//				.build();
//
//			petRegRepository.save(petRegEntity);
//
//			// 동물(Pet) 추가
//			PetEntity petEntity = PetEntity.builder()
//				.pid("4100000000000" + Integer.toString(i))
//				.ca("12312")
//				.lost(true)
//				// .petInfo()
//				// .petReg()
//				// .adopt()
//				// .authList()
//				.build();
//
//			petRepository.save(petEntity);
//
//			// 유기견 추가
//			AdoptEntity adoptEntity = AdoptEntity.builder()
//				.pid("4100000000000" + Integer.toString(i))
//				.uid(1L)
//				.profile1(
//					"https://images.mypetlife.co.kr/content/uploads/2021/10/22152410/IMG_2087-scaled-e1634883900174-1024x739.jpg")
//				.build();
//
//			adoptRepository.save(adoptEntity);
//		}

		// PetInfoEntity petInfoEntity = PetInfoEntity.builder()
		// 	.pid("410000000000000")
		// 	.build();
		//
		// petInfoRepository.save(petInfoEntity);

		AuthenticationId id = new AuthenticationId();
		id.setPid("41000000000000");
		id.setUid(1);

		PetEntity petEntity = petRepository.findByPid("41000000000000");
		UserEntity userEntity = userRepository.findByUid(1);


		AuthenticationEntity authEntity = AuthenticationEntity
				.builder()
				.aid(id)
				.regTime(LocalDate.now())
				.type(AuthenticationType.MASTER)
				.pet(petEntity)
				.user(userEntity)
				.build();
 		authenticationRepository.save(authEntity);

//		List<AuthenticationEntity> auths = authenticationRepository.findAllByAid(id);
//		auths = authenticationRepository.findAllByUid(1);
//
//
//		AuthenticationEntity entity = AuthenticationEntity
//				.builder()
//				.aid(id)
//				.type(AuthenticationType.MASTER)
//				.regTime(LocalDate.now())
//				.build();
//		authenticationRepository.save(entity);
	}
}
