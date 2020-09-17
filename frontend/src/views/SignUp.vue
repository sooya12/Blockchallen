<template>
  <div id="app">
    <div>
      <v-btn color="pink" dark top left style="margin: 2%;" @click="backHome">
        <v-icon dark left>arrow_back</v-icon>처음으로
      </v-btn>
    </div>
    <div id="terms">
      <p>서비스 이용 약관</p>
      <div>
        <v-textarea :value="userInfoAgree" outlined label="서비스 이용 약관" readonly></v-textarea>
      </div>
    </div>
    <div id="nicknameForm">
      <p>닉네임 입력 후, 중복확인을 해주세요</p>
      <div id="nickname">
        <v-text-field v-model="nickname" label="닉네임" :rules="nicknameRules" hide-details="auto"></v-text-field>
      </div>
      <div id="nicknameCheck">
        <v-btn small color="primary" @click="duplicationCheck">중복확인</v-btn>
      </div>
    </div>
    <div id="signUp">
      <v-btn large color="primary" @click="signUp" :disabled="!flag">가입하기</v-btn>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: "SignUp",
  data: () => ({
    userInfoAgree: '블록챌린은 이메일로 계정을 생성합니다. 다만, 실제 이메일의 소유주임을 확인하기 위해서 가입 당시 인증 절차를 거치게 됩니다. 아래의 경우에는 계정 생성을 승인하지 않을 수 있습니다.\n\n다른 사람의 개인정보를 이용하여 계정을 생성하려 한 경우\n계정 생성시 필요한 정보를 입력하지 않거나 허위 정보를 입력한 경우\n블록챌린이 과거에 운영원칙 또는 법률 위반 등의 정당한 사유로 해당 계정을 삭제 또는 징계한 경우\n\n계정은 본인만 이용할 수 있고, 다른 사람에게 이용을 허락하거나 양도할 수 없습니다. 이메일이 바뀐 경우에는 서비스 내 설정 메뉴나 고객센터 문의를 통해 새 이메일로 인증절차를 걸쳐 수정할 수 있습니다.',
    id: 0,
    nickname: "",
    nicknameRules: [
        value => !! value || '필수 사항. 한 글자 이상 입력해주세요',
        value => (value && value.length >= 1) || '한 글자 이상 입력해주세요'
    ],
    flag: false
  }),
  methods: {
    backHome() {
      this.$router.push("/")
    },
    duplicationCheck() {
      axios.get('http://localhost:8080/blockchallen/account/nickname/' + this.nickname)
      .then((res) => {
        console.log(res.data)
        this.flag = true
      })
      .catch((err) => {
        console.log(err)
      })
    },
    signUp() {
      const account = {
        id: this.id,
        nickname: this.nickname
      }

      axios.put('http://localhost:8080/blockchallen/account', account)
      .then((res) => {
        sessionStorage.removeItem("user")
        sessionStorage.setItem("user", JSON.stringify(res.data))

        this.$router.push("/challenges")
      })
      .catch((err) => {
        console.log(err)
        alert("닉네임 생성 실패")
        this.flag = false
      })
    }
  },
  mounted() {
    this.id = JSON.parse(sessionStorage.getItem("user")).id
    console.log(this.id)
  }
}
</script>

<style scoped>
#app {
  width: 100%;
  height: 1vh;
  margin: 0 auto;
}

#terms {
  width: 80%;
  height: 40vh;
  margin: 0 auto;
  margin-top: 5%;
}

#nicknameForm {
  width: 80%;
  height: 15vh;
  margin: 0 auto;
  margin-top: 5%;
}

#nickname {
  width: 70%;
  float: left;
}

#nickname .v-text-field {
  padding: 0;
  margin: 0;
}

#nicknameCheck {
  width: 15%;
  float: right;
  text-align: right;
}

p {
  font-size: large;
}

#signUp {
  width: 70%;
  margin: 0 auto;
  text-align: center;
}
</style>