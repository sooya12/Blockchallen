<template>
  <div style="width: 100%; max-width: 1000px; margin: 0 auto;">


  <div v-if="isLoading">
    <v-btn
        color="pink"
        dark
        top
        left
        style="margin: 2%;"
        @click="goMain"
    >
      <v-icon dark left>arrow_back</v-icon>
      메인으로
    </v-btn>
    <v-btn
        color="#000000"
        top
        style="margin: 2%; float: right; color : #ffffff;"
        @click="logout"
    >
      로그아웃
    </v-btn>
    <v-btn
        color="#ffffff"
        top
        right
        elevation="0"
        style="margin: 2%; float: right;"
        @click="goMypage"
    >
      {{ user.nickname }} 님
    </v-btn>
    <loading></loading>
  </div>
  <div v-if="!isLoading">
    <v-btn
        color="pink"
        dark
        top
        left
        style="margin: 2%;"
        @click="goMain"
    >
      <v-icon dark left>arrow_back</v-icon>
      메인으로
    </v-btn>
    <v-btn
        color="#000000"
        top
        right
        style="margin: 2%; float: right; color : #ffffff;"
        @click="logout"
    >
      로그아웃
    </v-btn>
    <v-btn
        color="#ffffff"
        top
        right
        elevation="0"
        style="margin: 2%; float: right;"
        @click="goMypage"
    >
      {{ user.nickname }} 님
    </v-btn>
    <div style="margin-left: 5%; margin-top: 3%;">
      <v-card style="width: 90%; max-width: 1000px;">
          <v-img
              src="/lego.ico"
              height="4vh"
              width="4vh"
              style="float:left; margin:2%;"
          ></v-img>
          <p style="font-size:4vh; font-weight: bold; margin-left:3%; padding-top:1%; float:left; color: #f39c14;">{{ title }}</p>
          <v-btn style="float:right; margin:2%; " @click="certification(cid)" v-if="alreadyParicipate && todaystate && certificationAvailableTime && ongoing">
              인증하기
          </v-btn>
          <br style="clear:both;"/>
        <div>
          <span style="font-size:2.5vh; font-weight: bold; margin-left: 2%; margin-right: 3%;">{{ startDate }}</span> ~
          <span style="font-size:2.5vh; font-weight: bold; margin-left: 3%;">{{ endDate }} </span> <span
            style="color:#ff5555; margin-left: 1%;">({{ periods }})</span>
        </div>
      </v-card>
      <v-card style="width: 90%; max-width: 1000px; padding: 1% 2%; margin-top: 3%;">
        <p style="font-size:2.5vh; margin-top: 2%;">배팅 금액 : <span style="font-size:3vh; font-weight: bold"> {{
            fee
          }} ETH</span></p>
        <p style="font-size:2.5vh; margin-top: 2%;">분배 방식 : <span style="font-size:3vh; font-weight: bold"> {{
            divide
          }}</span></p>
        <p style="font-size:2.5vh; margin-top: 2%;">인증 조건 : <span
            style="font-size:2.5vh; font-weight: bold"> {{ certificationCondition }}</span></p>
        <p style="font-size:2.5vh; margin-top: 2%;">인증 가능 시간 : <span
            style="font-size:2.5vh; font-weight: bold"> {{ timepick[certificationStartTime] }} ~ {{ timepick[certificationEndTime] }}</span></p>
        <div v-if="samplepicture">
          <p style="font-size:2.5vh; margin-top: 2%;">인증 예시 </p>
          <v-img :src="samplepicture" style="width:50%; margin-right: 10%; margin-bottom: 5%;"></v-img>
        </div>
      </v-card>
      <v-card style="width: 90%; max-width: 1000px; padding: 1% 2%; margin-top: 3%;">
        <p style="font-size:2.5vh; margin-top: 2%;  font-weight: bold;">현재 참여 인원 : {{ users.length }}명</p>
        <p style="font-size:2.5vh; margin-top: 2%;  font-weight: bold; " v-if="challengeState=='before'">현재 까지
          {{ gather }} ETH가 모였습니다.</p>
        <p style="font-size:2.5vh; margin-top: 2%;  font-weight: bold; " v-if="challengeState!='before'">도전 금액 :
          {{ gather }} ETH</p>

      </v-card>
      <v-card style="width: 90%; max-width: 1000px; padding: 1% 2%; margin-top: 3%;">
        <div v-if="challengeState=='before'">
          <p style="font-size:2.5vh; margin-top: 2%; color:#ff5555; font-weight: bold;">마감까지 {{ remain }}</p>
          <p style="font-size:2.5vh; margin-top: 2%;  font-weight: bold;" v-if="alreadyParicipate">참여 중인 챌린지입니다.</p>
        </div>
        <div v-if="challengeState=='notStart'">
          <p style="font-size:2.5vh; margin-top: 2%; color:#ff5555; font-weight: bold;">본 챌린지는 참가 신청이 마감되어 곧 시작됩니다.</p>
          <p style="font-size:2.5vh; margin-top: 2%;  font-weight: bold;" v-if="alreadyParicipate">참여 중인 챌린지입니다.</p>
        </div>
        <div v-if="challengeState=='doing'">
          <p style="font-size:2.5vh; margin-top: 2%; color:#ff5555; font-weight: bold;">본 챌린지는 진행중입니다. </p>
          <p style="font-size:2.5vh; margin-top: 5%;  font-weight: bold;">
            챌린지 현황
          </p>
          <div v-for="participant of userlist" v-bind:key="participant.id">
            <v-card style="margin : 5% 1%; margin-top : 1%; min-height: 32vh;"
                    @click="clickParticipant(participant,total)">
              <div>

                <div style="float:left; width:40%; height : 20vh;">
                  <v-img
                      :src=participant.certification.picture
                      height="30vh"
                      style="margin: 2%;"

                  >
                  </v-img>
                </div>

                <div style="float:left; padding-left : 5%; width:60%;">
                  <v-img
                      src="/certification.jpg"
                      height="15vh"
                      width="15vh"
                      style=" float: right;  margin-right: 2%; margin-top: 2%;"
                      v-if="!participant.certification.isReported"
                  >
                  </v-img>
                  <v-img
                      src="/uncertification.jpg"
                      height="15vh"
                      width="15vh"
                      style=" float: right;  margin-right: 2%; margin-top: 2%;"
                      v-if="participant.certification.isReported"
                  >
                  </v-img>
                  <div style=" margin-top: 5%; ">
                    <i><img src="/lego.png" style="height:8vh;"></i>
                    <p style="font-size:2vh;  font-weight: bold;">{{ participant.nickname }}</p>
                  </div>

                  <i>upload at</i>
                  <p>{{ participant.certification.regDate }}</p>
                  <p style="font-size:2vh;  font-weight: bold;">진행도 : <span
                      style="color:#ff5555;">{{ participant.progress }} / {{ total }}</span></p>
                  <block-progress :progress="participant.progress" :total="total"></block-progress>


                </div>
                <br style="clear:both;"/>

              </div>
            </v-card>
          </div>


        </div>
        <div v-if="challengeState=='done'">
          <p style="font-size:2.5vh; margin-top: 2%; color:#ff5555; font-weight: bold;">본 챌린지는 마감되었습니다.</p>
          <p style="font-size:2.5vh; margin-top: 5%;  font-weight: bold;">
            결과
          </p>
          <p style="font-size:2.5vh;  font-weight: bold;"><span style="font-size: 2.2vh;">성공 인원 : </span>
            {{ successlist.length }}명</p>
          <p style="font-size:2.5vh;  font-weight: bold;"><span style="font-size: 2.2vh;">실패 인원 : </span>
            {{ faillist.length }}명</p>
          <v-card style="margin : 5% 1%; margin-top : 1%; padding : 1%;  padding-top : 2%;" v-if="successlist.length>0">
            <div v-for="(success,index) in successlist" v-bind:key="success.id">
              <div>
                <p style="font-size:2vh; font-weight: bold; float:left; width:50%; text-align: center;">
                  {{ success.nickname }}
                </p>
              </div>
              <div>
                <p style="font-size:2vh; font-weight: bold; text-align: center;">
                  <font-awesome-icon icon="medal" v-if="index==0&&isRandom" style="color:gold;"></font-awesome-icon>
                  <font-awesome-icon icon="medal" v-if="index==1&&isRandom" style="color:silver;"></font-awesome-icon>
                  <font-awesome-icon icon="medal" v-if="index==2&&isRandom" style="color:#cd7f32;"></font-awesome-icon>

                  <span v-if="index<3" style="font-size:2.2vh;">{{ insertCommaInNumber(success.prize) }}원</span>
                  <span v-if="index>2" style="font-size:2vh;">{{ insertCommaInNumber(success.prize) }}원</span>
                </p>
              </div>

            </div>
          </v-card>
        </div>
      </v-card>
      <div style="width:70%; padding: 1% 2%; margin-top: 3%; text-align: center;" v-if="challengeState=='before'">
        <v-dialog
            v-model="passwordDialog"
            persistent
            max-width="600px"
        >

          <v-card>
            <v-card-title>
              <span >비밀번호 입력</span>
            </v-card-title>
            <v-card-text>
              <v-card-text>
                챌린지 인원 미 충족시, 챌린지가 취소 될 수 있습니다.<br>
                위의 경우를 제외하고는 어떠한 경우에도 참가 신청을 철회 할 수 없습니다.
              </v-card-text>
              <v-checkbox
                  v-model="participateAgree"
                  label="위 사실에 동의합니다."
              ></v-checkbox>
              <div v-if="participateAgree">
              <v-container>
                <v-snackbar
                    v-model="participateSnackbar"
                    :timeout="3000"
                    :value="participateSnackbar"
                    absolute
                    centered
                    color="red darken-2"
                    elevation="36"
                    style="z-index: 3;"
                >
                  {{ participateSnackbarText }}
                </v-snackbar>
                <v-text-field
                    v-model="password"
                    :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
                    :rules="[passwordRules.required, passwordRules.min]"
                    :type="passwordShow ? 'text' : 'password'"
                    name="input-10-2"
                    label="비밀번호"
                    hint="입력"
                    :value="password"
                    class="input-group--focused"
                    @click:append="passwordShow = !passwordShow"

                    style="width:50%; "
                ></v-text-field>
              </v-container>
              <small>지갑 비밀번호를 입력해주세요.</small>
              </div>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                  color="blue darken-1"
                  text
                  @click="passwordDialog = false"
              >
                취소
              </v-btn>
              <v-btn
                  color="blue darken-1"
                  text
                  @click="participate"
                  v-if="participateAgree"
              >
                참가하기
              </v-btn>
            </v-card-actions>
          </v-card>
          <v-overlay :value="overlay" style="text-align: center;">
            <p v-if="overlayProgress==1"> 내 계좌 전송 준비중...</p>
            <p v-if="overlayProgress==2"> 내 계좌에서 챌린지 계좌로 송금중...</p>
            <p v-if="overlayProgress==3"> 서버로 결과 전송중...</p>
            <v-progress-circular
                indeterminate
                size="64"
            ></v-progress-circular>
          </v-overlay>
        </v-dialog>
        <v-btn color="error" dark large style="margin: 2% 0; width:50%; height: 8vh; font-size:3vh; font-weight: bold;" v-if="!alreadyParicipate" @click="passwordDialog=true">
          참여하기
        </v-btn>

      </div>


    </div>
  </div>

  </div>
