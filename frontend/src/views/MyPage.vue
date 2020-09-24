<template>
  <div id="app">
    <div>
      <v-btn color="pink" dark top left style="margin: 2%;" @click="backHome">
        <v-icon dark left>arrow_back</v-icon>
        메인으로
      </v-btn>
      <v-btn dark top right style="margin: 2%; float: right;" @click="kakaoLogout">로그아웃</v-btn>
    </div>
    <div id="header">
      <h1><span>{{ user.nickname }}</span>님의 마이페이지</h1>
    </div>
    <div id="wallet">
      <h2>나의 지갑</h2>
      <div v-if="!walletFlag">
        <v-btn @click="createWallet" v-if="passwordFlag == 0">생성하기</v-btn>
        <div id="passwordArea" v-else-if="passwordFlag == 1">
          <p>비밀번호는 이더 사용/충전에 필요하며, 수정/재발급이 불가합니다. 꼭 기억해주세요!</p>
          <span id="passwordSpan">지갑 비밀번호</span>
          <v-text-field id="passwordInput" :rules="pwRules" v-model="password"></v-text-field>
          <v-btn id="passwordBtn" @click="submitPw">입력 완료</v-btn>
        </div>
      </div>
      <div v-else>
        <p>나의 계정 주소 {{ myWallet.walletAddress }}</p>
        <p>나의 잔고는 {{ myWallet.myEth }} 입니다.</p>
        <v-btn @click="charge">충전하기</v-btn>
        <!--<div v-if="!showPk" class="pkArea">
           <v-btn @click="showPkInput">비밀키 보기</v-btn>
         </div>
         <div v-else class="pkArea">
           <div id="inputPk">
             <p>나의 비밀키</p>
             <v-text-field :rules="pkRules" hint="blockchallen.txt 단어 16개 입력" v-model="pkWords"></v-text-field>
           </div>
           <div id="btnPk">
             <v-btn small color="primary" @click="checkPrivateKey">확인</v-btn>
           </div>
         </div>-->
      </div>
    </div>
    <div id="challenge">
      <h2>나의 챌린지</h2>
      <div id="totalSuccessRate">
        <canvas id="myChart" width="100" height="100"></canvas>
      </div>
      <div id="progressBars" v-for="challenge in user.challenges" :key="challenge.id">
        <div class="progressSet">
          <div class="challengeName"><p>{{ challenge.name }}</p></div>
          <v-progress-linear
              class="challengeProgress"
              color="red lighten-2"
              :buffer-value="challenge.progressRate"
              stream
          ></v-progress-linear>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Chart from 'chart.js'
import axios from 'axios'

const Web3 = require('web3')
const web3 = new Web3(new Web3.providers.HttpProvider('http://j3a102.p.ssafy.io:8545'))
// const web3 = new Web3(new Web3.providers.HttpProvider('https://mainnet.infura.io/v3/132d48f7fad8474db95aa5359cec4524'))
// const bip39 = require('bip39')

