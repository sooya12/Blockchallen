<template>
  <div id="app">

  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "SocialLogin",
  props: {
    id: {
      type: Number
    }
  },
  data: () => ({
    user: {
      id: 0,
      challenges: [],
      email: "",
      nickname: null,
      access_token: "",
    }
  }),
  mounted() {
    axios.get('http://localhost:8080/blockchallen/account/'+this.id)
    .then(res => {
      console.log(res)
      if(res.data.nickname != null && res.data.nickname != '') {
        this.user.id = this.id
        this.user.challenges = res.data.challenges
        this.user.email = res.data.email
        this.user.nickname = res.data.nickname
        this.user.access_token = res.data.access_token

        sessionStorage.setItem("user", JSON.stringify(this.user))

        this.$router.push("/challenges")
      } else {
        this.user.id = this.id
        this.user.challenges = res.data.challenges
        this.user.email = res.data.email
        this.user.access_token = res.data.access_token

        sessionStorage.setItem("user", JSON.stringify(this.user))
        this.$router.push("/signup")
      }
    })
    .catch(err => {
      console.log(err)
    })
  }
}
</script>

<style scoped>

</style>