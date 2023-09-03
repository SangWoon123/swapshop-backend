# 프로젝트 소개 : SwapShop


![logo](https://github.com/play3step/SwapShop-front/assets/97451257/93b9794a-53dd-4ccc-86ce-07ebc4120993)



## ✈️ 소개
대학교 중고서적 거래 플랫폼으로 한국공학대학교 2023년 2학기 시간표기준으로 필요한 대학교재를 거래할수 있는 서비스를 제공합니다.


## 📖 기술 스택
<div>
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 
<img src="https://img.shields.io/badge/docker-4479A1?style=for-the-badge&logo=docker&logoColor=white">
<img src="https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white">
<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
</div>      

<h6>※ 도커는 개발시 사용x, 공부용으로 사용</h6>

<br>

## 실행방법
- 기본
  ```
  1. chmod +x gradlew

  2. ./gradlew build

  3. java -jar build/libs/swapshop-backend-0.0.1-SNAPSHOT.jar
  ```
- Docker
  ```
  1. cd swapshop-backend

  2. docker build -t swapshop-back .
  
  3. docker run -p 8080:8080 swapshop-back
  ```
<br>


## 📆 프로젝트 기간
23/07/04 ~ 23/09/03
<br>

## 💻 front: 
https://github.com/play3step/SwapShop-front 
<br>


## 🎨 디자인
[SWAPSHOP_design.pdf](..%2F..%2F..%2FDownloads%2FSWAPSHOP_design.pdf)
<br>

## 📝 어플리케이션 기능
- 기능 요약
  1. 팔고싶은 책을 사진과 책을 사용하는 전공 과목을 함께 게시물로 업로드한다.
  2. 구매를 원하는 사용자는 댓글이나 개별쪽지 기능을 이용해 판매자와 거래를 진행한다.
  
- 개발한 기능 목록
  - 로그인 (카카오, 구글)
  - 게시글 작성
  - 댓글
  - 찜하기
  - 신고
  - 쪽지 보내기
  - 1:1 채팅
  - 카테고리 
<br>


## 📝 기능 개발 현황
- 23/07/12: 스프링 oauth2.0 로그인 구현 (구글,카카오) --> ※프론트 연결 O
- 23/07/19: 게시물 작성 구현 --> ※프론트 연결 O
- 23/07/26:
  - 게시글 댓글 --> ※프론트 연결 O
  - 게시물 aws s3 사진업로드 구현 --> ※프론트 연결 O
  - 카테고리 영역 DB 저장 --> ※프론트 연결 O
- 23/07/27: 게시글 찜하기 완료 --> ※프론트 연결 O
- 23/08/03: 게시글 대댓글 구현 완료 --> ※프론트 연결 O
- 23/08/17: 쪽지 기능 구현 완료 -> ※프론트 연결 O
<br>

## 📃 API
```
// 실행 방법 : 백엔드 서버 연결 후 접속
http://localhost:8080/swagger-ui/index.html#/
```

![image](https://github.com/why-only-english/Programmers/assets/114092152/29292426-5a7a-4159-805e-59c26f17abe7)
<br>

## ➡️️ Flowchart
![image](https://github.com/why-only-english/Programmers/assets/114092152/a620064c-99a5-431d-ba94-bd274fcef96d)
<br>

## 📑 ERD
초기 설계
![image](https://github.com/why-only-english/Programmers/assets/114092152/209714f8-886b-4052-89c2-2e49b79f9afe)

최종 설계
![image](https://github.com/SangWoon123/swapshop-backend/assets/100204926/909aff2e-37aa-4b2f-9066-e61924ca3387)


<br>

## 🛠️ Architecture Structure
![image](https://github.com/SangWoon123/swapshop-backend/assets/114092152/34d934a0-e088-4c42-ad7d-f501d5ba2ab5)
<br>

<!-- 
## 🙋 데모 (완성X)

<img width="30%" src="https://github.com/SangWoon123/swapshop-backend/assets/100204926/f4c7d764-c7b8-48b2-9838-611543b95f36"/> -->

## 데모 영상

https://github.com/play3step/SwapShop-front/assets/97451257/7a433406-432e-4bf2-a2e9-601ac01dfa5a