export default {
  name: "MyPage",
  data: () => ({
    user: {
      id: 0,
      challenges: [],
      email: "",
      nickname: "",
      access_token: "",
      walletAddress: "",
    },
    walletFlag: false,
    myWallet: {
      privateKey: "",
      walletAddress: "",
      myEth: 0,
    },
    /* Mnemonic 관련 변수 */
    // showPk: false,
    // pkWords: "",
    // pkRules: [
    //     value => !!value || 'blockchallen.txt 단어 16개 입력'
    // ],
    passwordFlag: 0,
    pwRules: [
      value => !!value || '지갑의 비밀번호를 입력해주세요',
      value => !(value.length < 4) || '최소 4자 이상'
    ],
    password: "",
  }),
  methods: {
    backHome() {
      this.$router.push("/challenges")
    },
    async createWallet() {
      this.passwordFlag = 1

      /*
      accounts.create()로 계정 생성
      privateKey Mnemonic 처리해서 사용자에게 자동 다운로드
      백엔드와 post 통신하여 DB에 계정 주소 저장
      */
      // let wallet = await web3.eth.accounts.create();
      // console.log(wallet)
      //
      // this.myWallet.privateKey = wallet.privateKey
      // this.myWallet.walletAddress = wallet.address
      // this.getWalletInfo(this.myWallet.walletAddress)
      //
      // const pkString = this.myWallet.privateKey.toString().substring(2)
      //
      // bip39.setDefaultWordlist('korean')
      //
      // const etm_prefix = bip39.entropyToMnemonic(pkString.substring(0, 32))
      // const etm_suffix = bip39.entropyToMnemonic(pkString.substring(32))
      //
      // this.download(etm_prefix + " " + etm_suffix)
      //

      /* rpc로 새로운 계정 생성 */
      // axios(
      //   {
      //     method: 'post',
      //     url: 'http://j3a102.p.ssafy.io:8545',
      //     headers: {
      //       "Content-Type": "application/json"
      //     },
      //     data: {
      //       "jsonrpc": '2.0',
      //       "method": 'personal_newAccount',
      //       'params': ['pass'],
      //       "id": 1
      //     }
      //   }
      // )
      // .then(res => {
      //   console.log(res)
      // })
      // .catch(err => {
      //   console.log(err)
      // })
    },
    async submitPw() {
      console.log(this.password)
      await web3.eth.personal.newAccount(this.password)
        .then(res => {
          const address = res

          axios.post(this.$store.state.server + '/wallet/create', {id: this.user.id, address: address})
          .then(res => {
            console.log(res)
            this.passwordFlag = 2
            this.myWallet.walletAddress = address
            this.walletFlag = true
          })
          .catch(err => {
            console.log(err)
          })
        })
    },
    charge() {
      alert("충전")
    },
    createChart() {
      const ctx = document.getElementById('myChart').getContext('2d')
      window.chart = new Chart(ctx, {
        type: 'pie',
        data: {
          datasets: [{
            data: [
              80,
              20
            ],
            backgroundColor: [
              'rgb(91, 132, 177)',
              'rgb(252, 118, 106)'
            ],
          }],
          labels: [
            '성공',
            '실패'
          ],
        },
        options: {
          responsive: true,
        }
      })
    },
    kakaoLogout() {
      let win = window.open('https://accounts.kakao.com/logout?continue=https://accounts.kakao.com/weblogin/account')
      win.close()
      sessionStorage.removeItem("user")
      this.$router.push("/")
    },
    async getWalletInfo(walletAddress) {
      this.myWallet.myEth = await web3.eth.getBalance(walletAddress)
    },
    download(content) {
      var pom = document.createElement('a')
      pom.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(content.normalize()))
      pom.setAttribute('download', 'blockchallenKey.txt')

      pom.click()
    },
    showPkInput() {
      this.showPk = true
    },
    checkPrivateKey() {
      // const mte = '0x' + bip39.mnemonicToEntropy(etm_prefix) + bip39.mnemonicToEntropy(etm_suffix)
      // console.log(mte)
    },
  },
  created() {
    axios.get(this.$store.state.server + '/mychallenges/' + this.user.id)
        .then(res => {
          console.log('나의 챌린지 목록')
          console.log(res)
          this.user.challenges = res.data
          console.log(this.user.challenges)
        })
        .catch(err => {
          console.log(err)
        })
  },
  mounted() {
    this.createChart()

    const user = JSON.parse(sessionStorage.getItem("user"))
    this.user = user

    axios.get(this.$store.state.server + '/wallet/' + this.user.id)
    .then(res => {
      const address = res.data.address

      if(address != null && address != ' ' && address != '') {
        this.myWallet.walletAddress = address
        this.getWalletInfo(this.myWallet.walletAddress)
        this.walletFlag = true
      }
    })
  }
}
</script>

<style scoped>
#app {
  width: 100%;
  height: 1vh;
  margin: 0 auto;
}

#header, #wallet, #challenge {
  width: 80%;
  height: auto;
  margin: 0 auto;
  text-align: center;
}

#header h1 span {
  color: red;
}

#wallet {
  width: 80%;
  height: auto;
  margin-top: 3%;
  text-align: center;
}

#passwordArea {
  width: 100%;
  height: auto;
  margin-top: 3%;
}

#passwordSpan {
  width: 30%;
  height: auto;
  float: left;
  margin-top: 1%;
}

.theme--light.v-input {
  padding: 0;
  margin: 0;
}

#passwordArea > div {
  width: 45%;
  float: left;
}

#passwordArea p {
  color: red;
}

#challenge {
  width: 80%;
  height: auto;
  margin-top: 3%;
  text-align: center;
}

#totalSuccessRate {
  width: 50%;
  height: auto;
  min-width: 212px;
  min-height: 212px;
  margin: 0 auto;
  margin-bottom: 3%;
}

#progressBars {
  width: 100%;
  height: 10vh;
  padding-top: 3vh;
  margin: 0 auto;
}

.progressSet {
  width: 100%;
  height: 5vh;
  margin-top: 1%;
  margin: 0 auto;
  float: none;
}

.challengeName {
  float: left;
  font-size: medium;
  font-weight: bold;
}

.challengeProgress {
  width: 80%;
  float: right;
  margin-left: 3vw;
  margin-top: 1%;
  vertical-align: center;
}

</style>