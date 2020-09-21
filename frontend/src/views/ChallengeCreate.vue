<template>
  <div>
    <v-btn
        color="pink"
        dark
        top
        left
        style="margin : 2%;"
    >
      <v-icon dark left>arrow_back</v-icon>
      메인으로
    </v-btn>
    <div style="text-align: center; ">
      <h1>챌린지 생성하기</h1>
      <div style="width:50%; margin: 2% auto;">

        <v-text-field
            v-model="title"
            :rules="rules"
            counter="25"
            label="챌린지명"
            placeholder="챌린지명을 입력해주세요."
            outlined

        ></v-text-field>

        <div>
          <p style="text-align: left;">기간 &nbsp;&nbsp; <span
              style="color:#ff5555; margin-left: 3%;"> &nbsp;&nbsp;{{ periods }} </span></p>
          <div style="width : 20%; float: left;">
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
          <div style="width : 20%; margin-left:10%; float :left;">
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
          <p>베팅금액</p>
          <v-flex xs12 sm6 d-flex>
            <v-select
                :items="bets"
                label="베팅 금액"
                outlined
                v-model="bet"
            ></v-select>
          </v-flex>
        </div>
        <div>
          <p style="text-align: left;">분배방식</p>
          <v-radio-group v-model="isRandom" :mandatory="true" row>
            <v-radio label="랜덤 차등 분배" value="true"></v-radio>
            <v-radio label="균등 분배" value="false"  style="margin-left: 5%;"></v-radio>
          </v-radio-group>

        </div>
        <div style="margin-top: 2%;">
          <p style="text-align: left;">사진 인증 가능 시간</p>
          <v-switch
              v-model="certificationAvailableTime"
              class="mt-0"
              color="green lighten-2"
              hide-details
              label="항상 가능"
              style="margin-bottom: 2%;"
          ></v-switch>
          <v-spacer></v-spacer>
          <div v-if="certificationAvailableTime">
            <p style="text-align: left; margin-top:2%; font-weight: bold;">시간 선택</p>
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
          <p style="text-align: left;">사진 인증 조건 (선택)</p>
          <v-text-field
              v-model="certificateCondition"
              :rules="rules"
              counter="25"
              label="사진 인증 조건"
              placeholder="인증 조건을 입력해주세요."
              outlined

          ></v-text-field>
        </div>
        <div>
          <p style="text-align: left;">모집 마감 기간</p>
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
          <p style="color:#ff5555; text-align: left;">모집 마감 기간까지 최소 00명이 모이지 않을 시 챌린지가 자동으로 <span
              style="font-weight: bold;">삭제</span>됩니다.</p>
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


      <v-btn class="ma-2" color="primary" :disabled="!(checktitle&&(bet>0)&&checkdate)"
             @click="register"
      >
        챌린지 만들기
        <v-icon dark right>mdi-checkbox-marked-circle</v-icon>
      </v-btn>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: "challengeCreate",
  data() {
    return {
      title: '',
      description: '챌린지명을 입력해주세요',
      rules: [v => v.length <= 25 || 'Max 25 characters'],
      periods: '',
      startdate: new Date().toISOString().substr(0, 10),
      startmenu: false,
      enddate: new Date().toISOString().substr(0, 10),
      endmenu: false,
      bets: [1000, 2000, 3000, 5000, 10000, 20000],
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


    }
  },
  mounted() {
    console.log(new Date().getHours(),',',new Date().getMinutes())
  },
  methods: {
    register: function () {
      axios.post('http://localhost:8080/blockchallen/challenge', {
        name: this.title,
        startDate: this.startdate,
        endDate: this.enddate,
        expireDate: this.expiredate,
        fee: this.bet,
        isRandom: this.isRandom,
        certification: this.certificateCondition,
        uid: 61,
        certificationStartTime:this.certificationStartTime,
        certificationEndTime : this.certificationEndTime,
      })
          .then(() => {
            this.$router.push('/') //상세페이지로 이동하자
          })
          .catch((err)=>{
            console.log(err)
          })
    }
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

      let curDate = new Date().toISOString().substr(0, 10)

      if (curDate >= newVal) {
        this.snackbarmsg = '시작 일은 현재 날짜 보다 최소 1일 이후 여야 합니다.'
        this.snackbar = true;
        this.startdate = curDate
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
        this.snackbar = true;
        let tempDate = new Date(this.startdate)
        tempDate.setDate(tempDate.getDate() - 1)
        this.expiredate = tempDate.toISOString().substr(0, 10)
      }

    },

    certificationTime :function (newVal){
      this.certificationStartTime=newVal[0]
      this.certificationEndTime=newVal[1]

    },

  }
}
</script>

<style scoped>

</style>