<template>
  <div >

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
        <i ><img src="/lego.png" style="height:8vh;"></i><p style="font-size:2vh;  font-weight: bold;">{{participant.nickname}}</p>
      </div>

      <i>upload at</i>
      <p>{{participant.certification.regDate}}</p>
      <p style="font-size:2vh;  font-weight: bold;">진행도 : <span style="color:#ff5555;">{{participant.progress}} / {{total}}</span></p>
      <block-progress :progress="participant.progress" :total="total" ></block-progress>

      <v-tooltip top>
        <template v-slot:activator="{ on, attrs }">
          <v-btn
              color="#ff5555"
              style="float:right; margin-top:4%; margin-right: 4%;"
              v-bind="attrs"
              v-on="on"
          >
            <p style="color:white; font-size:2vh;  font-weight: bold;">신고하기</p>
            <v-icon dark right style="color:white;">mdi-cancel</v-icon>
          </v-btn>
        </template>
        <p style="font-size: 1.8vh; font-weight: bold;">신고하기는 이럴때!</p>
        <p style="font-size: 1.6vh; margin-top: 0.5vh; margin-bottom: 0;">인증샷이 본 챌린지와 상관이 없다면 신고 버튼을 눌러주세요.</p>
        <p style="font-size: 1.6vh; margin-top: 0.2vh; margin-bottom: 0;">인증샷이 의심스럽다면 신고 버튼을 눌러주세요.</p>
      </v-tooltip>

      <div style="position: absolute; left: 0; bottom: 0; width: 100%;">

      </div>


    </div>
    <br style="clear:both;"/>


  </div>
</template>

<script>
import BlockProgress from "@/components/BlockProgress";


export default {
  name: "ChallengeModal",
  components : {
    BlockProgress,

  },
  props:{
    participant : Object,
    total : Number
  },
  data(){
    return {

    }
  },
  mounted() {



  }
}
</script>

<style scoped>

</style>