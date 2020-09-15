<template>
    <div id="app">
      <div>
        <v-btn color="pink" dark top left style="margin: 2%;" @click="backHome">
          <v-icon dark left>arrow_back</v-icon>메인으로
        </v-btn>
      </div>
      <div id="header">
        <h1>{{ user }}님의 마이페이지</h1>
      </div>
      <div id="wallet">
        <h2>나의 지갑</h2>
        <!--<div v-if="myWallet == null">-->
        <div v-if="!flag">
          <v-btn @click="createWallet">생성하기</v-btn>
        </div>
        <!--<div v-else>-->
        <div v-else>
          <p>나의 비밀키 {{ myWallet.privateKey }}</p>
          <p>나의 계정 주소 {{ myWallet.walletAddress }}</p>
          <p>나의 잔고는 {{ myEth }} 입니다.</p>
          <v-btn @click="charge">충전하기</v-btn>
        </div>
      </div>
      <div id="challenge">
        <h2>나의 챌린지</h2>
        <div id="totalSuccessRate">
          <canvas id="myChart" width="100" height="100"></canvas>
        </div>
        <div id="progressBars">
          <div class="progressSet">
            <div class="challengeName"><p>6시 기상 챌린지</p></div>
            <v-progress-linear
                class="challengeProgress"
                color="red lighten-2"
                buffer-value="50"
                stream
            ></v-progress-linear>
          </div>
          <div class="progressSet">
            <div class="challengeName"><p>30일 홈트 챌린지</p></div>
            <v-progress-linear
                class="challengeProgress"
                color="teal"
                buffer-value="70"
                stream
            ></v-progress-linear>
          </div>
        </div>
      </div>
    </div>
</template>

<script scoped>
import Chart from 'chart.js'
import Web3 from 'web3'

var web3 = new Web3(Web3.givenProvider || 'http://localhost:8545')

export default {
  name: "MyPage",
  data: () => ({
    user: "블록챌린",
    myEth: "100000",
    flag: false,
    myWallet: {
      privateKey: "",
      walletAddress: "",
    },
  }),
  methods: {
    backHome() {
      this.$router.push("/challengeList")
    },
    createWallet() {
      alert("지갑 생성")

      let wallet = web3.eth.accounts.create();

      console.log(wallet)
      console.log(wallet.privateKey)
      console.log(wallet.address)

      this.myWallet.privateKey = wallet.privateKey
      this.myWallet.walletAddress = wallet.address

      this.flag = true
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
              // 'rgba(119, 146, 174, 0.7)',
              // 'rgba(192, 41, 66, 0.7)'
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
    }
  },
  mounted() {
    this.createChart()
  }
}
</script>

<style>
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