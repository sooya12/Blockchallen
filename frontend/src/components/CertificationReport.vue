<template>
  <div>
    <div>
      <v-card style="width: 80%; margin-left: 2%; padding : 2%; margin-top:1%;">
        <p style="font-size: 2.5vh; font-weight: bold; margin: 2%; margin-left:0%;">신고하기</p>
        <p style="font-size: 1.5vh; font-weight: bold;">
          본 신고 기능은 우선 신고 처리 후, 담당자가 직접 검토합니다. <br>
          인증샷이 본 챌린지와 상관이 없거나 충분히 의심스러울 상황일 경우에만 신고해주세요. <br>
          신고하신 내용은 취소할 수 없으며 우선 해당 인증된 사진을 미인증 처리 후 담당자가 다시 검토합니다. <br>
          악의적인 목적으로 신고 시 제재 받을 수 있음을 알려드리며 아래 내용을 입력하면 신고 버튼이 활성화 됩니다.
        </p>
      </v-card>


      <v-card style="width: 80%; margin: 2%; padding: 1%; margin-top:3%;  padding-bottom: 0;">
        <p style="font-size: 1.5vh; font-weight: bold;">
          아래 내용을 입력창에 입력하시면 신고 버튼이 활성화 됩니다.
          <br>
          <br>
        </p>
        <p style="color:red; font-size: 1.8vh; font-weight: bold; ">
          {{ textOriginal }}
        </p>
        <v-text-field
            ref="title"
            v-model="textSignature"
            :placeholder="description"

            outlined
            style="width: 90%; margin-top:5%;"
            onpaste="return false;"
            oncopy="return false;"
        ></v-text-field>
      </v-card>
      <div style="position: absolute; left:0; bottom :5%; width: 100%;">
        <div style="position: relative; margin: 0 auto; text-align: center; float:left;  width: 100%;">
          <p> {{checkmessage}} </p>
          <v-btn 
            @click="checkPicture()"
            style="margin-right: 2%;">
            
             <p style="color:white; font-size:2vh;  font-weight: bold;">위변조 검사하기</p>
          </v-btn>

          <v-btn
              color="#ff5555"

              @click="clickReport(participant)"
              :disabled="!activateReport"
              style="margin-right: 2%; "
          >
            <p style="color:white; font-size:2vh;  font-weight: bold;">신고하기</p>
            <v-icon dark right style="color:white;">mdi-cancel</v-icon>
          </v-btn>

          <v-btn
              color="#ff5555"

              @click="cancel"
              style=""
          >
            <p style="color:white; font-size:2vh;  font-weight: bold;">취소</p>

          </v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import ChallengeModal from "@/components/ChallengeModal";

export default {
  name: "CertificationReport",
  props: {
    participant: Object,
    total: Number,
  },
  data() {
    return {
      textSignature: '',
      description: '위 내용을 그대로 입력해주세요. (붙여넣기 사용 불가)',
      textOriginal: '위 내용을 숙지하였으며 동의합니다.',
      activateReport: false,
      checkmessage:'',

    }
  },
  mounted() {

  },
  methods: {

    checkPicture(){
      axios.get(this.$store.state.server + '/certification/block/' + this.participant.certification.id)
        .then((res)=>{
          if(res.data){
            alert("사진 위변조 true")
            //  checkmessage = "사진이 위변조되지 않았습니다. 그래도 신고하시겠습니까?"
          }else{
            alert("사진 위변조 false")
            //  checkmessage = "사진이 위변조되었습니다."
          }

      })
    },

    clickReport(participant) {
      axios.get(this.$store.state.server +'/certification/report', {
        params : {
          'pid': Number(participant.certification.id),
          'uid': Number(participant.id)
        }
      })
          .then(() => {
            this.$emit('close')
            window.location.reload()
          })

    },


    cancel() {
      this.$emit('close')
      this.$modal.show(ChallengeModal, {
        participant: this.participant,
        total: Number(this.total),
        modal: this.$modal
      }, {
        name: 'dynamic-modal',
        width: '50%',
        height: '40%',
        draggable: false,
      })
    }

  },
  watch: {
    textSignature(val) {
      if (val == this.textOriginal) {
        this.activateReport = true
      } else {
        this.activateReport = false
      }
    },
  }

}
</script>

<style scoped>

</style>