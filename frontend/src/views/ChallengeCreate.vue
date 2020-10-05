<template>
  <div style="width: 100%; margin: 0 auto; max-width: 1000px;">
    <v-btn
        color="pink"
        dark
        top
        left
        style="margin : 2%;"
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
    <div style="text-align: center; ">
      <h1><span style="color: #f39c14;">챌린지</span> 생성하기</h1>
      <div style="width:80%; margin: 2% auto; max-width: 1000px;">

        <v-text-field
            v-model="title"
            :rules="rules"
            counter="25"
            label="챌린지명"
            placeholder="챌린지명을 입력해주세요."
            outlined

        ></v-text-field>

        <div>
          <p style="text-align: left; color: #f39c14;">기간 <span
              style="color:#ff5555; margin-left: 3%;"> &nbsp;&nbsp;{{ periods }} </span></p>
          <div style="width : 45%; float: left;">
            <v-menu
                ref="startmenu"
                v-model="startmenu"
                :close-on-content-click="false"
                :nudge-right="40"
                :return-value.sync="startdate"
                lazy
                transition="scale-transition"
                offset-y
                full-width
                min-width="290px"
            >
              <template v-slot:activator="{ on }">
                <v-text-field
                    v-model="startdate"
                    label="챌린지 시작일"
                    prepend-icon="event"
                    readonly
                    v-on="on"
                ></v-text-field>
              </template>
              <v-date-picker v-model="startdate" no-title scrollable>
                <v-spacer></v-spacer>
                <v-btn color="primary" @click="menu = false">Cancel</v-btn>
                <v-btn color="primary" @click="$refs.startmenu.save(startdate)">OK</v-btn>
              </v-date-picker>
            </v-menu>
          </div>
          <div style="width : 45%; margin-left:5%; float :left;">
            <v-menu
                ref="endmenu"
                v-model="endmenu"
                :close-on-content-click="false"
                :nudge-right="40"
                :return-value.sync="enddate"
                lazy
                transition="scale-transition"
                offset-y
                full-width
                min-width="290px"
            >
              <template v-slot:activator="{ on }">
                <v-text-field
                    v-model="enddate"
                    label="챌린지 종료일"
                    prepend-icon="event"
                    readonly
                    v-on="on"
                ></v-text-field>
              </template>
              <v-date-picker v-model="enddate" no-title scrollable>
                <v-spacer></v-spacer>
                <v-btn color="primary" @click="menu = false">Cancel</v-btn>
                <v-btn color="primary" @click="$refs.endmenu.save(enddate)">OK</v-btn>
              </v-date-picker>
            </v-menu>
          </div>
        </div>

        <div style="width: 50%; text-align: left; margin-top: 8%;">
          <p style="color: #f39c14;">베팅금액 &nbsp;&nbsp; <span style="margin-left : 10%; color:#777;">내 잔고 :  <span style=" margin-left:2%;color: #444   ;">{{balance}} 이더</span></span></p>

          <v-flex xs12 sm6 d-flex>
            <v-select
                :items="bets"
                label="베팅 금액"
                outlined
                v-model="bet"
            ></v-select>
          </v-flex>
          <p v-if="balanceAlert"  style="color:#ff5555; margin-top: 0%; margin-bottom: 5%;">{{  balanceAlert  }}</p>
        </div>
        <div>
          <p style="text-align: left; color: #f39c14;">분배방식</p>
          <v-radio-group v-model="isRandom" :mandatory="true" row>
            <v-radio label="랜덤 차등 분배" value="true"></v-radio>
            <v-radio label="균등 분배" value="false"  style="margin-left: 5%;"></v-radio>
          </v-radio-group>

        </div>
        <div style="margin-top: 2%;">
          <p style="text-align: left; color: #f39c14;">사진 인증 가능 시간</p>
          <v-switch
              v-model="certificationAvailableTime"
              class="mt-0"
              color="green lighten-2"
              hide-details
              label="24시간 내내 가능"
              style="margin-bottom: 2%;"
          ></v-switch>
          <v-spacer></v-spacer>
          <div v-if="!certificationAvailableTime">
            <p style="text-align: left; margin-top:2%; font-weight: bold; color: #f39c14;">시간 선택</p>
            <v-range-slider
                v-model="certificationTime"
                :value="[certificationStartTime, certificationEndTime]"
                min="0"
                max="48"
            >
              <template v-slot:thumb-label="props">
                {{timepick[props.value]}}
              </template>
            </v-range-slider>
            <p style="text-align: left; margin-top:2%; font-weight: bold;">{{ Math.floor((certificationStartTime*30)/60) }}:{{ (certificationStartTime*30)%60==0?"00":"30" }} ~ {{ Math.floor((certificationEndTime*30)/60) }}:{{ (certificationEndTime*30)%60==0?"00":"30" }}</p>
          </div>
          <p style="text-align: left; margin-top: 5%; color: #f39c14;">사진 인증 조건 (선택)</p>
          <v-text-field
              v-model="certificateCondition"
              :rules="rules"
              counter="25"
              label="사진 인증 조건"
              placeholder="인증 조건을 입력해주세요."
              outlined

          ></v-text-field>


          <p style="text-align: left; margin-top: 5%; color: #f39c14;">사진 인증 예시</p>
          <div >
            <v-img :src="imageUrl" v-if="imageUrl" style="width:50%; margin-right: 10%; margin-bottom: 5%;"></v-img>
            <v-file-input
                v-model="picture"
                accept="image/png, image/jpeg, image/bmp, image/gif"
                prepend-icon="mdi-camera"
                label="인증 사진 예시를 올려주세요."
                :rules="picturelimit"

            >

            </v-file-input>
            <p style="margin-left:5%; margin-bottom:5%; text-align: left; color:#aaa; font-size: small;">사진 용량은 16MB이하만 가능합니다.</p>
          </div>
        </div>
        <div>
          <p style="text-align: left; color: #f39c14;">모집 마감 기간</p>
          <v-menu
              ref="expire"
              v-model="expiremenu"
              :close-on-content-click="false"
              :nudge-right="40"
              :return-value.sync="expiredate"
              lazy
              transition="scale-transition"
              offset-y
              full-width
              min-width="290px"
          >
            <template v-slot:activator="{ on }">
              <v-text-field
                  v-model="expiredate"
                  label="모집 마감일"
                  prepend-icon="event"
                  readonly
                  v-on="on"
              ></v-text-field>
            </template>
            <v-date-picker v-model="expiredate" no-title scrollable>
              <v-spacer></v-spacer>
              <v-btn color="primary" @click="menu = false">Cancel</v-btn>
              <v-btn color="primary" @click="$refs.expire.save(expiredate)">OK</v-btn>
            </v-date-picker>
          </v-menu>
          <p style="color:#ff5555; text-align: left; margin-top: 2%;">모집 마감 기간까지 최소 3명이 모이지 않을 시 챌린지가 자동으로 <span
              style="font-weight: bold;">삭제</span>됩니다.</p>
        </div>

        <div style="margin-top: 5%;">
          <p style="text-align: left; color: #f39c14;">지갑 비밀번호</p>
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
        </div>
      </div>
      <v-snackbar
          v-model="snackbar"
          :color="snackbarcolor"
          :timeout="snackbartimeout"
          :vertical="true"

      >

        {{ snackbarmsg }}

        <v-btn
            dark
            :color="snackbarcolor"
            @click="snackbar = false"

        >
          Close
        </v-btn>
      </v-snackbar>



      <v-btn class="ma-2" color="primary" :disabled="!(checktitle&&(bet>0)&&checkdate&&!balanceAlert&&imageUrl&&(password.length==4))"
             @click="register"
      >
        챌린지 만들기
        <v-icon dark right>mdi-checkbox-marked-circle</v-icon>
      </v-btn>


    </div>
    <v-overlay :value="overlay" style="text-align: center;">
      <p v-if="overlayProgress==1"> 내 계좌 전송 준비중...</p>
      <p v-if="overlayProgress==2"> 챌린지 계좌 생성중...</p>
      <p v-if="overlayProgress==3"> 내 계좌에서 챌린지 계좌로 송금중...</p>
      <p v-if="overlayProgress==4"> 서버로 결과 전송중...</p>
      <v-progress-circular
          indeterminate
          size="64"
      ></v-progress-circular>
    </v-overlay>
  </div>
