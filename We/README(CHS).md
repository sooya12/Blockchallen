### :scroll: 20.09.07

> #### Sub PJT 2 명세서 확인
>
> - Java - OpenJDK 12 설치
>
> - Docker 설치
>
> - Docker의 Container에 DB 추가 후 테이블 확인
>
>   ```bash
>   $ docker pull emblockit/ssafy-mysql
>   $ docker images
>   $ docker run -d -p 3306:3306 --name mysql emblockit/ssafy-mysql
>   $ docker ps -a
>   
>   $ docker exec -it mysql /bin/bash // 도커 컨테이너 내부 접속
>   $ mysql -p[패스워드 - admin] // mysql 접속
>   
>   > show databases;
>   > use [스키마명];
>   > show tables;
>   ```
>
> #### Git, Jira 생성
>
> #### 주제 선정 및 팀명 선정
>
> - 팀명 - **블록챌린**
> - 주제 - 블록체인을 기반으로 원하는 목표를 설정하고 목표 달성 시 보상받는 서비스
>
> #### 사용 툴(프로그램) 결정
>
> - Git : 코드 관리
> - Jira, Notion : 이슈 및 일정 관리
> - Swagger : API 관리 및 테스트
> - Jenkins : 빌드 관리
> - Jpa : DB 연동
>
> #### Git, Jira 관리자 결정
>
> #### Commit 규칙 결정

#### 

### :thinking: 내일 할 일

> #### 기능 설계
>
> #### 와이어프레임 설계
>
> #### DB 설계
>
> #### API 설계



### :bulb: 공부해야 할 것

> #### Jpa
>
> #### 스마트 컨트랙트
>
> ####  Multi Signiture



------

