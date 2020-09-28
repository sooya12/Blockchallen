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
      <div v-if="!chargeFlag">
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
          <p>나의 잔고는 {{ myWallet.myEth / 1000000000000000000 }} ETH 입니다.</p>
          <v-btn @click="charge">충전하기</v-btn>
        </div>
      </div>
      <div id="loadingArea" v-else>
        <my-page-loading></my-page-loading>
      </div>
    </div>
    <div id="challenge" v-show="!chargeFlag">
      <h2>나의 챌린지</h2>
      <div id="totalSuccessRate">
        <canvas id="myChart" width="100" height="100"></canvas>
      </div>
      <div v-if="progressBarFlag">
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
      <div id="loading" v-else>
        <v-progress-circular
            :size="70"
            :width="7"
            color="purple"
            indeterminate
        ></v-progress-circular>
      </div>
    </div>
  </div>
</template>

<script>
import Chart from 'chart.js'
import axios from 'axios'
import MyPageLoading from '@/components/MyPageLoading.vue'

const Web3 = require('web3')
const web3 = new Web3(new Web3.providers.HttpProvider('http://j3a102.p.ssafy.io:8545'))

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
    chargeFlag: false,
    walletFlag: false,
    myWallet: {
      privateKey: "",
      walletAddress: "",
      myEth: 0,
    },
    passwordFlag: 0,
    pwRules: [
      value => !!value || '지갑의 비밀번호를 입력해주세요',
      value => !(value.length < 4) || '최소 4자 이상'
    ],
    password: "",
    progressBarFlag: false
  }),
  components: {
    MyPageLoading
  },
  methods: {
    backHome() {
      this.$router.push("/challenges")
    },
    async createWallet() {
      this.passwordFlag = 1
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
    async charge() {
      alert("충전")
      this.chargeFlag = true

      await web3.eth.sendTransaction({
        from: "0x03fb923A157c20565E36D7d518418E1b9b0c2C86",
        gasPrice: "1600000000",
        gas: "2100000",
        to: this.myWallet.walletAddress,
        value: "1000000000000000000",
        data: "",
      }, 'ssafy').then(() => {
        this.chargeFlag = false
        this.createChart()
      })

      this.getWalletInfo(this.myWallet.walletAddress)
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
      let pom = document.createElement('a')
      pom.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(content.normalize()))
      pom.setAttribute('download', 'blockchallenKey.txt')

      pom.click()
    },
    showPkInput() {
      this.showPk = true
    },
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

    axios.get(this.$store.state.server + '/mychallenges/' + this.user.id)
        .then(res => {
          console.log('나의 챌린지 목록')
          console.log(res)
          this.user.challenges = res.data
          console.log(this.user.challenges)
          this.progressBarFlag = true
        })
        .catch(err => {
          console.log(err)
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

#loadingArea {
  width: 100%;
  height: 35vh;
  margin-bottom: 3%;
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

#loading {
  margin-top: 3%;
}

</style>