</template>

<script>
import axios from 'axios'

const Web3 = require('web3')
const web3 = new Web3(new Web3.providers.HttpProvider('https://j3a102.p.ssafy.io/geth'))
let offset = new Date().getTimezoneOffset() * 60000;
export default {
  name: "challengeCreate",

  data() {
    return {
      user : {},
      title: '',
      description: '챌린지명을 입력해주세요',
      rules: [v => v.length <= 25 || 'Max 25 characters'],
      periods: '',
      startdate: new Date(Date.now()-offset).toISOString().substr(0, 10),
      startmenu: false,
      enddate: new Date(Date.now()-offset).toISOString().substr(0, 10),
      endmenu: false,
      bets: [1, 2, 3, 5, 10, 20],
      bet: 0,
      isRandom: true,
      certificateCondition: '',
      expiredate: new Date().toISOString().substr(0, 10),
      expiremenu: false,
      certificationAvailableTime:true,
      certificationStartTime : 0,
      certificationEndTime : 48,
      certificationTime:[0,48],
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
      snackbar: false,
      snackbarcolor: 'red',
      snackbarmode: 'vertical',
      snackbartimeout: 3000,
      snackbarmsg: '정보 없음',
      checktitle: false,
      checkdate: false,
      picture:[],
      imageUrl:'',
      picturelimit:[
          value => !value || value.size < 16000000 || '이미지가 선택되지 않았거나 이미지 크기는 16MB 이하여야 합니다.!',
      ],
      walletAddress : '',
      balance : 0,
      balanceAlert: '',
      passwordShow: false,
      password: '',
      passwordRules: {
        required: password => !!password || '입력해주세요.',
        min: v => v.length == 4 || '비밀번호는 4글자 입니다.',

      },
      overlay: false,
      overlayProgress : 0,
    }
  },
  mounted() {

    this.startdate =new Date(Date.now()-(offset-24*60*60*1000)).toISOString().substr(0, 10)
    this.user=JSON.parse(sessionStorage.getItem('user'))
    axios.get(this.$store.state.server + '/wallet/' + this.user.id)
            .then(res => {
              const address = res.data.address

              if (address != null && address != ' ' && address != '') {
                this.walletAddress = address
                this.getBalance()
              }
            })





  },
  methods: {

    async register() {
      this.overlay=true
      let formData = new FormData
      if(this.certificationAvailableTime){
        this.certificationStartTime=0
        this.certificationEndTime=48
      }
      formData.append("name",this.title)
      formData.append("startDate",this.startdate)
      formData.append("endDate",this.enddate)
      formData.append("expireDate",this.expiredate)
      formData.append("fee",this.bet)
      formData.append("isRandom",this.isRandom)
      formData.append("certification",this.certificateCondition)
      formData.append("uid",JSON.parse(sessionStorage.getItem("user")).id)
      formData.append("certificationStartTime",this.certificationStartTime)
      formData.append("certificationEndTime",this.certificationEndTime)
      formData.append("samplepicture",this.picture)
      let price = 1000000000000000000*this.bet
      this.overlayProgress=1
      await web3.eth.personal.unlockAccount(this.walletAddress, this.password, 600).then(() => {
        this.overlayProgress=2
      web3.eth.personal.newAccount("ssafy")
      .then(res => {
        formData.append("address", res)
        this.overlayProgress=3
          web3.eth.sendTransaction({
            from: this.walletAddress,
            gasPrice: "200000000",
            gas: "1000000",
            to: res,
            value: String(price),
            data: ""
          }, this.password).then(() => {
            this.overlayProgress=4
            axios.post(this.$store.state.server + '/challenge', formData, {
              headers: {
                'Content-Type': 'multipart/form-data'
              }
            })
                .then(() => {
                  this.overlay=false
                  this.$router.push('/challenges') //상세페이지로 이동하자
                })
                .catch(() => {
                  this.overlay=false
                })
          })
        .catch(()=>{
          this.overlay=false
        })

        })

      })
          .catch(()=> {
            this.snackbarmsg = '비밀번호를 틀렸습니다. 다시 확인해주세요.'
            this.snackbartimeout=10000
            this.snackbar = true;
            this.overlay=false
          })




    },
    async getBalance(){
      await web3.eth.getBalance(this.walletAddress).then((b)=>this.balance=Math.floor(b/1000000000000000000))
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

    piccer(){
      this.picture = this.$refs.picture
      this.checkflag=false
      this.imageUrl = URL.createObjectURL(this.picture);

    },
  },
  watch: {
    title: function (newVal) {
      if (newVal != null && newVal.length > 0) {
        this.checktitle = true;
      } else {
        this.checktitle = false;
      }
    },
    startdate: function (newVal) {

      let curDate = new Date(Date.now()-offset).toISOString().substr(0, 10)
      if (curDate >= newVal) {
        this.snackbarmsg = '시작 일은 현재 날짜 보다 최소 1일 이후 여야 합니다.'
        this.snackbartimeout=3000
        this.snackbar = true;
        this.startdate = new Date(Date.now()-(offset-24*60*60*1000)).toISOString().substr(0,10)
        this.checkdate = false;
        return;
      } else {
        if (this.enddate < newVal) {
          let tempDate = new Date(newVal)
          tempDate.setDate(tempDate.getDate() + 1)
          this.enddate = tempDate.toISOString().substr(0, 10)
        }

        let newDate = new Date(newVal)
        newDate.setDate(newDate.getDate() - 1)
        this.expiredate = newDate.toISOString().substr(0, 10)
      }
    },
    enddate: function (newVal) {
      if (this.startdate >= newVal) {

        this.snackbarmsg = '종료 날짜는 시작 날짜 이후여야 합니다.'
        this.snackbartimeout=3000
        this.snackbar = true;
        let tempDate = new Date(this.startdate)
        tempDate.setDate(tempDate.getDate() + 1)
        this.enddate = tempDate.toISOString().substr(0, 10)
        this.checkdate = false;
        return;
      } else {
        let from = new Date(this.startdate)
        let to = new Date(this.enddate)
        let differ = (to - from) / (24 * 60 * 60 * 1000)
        differ+=1
        if (differ >= 7) {
          if ((differ % 7) == 0) {
            differ = Math.floor((differ / 7)) + '주'
          } else {
            differ = Math.floor((differ / 7)) + '주 ' + (differ % 7) + '일'
          }
        } else {
          differ = differ + '일'
        }
        this.checkdate = true;
        this.periods = differ
      }
    },
    expiredate: function (newVal) {
      if (newVal >= this.startdate) {
        this.snackbarmsg = '마감일은 시작일보다 최소 하루 전이여야 합니다.'
        this.snackbartimeout=3000
        this.snackbar = true;
        let tempDate = new Date(this.startdate)
        tempDate.setDate(tempDate.getDate() - 1)
        this.expiredate = tempDate.toISOString().substr(0, 10)
      }
      else if(newVal<new Date(Date.now()-(offset)).toISOString().substr(0,10)){
        this.snackbarmsg = '마감일은 최소 오늘 이후여야 합니다.'
        this.snackbartimeout=3000
        this.snackbar = true;
        this.expiredate=new Date(Date.now()-offset).toISOString().substr(0,10)
      }
    },

    certificationTime :function (newVal){
      this.certificationStartTime=newVal[0]
      this.certificationEndTime=newVal[1]

    },

    picture:function (newVal){
      if(newVal==null){
        this.imageUrl=null
        return;
      }
      this.imageUrl = URL.createObjectURL(newVal);

    },
    bet : function (newVal){
      this.balanceAlert=''
      if(newVal>this.balance){
        this.balanceAlert='현재 가진 잔액이 부족합니다.'

      }
    }



  }
}
</script>

<style scoped>

</style>