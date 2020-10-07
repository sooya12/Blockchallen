<template>
  <v-app>
    <v-main>
      <router-view :key="$route.fullPath"/>
    </v-main>
    <v-footer
        style="margin-top: 10%;"
    >
      <main-footer></main-footer>

    </v-footer>
  </v-app>
</template>

<script>
import Footer from '@/components/Footer'

export default {
  name: 'App',
  components: {
    'main-footer': Footer,
  },
  data: () => ({}),
  mounted() {
    const user = sessionStorage.getItem("user");

    if (user == null) {
      this.$router.push("/");
    }
  }
};
</script>

<style>
@font-face {
  font-family: 'yg-jalnan';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_four@1.2/JalnanOTF00.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}

* {
  font-family: 'yg-jalnan';
}
</style>
