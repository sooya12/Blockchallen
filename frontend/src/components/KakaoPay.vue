<template>

    <iframe :src="url" style="width: 100%; "></iframe>

</template>

<script>
export default {
  name: "KakaoPay.vue",
  props:{
    url : String,

  },
  mounted() {

  },
  methods:{

  }
}
</script>

<style scoped>
  html,body{
    height: 100%;
    overflow: hidden;
  }
  iframe{
    height: 100%;
  }

</style>