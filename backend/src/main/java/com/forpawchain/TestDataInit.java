package com.forpawchain;

import static com.forpawchain.domain.entity.Sex.*;
import static com.forpawchain.domain.entity.Social.*;
import static com.forpawchain.domain.entity.Type.*;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.web3j.crypto.CipherException;

import com.forpawchain.domain.dto.request.LicenseReqDto;
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
import com.forpawchain.repository.AuthenticationRepository;
import com.forpawchain.repository.DoctorLicenseRepository;
import com.forpawchain.repository.PetInfoRepository;
import com.forpawchain.repository.PetRegRepository;
import com.forpawchain.repository.PetRepository;
import com.forpawchain.repository.UserRepository;
import com.forpawchain.service.AuthenticationService;
import com.forpawchain.service.Web3Service;

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
	private final AuthenticationService authenticationService;
	private final Web3Service web3Service;

	/**
	 * 초기 데이터 추가
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void initData() throws
		InvalidAlgorithmParameterException,
		CipherException,
		IOException,
		NoSuchAlgorithmException,
		NoSuchProviderException {
		// 의사 면허 정보 추가 (실제 서비스에서는 정부 db로 대체됨)
		doctorLicenseRepository.save(new DoctorLicenseEntity(1L, "김의사", "1234561234567", "01012341234", 1));
		doctorLicenseRepository.save(new DoctorLicenseEntity(2L, "이리나", "1234561234567", "01012341234", 1));

		// 유저 추가
		String[] names = new String[6];
		names[0] = "이리나";
		names[1] = "최진우";
		names[3] = "김민소";
		names[4] = "최준아";
		names[5] = "윤혜진";
		names[6] = "이현정";

		for (int i = 0; i < 6; i++) {
			UserEntity userEntity = UserEntity.builder()
				.id("123")    // 소셜 로그인 아이디
				.social(KAKAO)
				.name(names[i])
				.del(false)
				.build();
			userRepository.save(userEntity);
		}

		// uid 6L 회원을 의사로
		web3Service.createWallet(6L, new LicenseReqDto("이리나", "1234561234567", "01012341234", 1));

		// 동물 정보 50개 추가
		for (int i = 0; i < 50; i++) {

			String petImgUrl = "";

			// 동물 정부 데이터 추가
			PetRegEntity petRegEntity = null;
			if (i % 10 == 0) {
				petRegEntity = PetRegEntity.builder()
					.pid("4100000000000" + Integer.toString(i))
					.sex(FEMALE)
					.spayed(false)
					.name("김엘리자베스")
					.type(DOG)
					.kind("푸들")
					.build();
				petImgUrl = "https://images.mypetlife.co.kr/content/uploads/2023/02/03094318/AdobeStock_366413112-1024x682.jpeg";
			} else if (i % 10 == 1) {
				petRegEntity = PetRegEntity.builder()
					.pid("4100000000000" + Integer.toString(i))
					.sex(FEMALE)
					.spayed(true)
					.name("똥똥이")
					.type(CAT)
					.kind("메인쿤")
					.build();
				petImgUrl = "https://images.mypetlife.co.kr/content/uploads/2021/05/26132237/lina-angelov-1vNvGY11Lds-unsplash-1024x683.jpg";
			} else if (i % 10 == 2) {
				petRegEntity = PetRegEntity.builder()
					.pid("4100000000000" + Integer.toString(i))
					.sex(MALE)
					.spayed(false)
					.name("최진우")
					.type(ETC)
					.kind("리본돼지")
					.build();
				petImgUrl = "https://previews.123rf.com/images/baloncici/baloncici1212/baloncici121200227/17036824-%EB%8D%94%EB%9F%AC%EC%9A%B4-%EC%BD%94%EC%99%80-%EB%A6%AC%EB%B3%B8-%ED%95%A8%EA%BB%98-%EC%9E%91%EC%9D%80-%EB%8F%BC%EC%A7%80.jpg";
			} else if (i % 10 == 3) {
				petRegEntity = PetRegEntity.builder()
					.pid("4100000000000" + Integer.toString(i))
					.sex(MALE)
					.spayed(true)
					.name("똑똑이")
					.type(DOG)
					.kind("리트리버")
					.build();
				petImgUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f8/Labrador-retriever.jpg/1200px-Labrador-retriever.jpg";
			} else if (i % 10 == 4) {
				petRegEntity = PetRegEntity.builder()
					.pid("4100000000000" + Integer.toString(i))
					.sex(FEMALE)
					.spayed(false)
					.name("도지")
					.type(DOG)
					.kind("진돗개")
					.build();
				petImgUrl = "https://t1.daumcdn.net/cfile/tistory/9949E333598C76C229";
			} else if (i % 10 == 5) {
				petRegEntity = PetRegEntity.builder()
					.pid("4100000000000" + Integer.toString(i))
					.sex(MALE)
					.spayed(false)
					.name("비닐봉지")
					.type(DOG)
					.kind("말티즈")
					.build();
				petImgUrl = "https://post-phinf.pstatic.net/MjAxOTEyMjZfMjg4/MDAxNTc3MzQ4MDk0OTg3.enPaZdNz18Qodom0-FVoGTxKsK7XVuB-0xlDVhy7lDYg.0ysMSYYiTvGa2z2__No32752OyRbAi79c6hDe-xNmlMg.JPEG/EMKK3hcXsAAMAOG.jpg?type=w1200";
			} else if (i % 10 == 6) {
				petRegEntity = PetRegEntity.builder()
					.pid("4100000000000" + Integer.toString(i))
					.sex(MALE)
					.spayed(true)
					.name("치즈")
					.type(CAT)
					.kind("치즈고양이")
					.build();
				petImgUrl = "https://mblogthumb-phinf.pstatic.net/MjAxOTAyMTFfNCAg/MDAxNTQ5ODY3MDMwMDQ2.ZNx_166dOpt-bSvokwMKSr82Gm3FMYgc_jlqYe9tekkg.2j_kd77nubQyen92PZCKk-Ndi_GPvo2MrkGPQPQF6M8g.PNG.tjdkfqhd/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2019-02-11_%EC%98%A4%ED%9B%84_3.36.58.png?type=w800";
			} else if (i % 10 == 7) {
				petRegEntity = PetRegEntity.builder()
					.pid("4100000000000" + Integer.toString(i))
					.sex(FEMALE)
					.spayed(false)
					.name("도넛")
					.type(DOG)
					.kind("사향고양이")
					.build();
				petImgUrl = "https://img.freepik.com/premium-photo/close-up-photo-of-asian-palm-civet_266258-1376.jpg";
			} else if (i % 10 == 8) {
				petRegEntity = PetRegEntity.builder()
					.pid("4100000000000" + Integer.toString(i))
					.sex(MALE)
					.spayed(false)
					.name("따라쟁이")
					.type(ETC)
					.kind("앵무새")
					.build();
				petImgUrl = "https://blog.kakaocdn.net/dn/v4EQA/btrrP3WtDNK/Qk4LdFXibQv1OKjaHGh8ek/img.jpg";
			} else if (i % 10 == 9) {
				petRegEntity = PetRegEntity.builder()
					.pid("4100000000000" + Integer.toString(i))
					.sex(FEMALE)
					.spayed(true)
					.name("민지")
					.type(ETC)
					.kind("흑염소")
					.build();
				petImgUrl = "https://cdn.polinews.co.kr/news/photo/201905/393544_2.png";
			}


			petRegRepository.save(petRegEntity);

			// 동물(Pet) 추가
			PetEntity petEntity = PetEntity.builder()
				.pid("4100000000000" + Integer.toString(i))
				// .ca()
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
					.profile(petImgUrl)
					.etc("위치는 서울시 관악구입니다.\n"
						+ "길에서 발견되어 보호하게 되었습니다.\n"
						+ "많은 관심 부탁드립니다!\n")
					.tel("01012341234")
					.pet(petEntity)
					.user(userRepository.findByUid(1))
					.build();

				petEntity.updatePetLost(true);

				adoptRepository.save(adoptEntity);
			}

			petRepository.save(petEntity);

			// 권한 추가
			// authenticationService.giveMasterAuthentication(6L, 2L, "41000000000000");

		}

		// PetInfoEntity petInfoEntity = PetInfoEntity.builder()
		// 	.pid("410000000000000")
		// 	.build();
		//
		// petInfoRepository.save(petInfoEntity);
	}
}
