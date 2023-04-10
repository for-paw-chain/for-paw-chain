# 🐶 포포체인 🐶 - 믿을 수 있는 동물 의료 기록 추적 서비스

👇[포포체인 소개 및 시연 영상]👇
<br>
https://youtu.be/9AmmoUADeQM

1. [**서비스 소개**](#1)
2. [**기술 스택**](#2)
3. [**개발 환경**](#3)
4. [**프로젝트 진행**](#4)
5. [**프로젝트 산출물**](#5)
6. [**팀원 소개**](#6)
<br>

# Ⅰ. 서비스 소개

## 1. 서비스 설명

### 🐾 포포체인 개요

- 한줄 소개 : `블록체인`을 활용한 동물 의료 기록 관리 및 권리 보호 플랫폼
- 서비스 명 : 포포체인
  - For Paw Chain, 발바닥을 가진 동물들을 위한 블록체인 기반 서비스라는 의미

<br>

## 2. 기획 배경
<br>
1) 현행법상 법적으로 진료기록을 발급할 의무가 없음
<br>
 2) 유기동물이 과거에 어떤 치료를 받았는지 알 수 없음
<br>
 3) 주인이 반려동물의 진료기록을 알 수 없음
<br>
 4) 동물등록제(인식칩)의 의무화
<br>
 5) 중복 치료로 인한 유기동물 동물권 침해
<br>
 6) 의료사고 발생 시 원인 파악 불가능
<br>
 7) 병원 이동 시 불필요한 검사 필요


### ❤ 기획 의도

- `동물권 보장` <br>
    적합한 치료를 받기 어려운 유기동물을 위해 건강 상태를 빠르고 정확하게 파악하여 동물권을 보장
- `동물보호자의 편의성 증진` <br>
    병원에 가지 않아도 모든 보호자가 반려동물의 진료 기록을 확인할 수 있는 편의성 제공 <br>
    병원을 옮길 시 이전의 진료기록 확인 가능
- `투명한 의료기록`<br>
    동물의 의료 기록 투명성을 보장하여 동물병원에 의한 피해를 줄이고, 입양/분양 시 거짓 건강상태로 인한 파양률을 줄임

### 🧡기대효과

`동물보호자`

- 반료 동물의 의료 기록 파악 가능
- 의료 사고 인지 가능
- 반려 동물을 잃어버린 경우, 자신의 정보 공개 가능

`의사`

- 다른 병원에서 치료한 동물이나 유기 동물의 의료 기록 파악 가능
- 성실히 의료 기록을 등록할 경우, 고객으로부터 신뢰성 증가

`동물`

- 유기 동물의 재검사 방지 및 적합한 치료 가능
- 인식칩 사용률 증가
- 동물의 유기 비율 감소

`입양 시`

- 예방 접종 등 사기 방지
- 유기 동물의 입양 장려
- 펫샵 이용률 감소

<br>

## 3. 서비스 화면

### 🤍 로그인

> - 별도 회원가입 없이 카카오 계정을 통해 로그인할 수 있다.
> - 이후 메인페이지로 이동한다.


### 🖤 로그아웃

<img src="" title="로그아웃" width="70%" height="70%"/>

### 🖤 회원 탈퇴

### 💜 메인페이지

> - 메인페이지에서는 동물등록번호를 입력함으로써 동물 정보와 의료내역을 조회하는 것이 가능하다.
> - 화면을 위로 스크롤 시, `나의 반려동물` 목록을 볼 수 있다.
> - `나의 반려동물` 목록에는 내가 직접 키우는 동물 또는, 의료내역을 열람할 수 있는 권한을 부여받은 동물이 포함된다.

<img src="" title="메인페이지" width="70%" height="70%"/>

### 🔳 QR 이미지 생성

> - 10자리가 넘는 긴 동물등록번호를 매번 기억하기 어려운 사용자들을 위해, QR 이미지를 제공한다.
> - QR 이미지는 휴대폰 갤러리에 저장할 수 있다.

<img src="" title="QR 이미지 생성" width="70%" height="70%"/>

### 동물등록번호 검색 결과 페이지

