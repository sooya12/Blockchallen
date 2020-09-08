## 20.09.07

### 오늘 한 일

- 주제 선정
- 팀명/서비스명 선정
- 개발 환경 구성



#### 개발 환경 구성

##### Backend

###### OpenJDK 설치

- https://jdk.java.net/archive 에서 jdk 12버전(zip) 다운

- 원하는 경로에 압축해제 (C:\\Program Files\\jdk-12.0.2)에 저장함
- 시스템 환경 변수 JAVA_HOME을 C:\Program Files\jdk-12.0.2로 설정
- Path에 %JAVA_HOME%\bin 추가 (또는 Path에 C:\Program Files\jdk-12.0.2\\bin 추가)

###### Maven 설치

- https://maven.apache.org에서 zip파일 다운

- 원하는 경로에 압축해제 (C:\SSAFY\apache-maven-3.6.3)에 저장함
- 시스템 환경 변수 MAVEN_HOME을 C:\SSAFY\apache-maven-3.6.3로 설정
- Path에 %MAVEN_HOME%\bin 추가 (또는 Path에 C:\SSAFY\apache-maven-3.6.3\\bin 추가)

###### 설치 확인

cmd 창에서 버전 확인

```
> java -version
> mvn -v
```



##### 데이터베이스 구성

###### 도커 설치

https://docs.docker.com/get-docker/



###### Docker Hub로부터 도커 이미지를 가져와 실행

- 필요한 DB Schema가 적용된 Mysql 컨테이너 이미지 사용

```
> docker pull emblockit/ssafy-mysql 
도커 이미지 다운로드
> docker images
다운로드된 도커이미지 확인
> docker run -d -p 3306:3306 --name mysql emblockit/ssafy-mysql
도커 이미지를 통해 컨테이너 생성
-d : detached모드. 데몬모드. 컨테이너 백그라운드 실행 
-p (연결할 호스트의 포트 번호):(컨테이너의 포트 번호)
> docker ps -a
mysql 컨테이너가 동작 중인지 확인 STATUS가 'UP 시간'이 나온다면 컨테이너가 동작하고 있는 것
> docker inspect 754e44cd2bcf
해당 컨테이너의 세부 정보를 알 수 있음
```



###### 데이터베이스 접속

URL : jdbc:mysql://{DATABASE_IP}:3306/bcssafy

root 계정 - ID : root / PW : admin

bc 계정 - ID : bcssafy / PW : bcssafy

```
> docker exec -it 754e44cd2bcf bash
mysql 컨테이너 bash쉘에 접속

root@754e44cd2bcf:/# mysql -u root -padmin
docker run 설정한 root 계정에 접속(bc 계정은 mysql -u bcssafy -pbcssafy로 접속 가능)

mysql> show databases;
데이터베이스 조회

mysql> use bcssafy;
데이터베이스 사용 설정

mysql> show tables;
데이터베이스 테이블 조회
```



## 20.09.08

### 오늘 한 일

- 와이어 프레임 설계
- DB 설계
- API 설계
- JPA 기본적인 내용 학습



#### JPA (Java Persistent API)

> Java에서의 ORM 기술 표준으로, 인터페이스의 모음. 구현체는 Hibernate를 사용할 것이다.

###### 장점

- 객체지향적으로 데이터 관리 가능 -> 비즈니스 로직에 집중할 수 있음
- 테이블 생성, 변경, 관리가 쉬움
- 빠른 개발이 가능

###### 단점

- 어려움
- 성능상 문제 발생 가능성



##### JPA annotation

@Entity

- DB의 테이블과 일대일로 매칭되는 객체 단위.
- 객체의 인스턴스 하나가 테이블에서 하나의 레코드 값 의미

@Id

- 테이블 상의 Primary Key

@GeneratedValue

- PK 컬럼의 데이터 형식은 유일하고, 데드락 방지를 위해 Java에서는 Long을 주로 사용

- GenerateType

  - IDENTITY : MySQL의 auto increment 방식 채택

    > 자동으로 새로운 레코드 생성이 될때마다 마지막 PK 값 +1 해주는 방식

  - SEQUENCE : ORACLE의 sequence 방식 채택

    > sequence ORACLE 객체를 생성해 두고 해당 sequence를 호출 할 때마다 기존 값 +1 반환 해주는 방식



###### 연관관계 매핑

- @OneToOne - 일대일( 1 : 1 )
- @OneToMany - 일대다( 1 : N )
- @ManyToOne - 다대일( N : 1 )
- @ManyToMany - 다대다( N : N )