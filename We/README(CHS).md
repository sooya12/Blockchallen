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

