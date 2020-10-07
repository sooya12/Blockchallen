<template>
  <div id="app">
    <v-content>
      <div id="animationWindow">
      </div>
      <div id="title">
        <p>
          <b>BlockChallen</b>
        </p>
        <p style="font-size: 2vw">돈은 새로운 나를 만든다</p>
      </div>
      <div id="kakaoLogin">
        <img :src="require('@/assets/kakao_login_large_narrow.png')" @click=kakaoLogin>
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
  },
  methods: {
    kakaoLogin() {
      window.location.href = 'https://kauth.kakao.com/oauth/authorize?client_id=28c57e4dec8be27db1832926dba21bb0&redirect_uri=' + this.$store.state.server + '/login&response_type=code'
    }
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Kalam:wght@700&display=swap');

#app {
  width: 100%;
  margin: 0 auto;
  font-family: 'Kalam', cursive;
}

#animationWindow {
  width: 100%;
  height: 60vh;
  margin: 0 auto;
}

#title {
  position: relative;
  top: -15vh;
  text-align: center;
}

#title p {
  font-size: 6vw;
}

#kakaoLogin {
  width: 100%;
  margin: 0 auto;
  text-align: center;
}

#kakaoLogin img {
  width: auto;
  height: 6vh;

}

</style>