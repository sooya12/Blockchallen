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
    <div id="tabs">
      <v-tabs fixed centered color="#f39c14">
        <v-tab @click="changeDivs"><h3><font-awesome-icon icon="coins"></font-awesome-icon> 나의 지갑</h3></v-tab>
        <v-tab @click="changeDivs"><h3><font-awesome-icon icon="thumbs-up"></font-awesome-icon> 나의 챌린지</h3></v-tab>
      </v-tabs>
    </div>
    <div id="wallet" v-show="showDiv">
      <div v-if="firstLoading">
        <v-progress-circular
            :size="70"
            :width="7"
            color="#f39c14"
            indeterminate
        ></v-progress-circular>
      </div>
      <div id="walletInfo" v-if="!firstLoading && !chargeFlag">
        <div v-if="!walletFlag">
          <v-btn @click="createWallet" v-if="passwordFlag == 0" color="#f39c14">생성하기</v-btn>
          <div id="passwordArea" v-else-if="passwordFlag == 1">
            <p>비밀번호는 이더 사용/충전에 필요하며, 수정/재발급이 불가합니다. 꼭 기억해주세요!</p>
            <span id="passwordSpan">지갑 비밀번호</span>
            <v-text-field id="passwordInput" :rules="pwRules" v-model="password"></v-text-field>
            <v-btn id="passwordBtn" @click="submitPw" color="#f39c14">입력 완료</v-btn>
          </div>
        </div>
        <div v-else>
          <div v-if="walletLoading">
            <p>지갑 생성 중입니다.</p>
            <v-progress-circular
                :size="70"
                :width="7"
                color="#f39c14"
                indeterminate
            ></v-progress-circular>
          </div>
          <div v-else>
            <v-text-field
                :value="myWallet.walletAddress"
                label="나의 계정 주소"
                outlined
                readonly
            ></v-text-field>
            <v-text-field
                :value="Math.floor(myWallet.myEth / 1000000000000000000)"
                label="나의 잔고"
                outlined
                readonly
                suffix="ETH"
            ></v-text-field>
            <v-card v-if="kakaopay" style="padding: 3%; ">
              <h2 style="text-align: left; font-size: 2vh;">카카오 페이로 이더 충전하기</h2>
              <br><br>
              <v-slider
                  v-model="kakaoEther"
                  :label="kakaoCoin.label"
                  :thumb-color="kakaoCoin.color"
                  max="15"
                  min="1"
                  thumb-label="always"
              ></v-slider>
              <h3>{{kakaoEther}} 이더</h3>
              <br><br>
            <kakao-pay :url="kakaourl" v-if="kakaourl" style="height: 50vh; overflow: hidden;" scrolling="no" frameBorder="0"></kakao-pay>
              <v-btn @click="kakaoPay" color="#f39c14">충전하기</v-btn>
              <v-btn @click="kakaopay=false" color="#f39c14" style="margin-left: 8%;">취소</v-btn>
            </v-card>
            <v-btn @click="kakaopay=true" color="#f39c14" v-if="!kakaopay">카카오페이로 충전하기</v-btn>

          </div>
        </div>
      </div>
      <div id="loadingArea" v-else-if="!firstLoading">
        <my-wallet-charging></my-wallet-charging>
        <div>
          <p>충전 중입니다.</p>
          <p><span>수 초</span> ~ <span>수 분</span>이 걸릴 수 있습니다.</p>
          <p>잠시만 기다려주세요.</p>
        </div>
      </div>
    </div>
    <div id="challenge" v-show="!showDiv">
      <div id="noticeChallenge" v-if="chartFlag == 0">
        <p>참여 중인 챌린지가 아직 없어요!</p>
        <p>챌린지에 참여해주세요!</p>
      </div>
      <div id="totalSuccessRate" v-else-if="chartFlag == 2">
        <canvas id="myChart" width="100" height="100"></canvas>
      </div>
      <div v-if="progressBarFlag">
        <div id="progressBars" v-for="(challenge) in user.challenges" :key="challenge.id">
          <div class="progressSet">
            <v-card class="challengeCard" elevation="3" @click="moveChallenge(challenge.id)">
              <div class="challengeName">
                <span> {{ challenge.name }} </span>
                <div class="chips">
                  <v-chip small v-if="challenge.running" color="#f39c14">진행 중</v-chip>
                  <v-chip small v-else color="#bbbbbb">마감</v-chip>
                  <v-chip small v-if="!challenge.running && challenge.progressRate < 85" color="#FC766A">실패</v-chip>
                  <v-chip small v-else-if="!challenge.running && challenge.progressRate >= 85" color="#5C84B1">성공</v-chip>
                </div>
              </div>
              <v-progress-linear
                  v-if="challenge.running"
                  class="challengeProgress"
                  :value="challenge.progressRate"
                  :color="progressColor[0]"
                  height="23"
              >
                <template :v-slot="challenge.progressRate">
                  <strong>{{ challenge.progressRate }}%</strong>
                </template>
              </v-progress-linear>
              <v-progress-linear
                  v-else
                  class="challengeProgress"
                  :value="challenge.progressRate"
                  :color="progressColor[1]"
                  height="23"
              >
                <template :v-slot="challenge.progressRate">
                  <strong>{{ challenge.progressRate }}%</strong>
                </template>
              </v-progress-linear>
            </v-card>
          </div>
        </div>
      </div>
      <div id="loading" v-else>
        <v-progress-circular
            :size="70"
            :width="7"
            color="#f39c14"
            indeterminate
        ></v-progress-circular>
      </div>
    </div>
  </div>
