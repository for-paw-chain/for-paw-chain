-- pet 테이블
INSERT INTO `forpawchain`.`pet` (`pid`, `lost`) VALUES ('410374628110410', b'1');
INSERT INTO `forpawchain`.`pet` (`pid`, `lost`) VALUES ('410123212310411', b'0');
INSERT INTO `forpawchain`.`pet` (`pid`, `lost`) VALUES ('410423223104113', b'0');
INSERT INTO `forpawchain`.`pet` (`pid`, `lost`) VALUES ('410005654664118', b'0');
INSERT INTO `forpawchain`.`pet` (`pid`, `lost`) VALUES ('410546422114121', b'1');
INSERT INTO `forpawchain`.`pet` (`pid`, `lost`) VALUES ('410373242340413', b'1');
INSERT INTO `forpawchain`.`pet` (`pid`, `lost`) VALUES ('410231433444136', b'1');
INSERT INTO `forpawchain`.`pet` (`pid`, `lost`) VALUES ('410000856750414', b'0');
INSERT INTO `forpawchain`.`pet` (`pid`, `lost`) VALUES ('410006745204145', b'1');
INSERT INTO `forpawchain`.`pet` (`pid`, `lost`) VALUES ('410324555550416', b'0');
INSERT INTO `forpawchain`.`pet` (`pid`, `lost`) VALUES ('410215453444221', b'0');

-- petinfo 테이블
INSERT INTO `forpawchain`.`pet_info` (`pid`, `birth`, `etc`, `profile`, `region`, `tel`) VALUES ('410374628110410', '2020-05-03', '사람을 무서워해요', 'https://storage.googleapis.com/bucket-img-for-paw-chain/1.png', '서울시 강남구', '01012345678');
INSERT INTO `forpawchain`.`pet_info` (`pid`, `birth`, `etc`, `profile`, `region`, `tel`) VALUES ('410123212310411', '2018-11-21', '귀염둥이입니당', 'https://storage.googleapis.com/bucket-img-for-paw-chain/4.png', '서울시 관악구', '01023411243');
INSERT INTO `forpawchain`.`pet_info` (`pid`, `birth`, `etc`, `profile`, `region`, `tel`) VALUES ('410423223104113', '2022-04-24', '귀염둥이입니당', 'https://storage.googleapis.com/bucket-img-for-paw-chain/3.png', '서울시 관악구', '01023411243');
INSERT INTO `forpawchain`.`pet_info` (`pid`, `birth`, `etc`, `profile`, `region`, `tel`) VALUES ('410005654664118', '2023-06-14', '사람 말을 잘 따라해요', 'https://storage.googleapis.com/bucket-img-for-paw-chain/10.png', '서울시 강남구', '01012345678');
INSERT INTO `forpawchain`.`pet_info` (`pid`, `birth`, `etc`, `profile`, `region`, `tel`) VALUES ('410373242340413', '2016-12-11', '손도 잘 타고 착해요', 'https://storage.googleapis.com/bucket-img-for-paw-chain/2.png', '서울시 강남구', '01012345678');
INSERT INTO `forpawchain`.`pet_info` (`pid`, `birth`, `etc`, `profile`, `region`, `tel`) VALUES ('410000856750414', '2017-01-29', '귀염둥이입니당', 'https://storage.googleapis.com/bucket-img-for-paw-chain/7.png', '서울시 관악구', '01023411243');
INSERT INTO `forpawchain`.`pet_info` (`pid`, `birth`, `etc`, `profile`, `region`, `tel`) VALUES ('410215453444221', '2019-04-04', '장난치는 걸 좋아해요', 'https://storage.googleapis.com/bucket-img-for-paw-chain/5.png', '서울시 강남구', '01012345678');

-- petreg 테이블
INSERT INTO `forpawchain`.`pet_reg` (`pid`, `kind`, `name`, `sex`, `spayed`, `type`) VALUES ('410374628110410', '푸들', '김엘리자베스', 'FEMALE', b'0', 'DOG');
INSERT INTO `forpawchain`.`pet_reg` (`pid`, `kind`, `name`, `sex`, `spayed`, `type`) VALUES ('410123212310411', '메인쿤', '똥똥이', 'FEMALE', b'1', 'CAT');
INSERT INTO `forpawchain`.`pet_reg` (`pid`, `kind`, `name`, `sex`, `spayed`, `type`) VALUES ('410423223104113', '리트리버', '똑똑이', 'MALE', b'1', 'DOG');
INSERT INTO `forpawchain`.`pet_reg` (`pid`, `kind`, `name`, `sex`, `spayed`, `type`) VALUES ('410005654664118', '앵무새', '따라쟁이', 'MALE', b'0', 'ETC');
INSERT INTO `forpawchain`.`pet_reg` (`pid`, `kind`, `name`, `sex`, `spayed`, `type`) VALUES ('410546422114121', '코리안숏헤어', '턱시도', 'FEMALE', b'0', 'CAT');
INSERT INTO `forpawchain`.`pet_reg` (`pid`, `kind`, `name`, `sex`, `spayed`, `type`) VALUES ('410373242340413', '도마뱀', '얌얌이', 'FEMALE', b'1', 'ETC');
INSERT INTO `forpawchain`.`pet_reg` (`pid`, `kind`, `name`, `sex`, `spayed`, `type`) VALUES ('410231433444136', '코리안숏헤어', '고등어', 'MALE', b'0', 'CAT');
INSERT INTO `forpawchain`.`pet_reg` (`pid`, `kind`, `name`, `sex`, `spayed`, `type`) VALUES ('410000856750414', '진돗개', '도지', 'FEMALE', b'0', 'DOG');
INSERT INTO `forpawchain`.`pet_reg` (`pid`, `kind`, `name`, `sex`, `spayed`, `type`) VALUES ('410006745204145', '말티즈', '비닐봉지', 'MALE', b'0', 'DOG');
INSERT INTO `forpawchain`.`pet_reg` (`pid`, `kind`, `name`, `sex`, `spayed`, `type`) VALUES ('410324555550416', '치즈고양이', '치즈', 'MALE', b'1', 'CAT');
INSERT INTO `forpawchain`.`pet_reg` (`pid`, `kind`, `name`, `sex`, `spayed`, `type`) VALUES ('410215453444221', '아비시니안', '아바', 'MALE', b'0', 'CAT');