> - [정부 api](https://www.data.go.kr/data/15098913/openapi.do?recommendDataYn=Y)를 이용해 해당 동물의 기본 정보를 같이 조회한다.
> - 의료내역 열람 권한이 없는 유저라면, 의료내역 대신 유기동물 분양 광고를 보여준다.
> - 의료내역 열람 권한이 있는 유저라면, 의료내역 목록을 확인할 수 있다.

### 동물 정보 등록

> - 해당 동물의 주인이라면, 동물의 추가적인 정보를 직접 입력할 수 있다.

### 의사 면허 등록

> - 본인이 의사임을 인증하면, 수의사로서 동물의 의료내역을 작성할 수 있는 권한이 부여된다.
> - 본인이 의사임을 인증하는 방법은 보건복지부의 [의료인면허정보조회](https://dataapi.co.kr/upload/dLab/20220105-173345-00330.html) api를 이용한다.

### 의료 기록 조회 및 등록

> - 의사이거나, 해당 동물의 주인이거나, 그 밖에 열람 권한을 따로 부여받은 사람이라면, 해당 동물의 의료 기록들을 조회할 수 있다.
> - 동물마다 1개의 컨트랙트가 블록체인 네트워크에 배포된다.
> - 의료 기록은 해당 동물의 컨트랙트에 저장된다.
> - 컨트랙트의 배포 및 조회, 작성은 모두 Web3j를 통해 이루어진다.

### 의료기록 열람 권한 부여

> - 기본적으로는 의사 또는 해당 동물의 주인만이 동물 의료 기록을 열람할 수 있다.
> - 하지만 해당 동물의 주인이 다른 사람에게도 의료 기록을 보여주고 싶은 경우, 열람 권한을 부여할 수 있다.
> - 열람 권한을 다시 뺏을 수도 있다.

### 분양 보내기

> - 키우던 애완 동물을 다른 사람에게 분양 보낼 경우, 분양하기 버튼을 눌러 주인을 변경할 수 있다.

### 유기동물 입양 공고

> -

<img src="" title="미리보기 글" width="70%" height="70%"/>

  <br>

<div id="2"></div>

# Ⅱ. 기술 스택

## 1. 블록체인 네트워크

부연 설명

## 2. IPFS

부연 설명

## 3. Android

부연 설명

## 4. Redis

유저 online, offline 상태정보와 게임점수는 실시간으로 업데이트 되는 정보이고, DB에 정보를 저장하여 사용하면 유저가 많아짐에 따라 과부하가 걸릴 것입니다. 이러한 데이터의 특성으로 캐싱을 적용하기에 적절하다고 생각을 했습니다. 따라서 Redis에 유저 online, offline 상태 정보와 실시간 게임 점수를 저장하여 DB를 거치지 않고 정보를 가져와 트래픽이 많아질 때 백엔드 부하를 줄이고, 정보 조회 속도를 높였습니다.

## 5. Google Cloud Platform

동물 정보를 등록할 때 입력받는 동물 사진 데이터가 많아져, 기존에 사용하는 mariadb 이외에 별도로 Google Cloud Platform에서 데이터를 효율적으로 관리했습니다.

<br>

<div id="3"></div>

# Ⅲ. 개발 환경 🖥️

## 🖥 Backend
![IntelliJ badge](https://img.shields.io/badge/-IntelliJ-green)
![Java badge](https://img.shields.io/badge/-Java11-blue)
![spring boot badge](https://img.shields.io/badge/-Spring%20Boot-yellow)
![spring-data-jpa badge](https://img.shields.io/badge/-spring--data--jpa-red)
![spring-security badge](https://img.shields.io/badge/-spring--security-brightgreen)
![JWT badge](https://img.shields.io/badge/-JWT-blue)
![Web3j badge](https://img.shields.io/badge/-Web3j-yellow)
![mariadb badge](https://img.shields.io/badge/-MariaDB-lightgrey)
![redis badge](https://img.shields.io/badge/-redis-orange)
![Google Cloud Platform badge](https://img.shields.io/badge/-GCP-brightgreen)

## 🖥 Frontend
![Kotlin](https://img.shields.io/badge/-Kotlin-blueviolet)
![Android Studio](https://img.shields.io/badge/-Android_Studio-success)

## 🖱 DevOps
![AWS EC2 badge](https://img.shields.io/badge/-AWS_EC2-brightgreen)
![docker badge](https://img.shields.io/badge/-docker-blue)
![jenkins badge](https://img.shields.io/badge/-jenkins-red)

## 🎨 UI/UX
![Figma](https://img.shields.io/badge/-Figma-ff69b4)

## 👨‍👩‍👧 협업 툴

![Git / Gitlab](https://img.shields.io/badge/-Git%20%2F%20Gitlab-9cf)
![Jira](https://img.shields.io/badge/-Jira-blue)
![Notion](https://img.shields.io/badge/-Notion-c93)

## 블록체인
![Solidity](https://img.shields.io/badge/-Solidity-orange)
![Geth](https://img.shields.io/badge/-Geth-yellowgreen)

<br>

<div id="4"></div>

# Ⅳ. 프로젝트 진행

## 🕑 프로젝트 진행 기간
2023.02.20(월) ~ 2023.04.07(금) (47일간 진행) 
<br>
SSAFY 8기 2학기 특화 프로젝트
<br>

## 1. git

![git_convension](/document/git_convention.png)
![gitflow](document/git_flow.png)
## 2. Jira

매주 월요일 오전 회의에서 차주에 진행되어야 할 것들을 정리하고 백로그에 등록했습니다. 금주에 완료하지 못한 이슈나, 앞으로 진행할 이슈들을 추가했습니다.

`에픽(epic)`은 구현하고자 하는 기능을 기준으로 구성하였습니다. (예: 권한 관리, 블록체인 등)

실질적인 작업 결과물이 나오는 업무는 `작업(task)`으로, 그렇지 않은 업무는 `스토리(story)`로 할당하였습니다.

마지막으로 담당자와 스토리 포인트 설정, 활성 스프린트에서 현재 업무의 진행에 따라 할 일, 진행중, 완료 실시간으로 반영하는 것을 가장 중요하게 생각했습니다.

![image](/document/burn-down-chart.png)

## 3. Notion

팀원들이 모두 공유해야할 자료 및 링크를 노션에 정리했습니다. API 설계, ERD, 컨벤션와 같이 여러번 다시 봐야하고 중요한 정보들을 공유하였습니다.

달력을 만들어 활용함으로써 중요한 일정은 잊지 않고 챙길수 있게끔 하였습니다.

![notion](/document/notion.png)

<br>

<div id="5"></div>

# V. 프로젝트 산출물

## 🏛 서비스 아키텍처

![서비스아키텍처](/document/시스템%20아키텍처.png)

## 🎨 화면 설계서

<a href="">
    <img src="" title="화면설계서로 이동"/>
</a>
<a href="">
    <img src="" title="화면설계서로 이동"/>
</a>

## 🛢︎ ERD

![ERD](/document/erd.png)

## 📜 API 설계서

<a href="">
    <img src="" title="API 설계서로 이동"/>
</a>

<br>

<div id="6"></div>

# VI. 팀원 소개

![member](/document/member.png)
