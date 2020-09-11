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



## 20.09.09

### 오늘 한 일

- 명세서 학습



#### 스켈레톤 구조 학습

##### Frontend

###### public 폴더

- public 폴더 안에 있는 것들은 정적 자원

- 접근을 위해 앱의 배포 경로를 고려해 절대 경로를 사용해야 함

- 이를 고려하지 않고 접근할 수 있도록 BASE_URL을 사용

  - public/index.html 에서는 <%= BASE_URL %>

  ```
  <link rel="icon" href="<%= BASE_URL %>images/rocket.svg">
  ```

  - javascrip 코드에서는 process.env.BASE_URL 과 같이 사용한 것 확인 가능 

  ```
  return process.env.BASE_URL + "images/" + name;
  ```

###### src 폴더

- src/api/ : API 호출을 위해 axios라이브러리 사용. index.js 파일에 baseurl, http header 설정을 해 둠. axios 인스턴스 생섬 함수, API 호출 기능 함수 등이 있다.

- src/views/, src/components/ : views 폴더는 웹 화면 별 컴포넌트, components 폴더에는 각 화면 별 자식 컴포넌트와 공통 컴포넌트를 작성하도록 되어 있다.
- src/config/ : 필요한 URL, 컨트랙트 주소, 스마트 컨트랙트 ABI 등 구동 환경 및 설정에 대한 변수 및 상수 정보
- src/utils/ : 스마트 컨트랙트 메서드 호출, 가스 단위 변환 등 개발 시 자주 사용되는 기능의 함수들이 있다.



##### Backend

###### com.ecommerce.api

API 정의 

@Controller



###### com.ecommerce.application

실제 비즈니스 로직

@Service



###### com.ecommerce.domain.*

com.ecommerce.domain - 데이터 클래스 (DTO)

com.ecommerce.domain.repository - CRUD 레포지토리 인터페이스 (DAO)

com.ecommerce.domain.wrapper - 스마트 컨트랙트 호출 및 블록 동기화 기능 구현



###### com.ecommerce.infrastructure.repository

MySQL DB에 객체의 CRUD를 실제로 수행하는 SQL 구현부. DB구조, Domain Layer와 밀접한 연관.





#### 지갑 관련 기능 구현

web3 라이브러리를 이용하여 지갑 생성/회원의 지갑 정보를 서버로 등록

> vue에서 npm install web3 명령으로 라이브러리 설치 (이미 되어있는 것 같군)
>
> ```
> createWallet: function() {
> 
>       let account = web3.eth.accounts.create()
>       this.privateKey = account.privateKey;
>       this.walletAddress = account.address;
>     
>       alert("지갑 생성이 완료되었습니다.");
>       
>       this.step += 1;
> }
> ```
>
> web3 api를 사용하여 지갑 생성



### ~~! 지갑이 Geth에는 생성되지 않는 문제~~

### ~~! DB에 지갑이 저장되고 후 조회 안됨 -> 정상 생성 기능이 안되는 문제~~



## 20.09.10

### 오늘 한 일

- 로고 정하기
- Backend 프레임 만들기
- Entitiy 설계



#### JPA 연관관계 매핑

##### 다대일 / 일대다

테이블 - 외래키 하나로 두 테이블이 연관관계를 가질수 있음

> 연관관계를 관리하는 포인트는 **외래키 하나!**

엔티티가 양방향으로 참조되면 A -> B, B->A 둘 중 하나를 정하여 외래키 관리

> 보통은 **외래키를 가진 쪽**을 **연관관계의 주인**으로 선택

주인이 아닌 방향은 읽기만 가능하다.

> 연관관계의 주인은 `mappedBy` 속성을 사용할 수 없으며, 주인이 아닌 쪽에 `mappedBy`로 주인 필드(외래키) 지정 (1:N 또는 N:1의 경우 N인 쪽이 외래키를 가짐. N인 쪽이 주인)
>
> 일대다 보다는 다대일을 사용하는 것 권장

[내일 마저 하자...](https://private-space.tistory.com/95?category=876554)

#### 내일은 좀 더 열심히 살아보자...



## 20.09.11

### 오늘 한 일

- 엔티티 수정
- 엔티티 연관관계 매핑
- 엔티티 빌더 생성
- 엔티티 순환 참조 방지



#### ManyToMany

양 쪽 모두 Set의 형태로 데이터를 가지게 한다.

연관관계의 주인 쪽에 관계를 구성

@JoinTable 어노테이션 이용 

- name = "조인 테이블 이름"
- joincolumns = @JoinColumn(name = "주인쪽 외래키 id")
- inverseJoinColumn(name = "다른쪽 외래키 id")

주인이 아닌쪽에 mappedBy 설정

[ManyToMany 레퍼런스](https://www.baeldung.com/jpa-many-to-many)

[ManyToMany 예제](https://thorben-janssen.com/best-practices-for-many-to-many-associations-with-hibernate-and-jpa/)



#### JPA 순환 참조 방지

@JsonIgnore 

- 실제로 property에 null을 할당하는 방식

@JsonManagedReference / @JsonBackReference 

- 본질적으로 순환참조를 방어하기 위한 Annotation
- 객체의 상위/하위 관계 처리 명시

- 부모 클래스측에(주인쪽에 사용했음 난) @JsonManagedReference 자식측에 @JsonBackReference

