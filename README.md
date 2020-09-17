# 블록챌린 - BlockChallen

##### 블록체인을 기반으로 원하는 목표를 설정하고, 목표 달성 시 ERC-20 토큰으로 보상받는 서비스

![img](Img\blockchallen.png)

##### 

-------



### :black_nib: 와이어프레임

![img](Img\wireframe.png)



-------



### :hammer_and_wrench: 사용 기술

![batge](https://img.shields.io/badge/Ubuntu-18.04.1%20LTS-blue)![batge](https://img.shields.io/badge/Docker-19.03.12-blue)![batge](https://img.shields.io/badge/MySQL-8.0.20-blue) ![batge](https://img.shields.io/badge/Vue.js-2.6.11-blue)![batge](https://img.shields.io/badge/Vagrant-2.2.10-blue) ![batge](https://img.shields.io/badge/Geth-1.9.20-blue)

![batge](https://img.shields.io/badge/OpenJDK-1.8-blue) ![batge](https://img.shields.io/badge/Node.js-12.18.2-blue) 

![batge](https://img.shields.io/badge/STS-3.9.12-lightgrey) ![batge](https://img.shields.io/badge/Intellij-2020.2.1-lightgrey) ![batge](https://img.shields.io/badge/VSCode-1.49.0-lightgrey) ![batge](https://img.shields.io/badge/Gitlab-11.6.2-yellow) ![batge](https://img.shields.io/badge/Jira-8.2-yellow) ![batge](https://img.shields.io/badge/Notion----yellow)



----



### :page_facing_up: Commit 규칙

##### [BE/FE/FS/BC] initial/update | 내용 | Jira 이슈 번호

> BE : 백
>
> FE : 프론트
>
> FS : 풀스택
>
> BC : 블록체인

##### README(이름)



### :page_facing_up: Branch 규칙

##### feature/기능명 으로 develop에서 분기해서 사용하고, 기능 개발 완료 후 develop에 머지!



----



### :computer: AWS 서버

```aws
// AWS 접속
$ ssh -i [pem 저장 경로]/[pem 키] ubuntu@j3a102.p.ssafy.io

// Docker 접속
$ sudo docker exec -it eth /bin/bash

// geth 백그라운드 실행
$ nohup geth --networkid 1225 --maxpeers 2 --datadir /node1/ --rpc --rpcapi "admin,db,eth,debug,miner,net,shh,txpool,personal,web3" --rpccorsdomain "*" --rpcaddr='0.0.0.0' --rpcvhosts="*" --rpcport "8545" >> /node1/geth.log &

// geth 접속
$ geth attach rpc:http://localhost:8545
$ geth attach rpc:http://j3a102.p.ssafy.io:8545
```

