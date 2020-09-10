<template>
  <div id="app">
    <v-content>
      <div id="animationWindow">
      </div>
      <div id="title">
        <p style="font-size:4vw;">
          <b>BlockChallen</b>
        </p>
      </div>
      <div id="kakaoLogin">
        <img :src="require('@/assets/kakao_login_large_narrow.png')" @click="함수이름">
      </div>
    </v-content>
  </div>
</template>

<script>
import lottie from 'lottie-web'

export default {
  name: 'Home',
  mounted() {
    this.anim = lottie.loadAnimation({
      wrapper: document.querySelector('#animationWindow'),
      animType: 'svg',
      loop: true,
      prerender: true,
      autoplay: true,
      path: 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/35984/LEGO_loader.json'
    })
    this.$emit('animCreated', this.anim)
  }

}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Kalam:wght@700&display=swap');

#app {
  width: 70%;
  height: auto;
  margin: 0 auto;
  font-family: 'Kalam', cursive;
}

#animationWindow {
  width: auto;
  height: 70%;
}

#title {
  position: relative;
  top: -13%;
  text-align: center;
}

#kakaoLogin {
  width: 100%;
  margin: 0 auto;
  text-align: center;
}

#kakaoLogin img {
  width: 30%;
  height: auto;
}

</style>