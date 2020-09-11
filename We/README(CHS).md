### :scroll: 20.09.07

##### Sub PJT 2 명세서 확인

- Java - OpenJDK 12 설치

- Docker 설치

- Docker의 Container에 DB 추가 후 테이블 확인

  ```docker
  $ docker pull emblockit/ssafy-mysql
  $ docker images
  $ docker run -d -p 3306:3306 --name mysql emblockit/ssafy-mysql
  $ docker ps -a
  
  $ docker exec -it mysql /bin/bash // 도커 컨테이너 내부 접속
  $ mysql -u [계정명] -p // 패스워드 입력하면 mysql 접속
  
  > show databases;
  > use [스키마명];
  > show tables;
  ```

##### Git, Jira 생성

##### 주제 및 팀명 선정 - "블록챌린"

- 팀명 - **블록챌린**
- 주제 - 블록체인을 기반으로 원하는 목표를 설정하고 목표 달성 시 보상받는 서비스

##### 사용 툴(프로그램) 결정

- Git : 코드 관리
- Jira, Notion : 이슈 및 일정 관리
- Swagger : API 관리 및 테스트
- Jenkins : 빌드 관리
- Jpa : DB 연동

##### Git, Jira 관리자 결정

##### Commit 규칙 결정



### :thinking: 내일 할 일

##### 기능 설계

##### 와이어프레임 설계

##### DB 설계

##### API 설계



### :bulb: 공부해야 할 것

##### Jpa

##### 스마트 컨트랙트

#####  Multi Signiture



------



### :scroll: 20.09.08

##### 발사믹으로 와이어프레임 생성

##### DB 설계 및 Jpa 기본 정보 습득(박형민 팀원의 도움)

##### API 설계

##### Jira 에픽 및 이슈 추가

##### README에 프로젝트 정보 및 커밋 규칙 등록



### :thinking: 내일 할 일

##### 백엔드, 프론트엔드 기본 틀 구성

##### ERC-20 토큰 기준 구성

##### 소셜 로그인 구현

----



### :scroll: 20.09.09

##### 명세서와 스켈레톤 코드를 가지고 명세서 학습



##### 3306 포트 충돌 시, 포트 목록에서 포트 사용하는 프로세스 죽이기

```kill port
$ netstat -a -o
$ taskkill /f /pid [프로세스 번호]
```



##### AWS에 pem 키로 접속 및 파일 올리기

```aws
$ find ~ -name "*.pem" // pem 확장자인 파일 검색
$ ssh -i ~[pem 경로]/J3A102T.pem [유저명]@[ip] // aws 서버에 접속
$ scp -i ~[pem 경로]/J3A102T.pem ~[파일경로] [유저명]@[ip]:[파일을 저장할 경로]
```



##### Vue.js Web3 사용 (https://web3js.readthedocs.io/en/v1.2.11/index.html)

```web3
import Web3 from "web3";

var web3 = new Web3(Web3.givenProvider || 'http://localhost:8545');

let account = web3.eth.accounts.create(); // eth 계정 생성해서 계정 정보 받기
```



##### Java BigDecimal 연산

```BigDecimal
BigDecimal bd = new BigDecimal(0);
bd.add(new BigDecimal(3)); // 더하기
bd.subtract(new BigDecimal(1)); // 빼기
bd.multiply(new BigDecimal(2)); // 곱하기
bd.divide(new BigDecimal(2)); // 나누기
```

 

### :thinking: 내일 할 일

##### 파트 분배

- 프론트엔드 3
- 백엔드 2

##### Vue.js 템플릿 찾아보기

------



### :scroll: 20.09.10

##### 프론트, 백 파트 분배

- 프론트 : 형민, 채영, 현수
- 백 : 진섭, 유진

##### Logo 선정

##### 시작 페이지 프론트 구성

- codepen에서 찾은 블록 로딩 화면 삽입

  - animation을 json 형태로 변환한 거라서 lottie를 이용해서 삽입

  ```lottie
  $ npm install lottie-web
  
  <script>
  	import lottie from 'lottie-web'
  	
  	...
  	
  	mounted() {
      this.anim = lottie.loadAnimation({
        wrapper: document.querySelector('#animationWindow'),
        animType: 'svg',
        loop: true,
        prerender: true,
        autoplay: true,
        path: 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/35984/LEGO_loader.json'
      })
      this.$emit('animCreated', this.anim)
    }
  </script>
  ```

- 서비스명 삽입

- 카카오 소셜 로그인을 위해서 로그인 버튼 이미지 삽입



### :thinking: 내일 할 일

##### 모바일 웹 사이즈에 맞게 시작 페이지 조정

##### 카카오 소셜 로그인 구현

-----



### :scroll: 20.09.11

##### 시작 페이지 모바일 웹 사이즈에 맞게 조정

- vh, vw 사용

##### 카카오 소셜 로그인 프론트단 구현

- 카카오 소셜 로그인에 애플리케이션 생성
- 사용자 이메일만 제공 받기 설정
- 시작 페이지에서 로그인 이미지 클릭 시, token 요청 http 통신

##### 소셜 로그인이 처음일 경우, 닉네임 받아서 회원 가입 처리

- 서비스 이용 약관 공지
- 닉네임 중복확인 설정
- 닉네임 입력에 따른 가입하기 버튼 (비)활성화



### :thinking: 다음 주에 할 일

##### 마이페이지 구현

##### Jira 이슈 추가

##### 멘토링 질문 준비

-----



