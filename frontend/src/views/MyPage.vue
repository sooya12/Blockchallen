<template>
  <div id="app">
    <div>
      <v-btn color="pink" dark top left style="margin: 2%;" @click="backHome">
        <v-icon dark left>arrow_back</v-icon>
        메인으로
      </v-btn>
    </div>
    <div id="header">
      <h1><span>{{ user.nickname }}</span>님의 마이페이지</h1>
      <v-btn @click="kakaoLogout">로그아웃</v-btn>
    </div>
    <div id="wallet">
      <h2>나의 지갑</h2>
      <div v-if="!flag">
        <v-btn @click="createWallet">생성하기</v-btn>
      </div>
      <div v-else>
        <!--<p>나의 비밀키 {{ myWallet.privateKey }}</p>-->
        <p>나의 계정 주소 {{ myWallet.walletAddress }}</p>
        <p>나의 잔고는 {{ myWallet.myEth }} 입니다.</p>
        <v-btn @click="charge">충전하기</v-btn>
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
              :buffer-value="challenge.rate"
              stream
          ></v-progress-linear>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Chart from 'chart.js'
import Web3 from 'web3'
import axios from 'axios'
// import bip39 from 'bip39'

var web3 = new Web3(Web3.givenProvider || 'http://j3a102.p.ssafy.io:8545')

const bip39 = require('bip39')

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
    flag: false,
    myWallet: {
      privateKey: "",
      walletAddress: "",
      myEth: 0,
    },
  }),
  methods: {
    backHome() {
      this.$router.push("/challenges")
    },
    createWallet() {
      let wallet = web3.eth.accounts.create();

      this.myWallet.privateKey = wallet.privateKey
      this.myWallet.walletAddress = wallet.address
      this.getWalletInfo(this.myWallet.walletAddress)

      const pkString = this.myWallet.privateKey.toString().substring(2)
      console.log(pkString.substring(0, 32))
      console.log(pkString.substring(32))

      bip39.setDefaultWordlist('korean')

      const etm_prefix = bip39.entropyToMnemonic(pkString.substring(0, 32))
      const etm_suffix = bip39.entropyToMnemonic(pkString.substring(32))
      console.log(etm_prefix)
      console.log(etm_suffix)

      const mte = '0x' + bip39.mnemonicToEntropy(etm_prefix) + bip39.mnemonicToEntropy(etm_suffix)
      console.log(mte)

      const content = etm_prefix + " " + etm_suffix

      this.download(content)

      // axios.post('http://localhost:8080/blockchallen/wallet/create', {id: this.user.id, address: this.myWallet.walletAddress})
      // .then(
      //   this.flag = true
      // )
      // .catch(err => {
      //   console.log(err)
      // })
    },
    charge() {
      alert("충전")
    },
    createChart() {
      var ctx = document.getElementById('myChart').getContext('2d')
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
      pom.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(content))
      pom.setAttribute('download', 'blockchallenKey.txt')

      pom.click()
    },
  },
  mounted() {
    this.createChart()

    const user = JSON.parse(sessionStorage.getItem("user"))
    user.challenges = [
      {
        id: 1,
        name: '6시 기상 챌린지',
        rate: 80
      },
      {
        id: 2,
        name: '코로나 챌린지',
        rate: 40
      }
    ]
    this.user = user

    axios.get('http://localhost:8080/blockchallen/wallet/' + this.user.id)
    .then(res => {
      const address = res.data.address

      if(address != null && address != ' ' && address != '') {
        this.myWallet.walletAddress = address
        this.getWalletInfo(this.myWallet.walletAddress)
        this.flag = true
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
  maring: 0 auto;
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