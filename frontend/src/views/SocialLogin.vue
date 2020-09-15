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
  mounted() {
    console.log(this.id)

    axios.get('http://localhost:8080/blockchallen/member/'+this.id)
        .then(res => {
          if(res.data.account.nickname != null) {
            sessionStorage.setItem("user", res.data.account)
            this.$router.push("/challenge")
          }
        })
  }
}
</script>

<style scoped>

</style>