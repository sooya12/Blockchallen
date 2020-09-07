## 20.09.07

### 오늘 할 일

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

