<template>
  <div>

  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "kakaoPaySuccess.vue",
  mounted() {
    let link = document.location.href;
    link=link.substr(link.indexOf("pg_token=")+9)
    axios.get(this.$store.state.server+"/kakaopay/kakaoPaySuccess",{
      params :{
        "pg_token" : link
      }


    }).then((res)=>{

      sessionStorage.setItem('chargeEther',res.data.quantity)
      parent.document.location.reload()


    })





  }
}
</script>

<style scoped>

</style>