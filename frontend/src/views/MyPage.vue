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
        <div>
          <v-btn @click="createWallet">생성하기</v-btn>
        </div>
        <!--<div v-else>-->
        <div>
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
          <div>

          </div>
        </div>
      </div>
    </div>
</template>

<script scoped>
import Chart from 'chart.js'
import Web3 from 'web3'

export default {
  name: "MyPage",
  data: () => ({
    user: "블록챌린",
    myEth: "100000",
    myWallet: null,

  }),
  methods: {
    backHome() {
      this.$router.push("/challengeList")
    },
    createWallet() {
      alert("지갑 생성")
      // let web3 = new Web3(Web3.givenProvider || 'rpc:http://localhost:8545')
      
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
              20,
              20,
              20,
              20,
              10,
              10
            ],
            backgroundColor: [
              'rgb(236, 208, 120)',
              'rgba(217, 91, 67, 0.7)',
              'rgba(192, 41, 66, 0.7)',
              'rgba(84, 36, 55, 0.7)',
              'rgba(83, 119, 122, 0.7)',
              'rgba(119, 146, 174, 0.7)'
            ],
          }],
          labels: [
            '챙',
            '몰라',
            '감자',
            '코섭',
            '쑤',
            '그나링'
          ],
        },
        options: {
          responsive: true
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
}

</style>