# 2020-09-07

- ## Done

  - ### 개발 환경 구성

    - #### Backend

      1. ##### OpenJDK 설치

         ```
         https://jdk.java.net/archive/ 에 접속하여 jdk 12버전(zip) 다운로드
         
         압축 해제 후 시스템 환경 변수 설정
         ```

      2. ##### Maven 설치

         ```
         https://maven.apache.org/ 에 접속하여 zip 파일 다운로드
         
         압축 해제 후 시스템 환경 변수 설정
         ```

      3. ##### 설치 확인

         ![image](https://user-images.githubusercontent.com/22046757/92393660-2d25ee80-f15b-11ea-8298-83310d0f3ff8.png)

    - #### 데이터베이스 구성

      1. ##### Docker 설치

         ```
         https://docs.docker.com/get-docker/ 에 접속하여 설치
         ```

      2. ##### 도커 실행

         ![image](https://user-images.githubusercontent.com/22046757/92394128-f0a6c280-f15b-11ea-9f4a-f9579574f5be.png)

         **실행 결과 다음과 같은 오류 발생 => 이미 3306 포트를 다른 곳에서 사용중!**

         <br>

         ![image](https://user-images.githubusercontent.com/22046757/92394260-2d72b980-f15c-11ea-93e0-49afc68e143b.png)

         **사용중인 3306 포트를 종료시킨다.**
        
         <br>
            
         ![image](https://user-images.githubusercontent.com/22046757/92394347-585d0d80-f15c-11ea-889d-62263c0fc887.png)

         **오류없이 명령어가 성공적으로 적용된다.**
        <br>

      3. ##### 데이터베이스 접속

         ![image](https://user-images.githubusercontent.com/22046757/92394557-b4c02d00-f15c-11ea-9f05-e75c8db6a8d1.png)

         **성공적으로 DB에 접속이 된다.**
         <br> <br>

         ![image](https://user-images.githubusercontent.com/22046757/92394620-d3262880-f15c-11ea-8693-41f3303cdc80.png)

         **DB의 구조를 확인 할 수 있다.** 
         <br>
         ​	

  - ### 주제 선정 및 계획 설립

    ####  주제

    >  블록체인을 기반으로 원하는 목표를 설정하고 목표 달성 시 보상받는 서비스

    ####  사용 Tool

    > Git : 코드 관리
    >
    > Jira, Notion : 이슈 및 일정 관리 
    >
    > Swagger : API 관리 및 테스트
    >
    > Jenkins : 빌드 관리

    ####  Git/Jira 관리자

    > Git 관리 : 박형민
    >
    > Jira 관리 : 최현수

    ####  Commit 규칙

    > [BE/FE/FS/BC] initial/update | 내용 | Jira 이슈 번호
    >
    > > BE : 백
    > >
    > > FE : 프론트
    > >
    > > FS : 풀스택
    > >
    > > BC : 블록체인
    >
    > README(이름)

    ####  Readme

    > We 폴더 내에 각자의 Readme에 하루 정리
    >
    > 감자 전용 폴더 생성해줄 것

# 2020-09-08

- ## Done    

    - #### API 설계
    
    - #### DB 설계 
    
    - #### 와이어프레임 설계
    
# 2020-09-09

- ## Done
    
     - #### 명세서 지갑 생성 구현중

# 2020-09-10

- ## Doing
    
    - #### 지갑 조회, 생성 REST API 구현중

- ## Issue

    - #### push 과정에서 src/main/resources(application.properties)가 손실 됨!
    
    - #### 지갑 조회 시 회원 번호가 필요!
    
- ## To Do

  - ### JPA 공부
  
  - ### 지갑 조회, 생성 REST API 구현 완성하기