-- adopt 테이블
INSERT INTO `forpawchain`.`adopt` (`pid`, `etc`, `profile`, `regTime`, `tel`, `uid`) VALUES ('410374628110410', '똑똑해서 말도 잘 알아듣고 훈련도 빨라요', 'https://storage.googleapis.com/download/storage/v1/b/bucket-img-for-paw-chain/o/7658bb3c-0f49-4de4-8010-9f1d0b857d9e?generation=1680597009620725&alt=media', '2023-04-04 08:30:09.763669', '01012345678', 1);
INSERT INTO `forpawchain`.`adopt` (`pid`, `etc`, `profile`, `regTime`, `tel`, `uid`) VALUES ('410546422114121', '굉장히 크고 커여운 냥입니다.
 저만 껴안을 수 있으니 조심하세요', 'https://storage.googleapis.com/bucket-img-for-paw-chain/4.png', '2023-03-30 15:01:09.430976', '01012341234', 1);
INSERT INTO `forpawchain`.`adopt` (`pid`, `etc`, `profile`, `regTime`, `tel`, `uid`) VALUES ('410373242340413', '우리집 부처님이예요. 애기가 볼꼬집고 등에 타면서 노는데도 참 착해요!!!', 'https://storage.googleapis.com/bucket-img-for-paw-chain/2.png', '2023-03-30 15:01:02.863440', '01012341234', 1);
INSERT INTO `forpawchain`.`adopt` (`pid`, `etc`, `profile`, `regTime`, `tel`, `uid`) VALUES ('410231433444136', '치츠냥이예요,,, 치츠냥은 치츠예요 왜냐하면 치츠색이거든요.
 치츠냥은 성격이 그냥 고양이 완전 그 자체예요', 'https://storage.googleapis.com/bucket-img-for-paw-chain/8.png', '2023-03-30 15:01:14.679735', '01012341234', 2);
INSERT INTO `forpawchain`.`adopt` (`pid`, `etc`, `profile`, `regTime`, `tel`, `uid`) VALUES ('410006745204145', '위치는 서울시 관악구입니다.
 길에서 발견되어 보호하게 되었습니다.
 많은 관심 부탁드립니다!', 'https://storage.googleapis.com/bucket-img-for-paw-chain/9.png', '2023-03-30 15:01:17.771594', '01012341234', 2);

-- authentication 테이블
INSERT INTO `forpawchain`.`authentication` (`pid`, `uid`, `regTime`, `type`) VALUES ('410005654664118', 2, '2021-04-04', 'FRIEND');
INSERT INTO `forpawchain`.`authentication` (`pid`, `uid`, `regTime`, `type`) VALUES ('410005654664118', 1, '2022-05-04', 'MASTER');
INSERT INTO `forpawchain`.`authentication` (`pid`, `uid`, `regTime`, `type`) VALUES ('410000856750414', 2, '2020-09-04', 'FRIEND');
INSERT INTO `forpawchain`.`authentication` (`pid`, `uid`, `regTime`, `type`) VALUES ('410000856750414', 1, '2022-01-04', 'MASTER');
INSERT INTO `forpawchain`.`authentication` (`pid`, `uid`, `regTime`, `type`) VALUES ('410324555550416', 1, '2021-12-04', 'MASTER');
INSERT INTO `forpawchain`.`authentication` (`pid`, `uid`, `regTime`, `type`) VALUES ('410215453444221', 2, '2023-11-04', 'MASTER');
INSERT INTO `forpawchain`.`authentication` (`pid`, `uid`, `regTime`, `type`) VALUES ('410215453444221', 1, '2023-10-04', 'FRIEND');

-- doctorlicense 테이블
INSERT INTO `forpawchain`.`doctor_license` (`id`, `dsnm`, `jumin`, `phonenum`, `telecomgubun`) VALUES ('1', '김민소', '9603201234567', '01038938970', '1');
INSERT INTO `forpawchain`.`doctor_license` (`id`, `dsnm`, `jumin`, `phonenum`, `telecomgubun`) VALUES ('2', '윤혜진', '9012121234567', '01012341234', '1');