<template>
  <div>
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

    <div style="margin-left: 20%; margin-top: 3%;">
      <v-card style="width:70%;">
          <v-img
              src="/lego.ico"
              height="4vh"
              width="4vh"
              style="float:left; margin:2%;"
          ></v-img>
          <p style="font-size:4vh; font-weight: bold; margin-left:3%; padding-top:1%;float:left; ">{{ title }}</p>
          <v-btn style="float:right; margin:2%; " @click="certification(cid)">
              인증하기
          </v-btn>
          <br style="clear:both;"/>
        <div>
          <span style="font-size:2.5vh; font-weight: bold; margin-left: 2%; margin-right: 3%;">{{ startDate }}</span> ~
          <span style="font-size:2.5vh; font-weight: bold; margin-left: 3%;">{{ endDate }} </span> <span
            style="color:#ff5555; margin-left: 1%;">({{ periods }})</span>
        </div>
      </v-card>
      <v-card style="width:70%; padding: 1% 2%; margin-top: 3%;">
        <p style="font-size:2.5vh; margin-top: 2%;">배팅 금액 : <span style="font-size:3vh; font-weight: bold"> {{
            fee
          }}원</span></p>
        <p style="font-size:2.5vh; margin-top: 2%;">분배 방식 : <span style="font-size:3vh; font-weight: bold"> {{
            divide
          }}</span></p>
        <p style="font-size:2.5vh; margin-top: 2%;">인증 조건 : <span
            style="font-size:2.5vh; font-weight: bold"> {{ certificationCondition }}</span></p>
      </v-card>
      <v-card style="width:70%; padding: 1% 2%; margin-top: 3%;">
        <p style="font-size:2.5vh; margin-top: 2%;  font-weight: bold;">현재 참여 인원 : {{ users.length }}명</p>
        <p style="font-size:2.5vh; margin-top: 2%;  font-weight: bold; " v-if="challengeState=='before'">현재 까지
          {{ gather }}원이 모였습니다.</p>
        <p style="font-size:2.5vh; margin-top: 2%;  font-weight: bold; " v-if="challengeState!='before'">도전 금액 :
          {{ gather }}원</p>

      </v-card>
      <v-card style="width:70%; padding: 1% 2%; margin-top: 3%;">
        <div v-if="challengeState=='before'">
          <p style="font-size:2.5vh; margin-top: 2%; color:#ff5555; font-weight: bold;">마감까지 {{ remain }}</p>

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
        <v-btn color="error" dark large style="margin: 2% 0; width:50%; height: 8vh; font-size:3vh; font-weight: bold;">
          참여하기
        </v-btn>

      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import BlockProgress from "@/components/BlockProgress";
import ChallengeModal from "@/components/ChallengeModal";
import PictureModal from "@/components/PictureModal";

export default {
  name: "challengeDetail",
  components: {
    BlockProgress
  },
  props: {
    cid: {
      type: Number
    }
  },
  data() {
    return {
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

    }
  },
  mounted() {

    /*
    TODO : 추후 URL 수정 필요
    */
    axios.get(this.$store.state.server + '/challenge', {
      params: {
        id: Number(this.cid),

      }
    })
        .then((res) => {
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
          let today = new Date().toISOString().substr(0, 10)
          if (this.isRandom) {
            this.divide = '랜덤 차등 분배'
          } else {
            this.divide = '균등 분배'
          }
          if (res.data.expireDate >= today) {
            this.challengeState = 'before'
          } else if (res.data.expireDate < today && res.data.endDate > today) {
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
        })
        .catch(() => {
          /*
          TODO : 에러 페이지로 이동
          */
          this.$router.push()
        })


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

  },
  methods: {
    remainTime() {
      let now = new Date()
      let exp = new Date(this.expire)
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
    /**
     * TODO : URL 수정
     */
    doing() {

      axios.get('/mock/userfeed' + this.cid + '.json', {
        params: {
          "id": Number(this.cid)
        }
      })
          .then((res) => {
            this.userlist = res.data.list

          })

    },
    /**
     * TODO : URL 수정
     */
    done() {
      axios.get('/mock/result' + this.cid + '.json', {
        params: {
          "id": Number(this.cid)
        }
      })
          .then((res) => {

            this.successlist = res.data.successlist
            this.faillist = res.data.faillist

          })
    },

    clickParticipant(participant, total) {
      this.$modal.show(ChallengeModal, {
        participant: participant,
        total: total,
        modal: this.$modal
      }, {
        name: 'dynamic-modal',
        width: '50%',
        height: '40%',
        draggable: false,
      })
    },

    certification:function(challengeid){
      this.$modal.show(PictureModal,{
        modal: this.$modal,
        challengeid:challengeid,

      },{
        name: 'dynamic-modal',
        width: '100%',
        height: '40%',
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



  }

}
</script>

<style scoped>

</style>