</template>

<script>
import axios from 'axios'
import BlockProgress from "@/components/BlockProgress";
import ChallengeModal from "@/components/ChallengeModal";
import PictureModal from "@/components/PictureModal";
import Loading from "@/components/Loading";
const Web3 = require('web3')
const web3 = new Web3(new Web3.providers.HttpProvider('https://j3a102.p.ssafy.io/geth'))
let offset = new Date().getTimezoneOffset() * 60000;
export default {
  name: "challengeDetail",
  components: {
    BlockProgress,
    Loading
  },
  props: {
    cid: {
      type: Number
    }
  },
  data() {
    return {
      isLoading : true,
      title: '',
      startDate: '',
      endDate: '',
      expireDate: '',
      fee: 0,
      isRandom: false,
      divide: '',
      certificationCondition: '',
      users: [],
      challengeState: 'before',
      periods: '0',
      expire: '',
      remain: '',
      gather: 0,
      userlist: [],
      total: 0,
      successlist: [],
      faillist: [],
      timepick : [
        "00:00",
        "00:30",
        "01:00",
        "01:30",
        "02:00",
        "02:30",
        "03:00",
        "03:30",
        "04:00",
        "04:30",
        "05:00",
        "05:30",
        "06:00",
        "06:30",
        "07:00",
        "07:30",
        "08:00",
        "08:30",
        "09:00",
        "09:30",
        "10:00",
        "10:30",
        "11:00",
        "11:30",
        "12:00",
        "12:30",
        "13:00",
        "13:30",
        "14:00",
        "14:30",
        "15:00",
        "15:30",
        "16:00",
        "16:30",
        "17:00",
        "17:30",
        "18:00",
        "18:30",
        "19:00",
        "19:30",
        "20:00",
        "20:30",
        "21:00",
        "21:30",
        "22:00",
        "22:30",
        "23:00",
        "23:30",
        "24:00"
      ],
      certificationStartTime : '',
      certificationEndTime : '',
      certificationAvailableTime : false,
      alreadyParicipate:false,
      todaystate:false, // 오늘 했는지 안했는지 (하루에 1번)
      samplepicture: '',
      ongoing:false, // 진행중인 챌린지인지 아닌지
      challengeAddress : '',
      walletAddress : '',
      balance : 0,
      passwordShow: false,
      password: '',
      passwordRules: {
        required: password => !!password || '입력해주세요.',
        min: v => v.length == 4 || '비밀번호는 4글자 입니다.',

      },
      passwordDialog : false,
      participateAgree:false,
      participateSnackbar:false,
      participateSnackbarText:'',
      overlay: false,
      overlayProgress : 0,
      user: [],
    }
  },
  mounted() {
    const user = JSON.parse(sessionStorage.getItem("user"))
    this.user = user

    axios.get(this.$store.state.server + '/participate', {
      params: {
        cid: Number(this.cid),
        uid: JSON.parse(sessionStorage.getItem("user")).id

      }
    })
    .then((res)=>{
      if(res.data){
        this.alreadyParicipate=true
      }else{
        this.alreadyParicipate=false
      }
    })

    axios.get(this.$store.state.server + '/certification/date', {
      params: {
        cid: Number(this.cid),
        uid: JSON.parse(sessionStorage.getItem("user")).id

      }
    })
    .then((res)=>{
      if(res.data){
        this.todaystate=true
      }else{
        this.todaystate=false
      }
    })


    axios.get(this.$store.state.server + '/challenge', {
      params: {
        id: Number(this.cid),

      }
    }) 
        .then((res) => {
          console.log(res)
          this.challengeAddress = res.data.address
          this.title = res.data.name
          this.startDate = res.data.startDate.substr(2, 8)
          this.endDate = res.data.endDate.substr(2, 8)
          this.expireDate = res.data.expireDate.substr(2, 8)
          this.expire = res.data.expireDate.substr(0, 10);
          this.fee = res.data.fee
          this.isRandom = res.data.isRandom
          this.users = res.data.users
          this.certificationCondition = res.data.certificationCondition
          this.startDate = this.startDate.replace(/-/g, '/')
          this.endDate = this.endDate.replace(/-/g, '/')
          this.expireDate = this.expireDate.replace(/-/g, '/')
          this.certificationStartTime=res.data.certificationStartTime
          this.certificationEndTime=res.data.certificationEndTime

          if(res.data.samplepicture!=null){
            this.samplepicture="data:;base64, "+res.data.samplepicture
          }

          let today = new Date(Date.now()-offset).toISOString().substr(0, 10)
          if (this.isRandom) {
            this.divide = '랜덤 차등 분배'
          } else {
            this.divide = '균등 분배'
          }
          let exp = new Date(this.expire)
          exp.setDate(exp.getDate()+1)
          exp=exp.toISOString().substr(0,10)

          if (exp > today) {
            this.challengeState = 'before'
            this.before()
          }else if(exp <= today && today<res.data.startDate){
            this.challengeState = 'notStart'
          } else if (res.data.startDate <= today && res.data.endDate >= today) {
            this.challengeState = 'doing'
            this.doing()
          } else {
            this.challengeState = 'done'
            this.done()
          }
          this.gather = this.fee * this.users.length;
          let len = String(this.gather).length;
          let comma = len % 3
          let tempstr = String(this.gather).substr(0, comma)
          while (comma < len) {
            tempstr += ','
            tempstr += String(this.gather).substr(comma, 3)
            comma += 3
          }
          this.gather = tempstr

          let from = new Date(res.data.startDate)
          let to = new Date(res.data.endDate)
          let differ = (to - from) / (24 * 60 * 60 * 1000)
          differ+=1
          this.total = differ
          if (differ >= 7) {
            if ((differ % 7) == 0) {
              differ = Math.floor((differ / 7)) + '주'
            } else {
              differ = Math.floor((differ / 7)) + '주 ' + Math.floor(differ % 7) + '일'
            }
          } else {
            differ = differ + '일'
          }
          this.checkdate = true;
          this.periods = differ




          if (this.challengeState == 'before') {
            setInterval(() => {
              this.remainTime()
            }, 1000); // 타이머 1초간격으로 수행
          }
          if (this.challengeState == 'doing') {
            setInterval(() => {
              this.checkCertificationTime()
            }, 1000); // 타이머 1초간격으로 수행
          }


          if(res.data.startDate <= today && res.data.endDate >= today){
            this.ongoing = true
          }
          if (this.challengeState != 'before'){
            this.isLoading=false
          }
        })
        .catch(() => {
          /*
          TODO : 에러 페이지로 이동
          */
          this.$router.push('/404')
        })



  },
  methods: {
    remainTime() {
      let now = new Date(Date.now()-offset)
      let exp = new Date(this.expire)
      exp.setDate(exp.getDate()+1)
      exp.setSeconds(exp.getSeconds()-1)
      if (now > exp) {
        return;
      } else {
        let _sec = 1000
        let _min = 60 * _sec
        let _hour = 60 * _min
        let _day = 24 * _hour

        let differ = exp - now
        let day = Math.floor(differ / _day)
        let hour = Math.floor((differ % _day) / _hour)
        let min = Math.floor((differ % _hour) / _min)
        let sec = Math.floor((differ % _min) / _sec)
        this.remain = ''
        if (day > 0) {
          this.remain += day + '일 '
        }
        if (hour > 0) {
          this.remain += hour + '시간 '
        }
        if (min > 0) {
          this.remain += min + '분 '
        }
        if (sec > 0) {
          this.remain += sec + '초 '
        }
        if (day == 0 && hour == 0 && min == 0 && sec == 0) {
          this.remain = '마감 되었습니다.'
        } else {
          this.remain += '남았습니다.'
        }

        this.isLoading=false
      }
    },
    checkCertificationTime(){
      let hour = new Date().getHours()
      let min = new Date().getMinutes()
      let timepickIndex=(hour*60+min)/30
      if(timepickIndex>=this.certificationStartTime&&timepickIndex<this.certificationEndTime){
        this.certificationAvailableTime=true
        return
      }
      else{
        this.certificationAvailableTime=false
      }
    },
    before(){
      axios.get(this.$store.state.server + '/wallet/' + JSON.parse(sessionStorage.getItem("user")).id)
          .then(res => {
            const address = res.data.address

            if (address != null && address != ' ' && address != '') {
              this.walletAddress = address
              this.getBalance()

            }
          })
          .catch(()=>{
            this.participateSnackbarText='지갑을 먼저 생성해주세요.'
            this.participateSnackbar=true
          })

    },
    /**
     * TODO : URL 수정
     */
    doing() {

      axios.get(this.$store.state.server +'/challenge/certification', {
        params: {
          "id": Number(this.cid)
        }
      })
          .then((res) => {

            for(let i=0;i<res.data.length;i++){
              if(res.data[i].certification[0]!=null){
                this.userlist.push({
                  id:res.data[i].id,
                  nickname:res.data[i].nickname,
                  progress:res.data[i].progress,
                  certification:{
                    id:res.data[i].certification[0].id,
                    picture:"data:;base64, "+res.data[i].certification[0].picture,
                    isReported : res.data[i].certification[0].reported,
                    regDate : res.data[i].certification[0].regDate

                  }
                })
              }
            }


          })
      this.isLoading=false
    },
    /**
     * TODO : URL 수정
     */
    done() {
      axios.get(this.$store.state.server +'/challenge/result', {
        params: {
          "id": Number(this.cid)
        }
      })
          .then((res) => {

            this.successlist = res.data.successlist
            this.faillist = res.data.faillist

          })
      this.isLoading=false
    },

    clickParticipant(participant, total) {
      this.$modal.show(ChallengeModal, {
        participant: participant,
        total: total,
        modal: this.$modal
      }, {
        name: 'dynamic-modal',
        width: '50%',
        height: '50%',
        draggable: false,
      })
    },

    certification:function(challengeid){
      this.$modal.show(PictureModal,{
        modal: this.$modal,
        challengeid:challengeid,

      },{
        name: 'dynamic-modal',
        width: '80%',
        height: '80%',
        draggable: false,
      })
    },

    insertCommaInNumber(num) {
      let len = String(num).length;
      let comma = len % 3
      let tempstr = String(num).substr(0, comma)
      while (comma < len) {
        tempstr += ','
        tempstr += String(num).substr(comma, 3)
        comma += 3
      }
      return tempstr
    },

    goMain(){
      this.$router.push('/challenges')
    },

    goMypage() {
      this.$router.push('/mypage')
    },

    logout() {
      let win = window.open('https://accounts.kakao.com/logout?continue=https://accounts.kakao.com/weblogin/account')
      win.close()
      sessionStorage.removeItem("user")
      this.$router.push("/")
    },

    async getBalance(){
      await web3.eth.getBalance(this.walletAddress).then((b)=>this.balance=Math.floor(b/1000000000000000000))
    },

    async participate(){
      if(this.password.length!=4){
        this.participateSnackbarText='비밀번호는 4글자여야합니다.'
        this.participateSnackbar=true
        return
      }
      if(this.balance<this.fee){
        this.participateSnackbarText='잔액이 부족합니다. 잔액을 확인해주세요.'
        this.participateSnackbar=true
        return
      }
      this.overlay=true
      this.overlayProgress=1
      let price = 1000000000000000000*this.fee
      await web3.eth.personal.unlockAccount(this.walletAddress, this.password, 600).then(() => {
        this.overlayProgress=2
        web3.eth.sendTransaction({
                from: this.walletAddress,
                gasPrice: "200000000",
                gas: "1000000",
                to: this.challengeAddress,
                value: String(price),
                data: ""
              }, this.password).then(() => {
                this.overlayProgress=3
                axios.post(this.$store.state.server + '/participate', {
                  cid: Number(this.cid),
                  uid: JSON.parse(sessionStorage.getItem("user")).id
                })
                    .then(()=>{
                      this.$router.go()
                    })
                    .catch(() => {
                      this.overlay=false
                    })
              }).catch((err)=>{
                console.log(err)
            this.participateSnackbarText='전송에 오류가 있습니다.'
            this.participateSnackbar=true
            this.overlay=false
          })

      })
          .catch((err)=> {
            console.log(err)
            this.participateSnackbarText='비밀번호를 틀렸습니다. 다시 확인해주세요.'
            this.participateSnackbar=true
            this.overlay=false
          })

    },





  }

}
</script>

<style scoped>

</style>