</template>

<script>
import Chart from 'chart.js'
import axios from 'axios'
import MyWalletCharging from '@/components/MyWalletCharging.vue'
import KakaoPay from "@/components/KakaoPay";
import EventBus from "@/EventBus";

const Web3 = require('web3')
const web3 = new Web3(new Web3.providers.HttpProvider('https://j3a102.p.ssafy.io/geth'))

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
      walletAddress: "",
      myEth: 0,
    },
    passwordFlag: 0,
    pwRules: [
      value => !!value || '지갑의 비밀번호를 입력해주세요',
      value => !(value.length < 4) || '최소 4자 이상'
    ],
    password: "",
    progressBarFlag: false,
    progressColor: [
      'orange darken-1',
      'grey'
    ],
    showDiv: true,
    pieSuccess: 0,
    pieFail: 0,
    pieRunning: 0,
    chartFlag: 0,
    firstLoading: true,
    walletLoading: false,
    kakaourl: '',
    kakaopay : false,
    kakaoCoin: { label: '충전 금액', val: 15, color: 'red' },
    kakaoEther : 1,
  }),
  components: {
    MyWalletCharging,
    KakaoPay
  },
  methods: {
    backHome() {
      this.$router.push("/challenges")
    },
    async createWallet() {
      this.passwordFlag = 1
    },
    async submitPw() {
      this.walletFlag = true
      this.walletLoading = true
      await web3.eth.personal.newAccount(this.password)
          .then(res => {
            const address = res

            axios.post(this.$store.state.server + '/wallet/create', {id: this.user.id, address: address})
                .then(res => {
                  console.log(res)
                  this.passwordFlag = 2
                  this.myWallet.walletAddress = address
                  this.walletLoading = false
                })
                .catch(err => {
                  console.log(err)
                })
          })
    },
    async charge(chargeEther) {
      this.chargeFlag = true
      let price=1001000000000000000*chargeEther+""
      await web3.eth.sendTransaction({
        from: "0x03fb923A157c20565E36D7d518418E1b9b0c2C86",
        gasPrice: "1000000",
        gas: "4700000",
        to: this.myWallet.walletAddress,
        value: price,
        data: "",
      }, 'ssafy').then(() => {
        this.chargeFlag = false

        if (this.user.challenges.length > 0 && this.chartFlag == 2) {
          this.createChart()
        }
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
              (this.pieSuccess / (this.pieSuccess + this.pieFail)) * 100,
              (this.pieFail / (this.pieSuccess + this.pieFail)) * 100,
              /*(this.pieSuccess / (this.pieSuccess + this.pieFail + this.pieRunning)) * 100,
              (this.pieFail / (this.pieSuccess + this.pieFail + this.pieRunning)) * 100,
              (this.pieRunning / (this.pieSuccess + this.pieFail + this.pieRunning)) * 100,*/
            ],
            backgroundColor: [
              '#5C84B1',
              '#FC766A',
              /*'#ffd696'*/
            ],
          }],
          labels: [
            '성공',
            '실패',
            /*'진행'*/
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
    moveChallenge(id) {
      this.$router.push("/challenges/" + id)
    },
    changeDivs() {
      if(this.showDiv == true) {
        this.showDiv = false
      } else {
        this.showDiv = true
      }
    },
    test(ether){
      alert('hi'+ether)
      console.log('hi')
    },


    kakaoPay(){
      let filter = "win16|win32|win64|mac|macintel";
      let url = ""
      axios.get(this.$store.state.server+"/kakaopay/ready",{
          params :{

            "ether" : Number(this.kakaoEther)
          }


      }).then((res)=>{
        if ( navigator.platform ) {
          if (filter.indexOf(navigator.platform.toLowerCase()) < 0) {
            //mobile
            url=res.data.next_redirect_mobile_url

          } else {
            //pc
            url=res.data.next_redirect_pc_url
          }
        }
        this.kakaourl=url




      })
    }
  },
  created() {
    EventBus.$on('charge',this.test)
  },
  mounted() {

    const user = JSON.parse(sessionStorage.getItem("user"))
    this.user = user

    axios.get(this.$store.state.server + '/wallet/' + this.user.id)
        .then(res => {
          const address = res.data.address

          if (address != null && address != ' ' && address != '') {
            this.myWallet.walletAddress = address
            this.getWalletInfo(this.myWallet.walletAddress)
            this.walletFlag = true
          }

          this.firstLoading = false
          if(sessionStorage.getItem('chargeEther')!=null){
            let chareEther =sessionStorage.getItem('chargeEther')
            sessionStorage.removeItem('chargeEther')
            this.charge(chareEther)
          }
        })

    axios.get(this.$store.state.server + '/mychallenges/' + this.user.id)
        .then(res => {
          console.log(res.data)
          this.user.challenges = res.data
          this.progressBarFlag = true

          for(var i = 0; i < this.user.challenges.length; i++) {
            if(!this.user.challenges[i].running) {
              if(this.user.challenges[i].progressRate >= 85) {
                this.pieSuccess += 1
              } else {
                this.pieFail += 1
              }
            } else {
              this.pieRunning += 1
            }
          }

          if(this.user.challenges.length > 0) {
            if(this.pieSuccess > 0 && this.pieFail > 0) {
              this.createChart()
            } else {
              this.chartFlag = 1
            }
          } else {
            this.chartFlag = 2
          }
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
  max-width: 1000px;
  height: 1vh;
  margin: 0 auto;
}

#header, #wallet, #challenge {
  width: 80%;
  max-width: 1000px;
  height: auto;
  margin: 0 auto 5%;
  text-align: center;
}

#header h1 span {
  color: #f39c14;
}

#tabs {
  width: 100%;
  margin-bottom: 7%;
}

#walletInfo {
  max-width: 500px;
  margin: 0 auto;
}

#loadingArea {
  width: 100%;
  height: 35vh;
  margin-bottom: 3%;
}

#loadingArea span {
  color: #1e88e5;
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

#totalSuccessRate {
  width: 50%;
  min-width: 212px;
  height: auto;
  min-height: 212px;
  margin: 0 auto 3%;
  padding: 0;
}

#progressBars {
  width: 100%;
  height: 14vh;
  padding-top: 3vh;
  margin: 0 auto;
}

.progressSet {
  width: 100%;
  height: 13vh;
  margin-bottom: 3vh;
  float: none;
}

.challengeCard {
  width: 100%;
  height: 12vh;
  padding: 1vw;
}

.challengeName {
  width: 100%;
  float: left;
  font-size: medium;
  font-weight: bold;
}

.challengeName > span {
  float: left;
}

.challengeProgress {
  width: 90%;
  float: right;
  margin: 2vh;
  vertical-align: center;
}

.chips {
  float: right;
}

#loading {
  margin-top: 5%;
}

</style>