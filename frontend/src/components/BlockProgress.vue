<template>
  <div style="display : inline;">
    <div v-for="index in 10" :key="index">

      <img src="/block/block10.jpg" style="width:8%; float: left;" v-if="index<progressInteger+1">
      <img :src=blockimg style="width:8%; float: left;" v-if="index==progressInteger+1">
      <img src="/block/block0.jpg" style="width:8%; float: left;" v-if="index>progressInteger+1">
    </div>
    <br style="clear:both;"/>
  </div>
</template>

<script>
export default {
  name: "BlockProgress.vue",
  props: {
    progress: Number,
    total: Number
  },
  data() {
    return {
      progressInteger: 0,
      progressDecimal: 0,
      blockimg: '',
    }
  },
  mounted() {

    this.progressInteger = Math.floor(this.progress * 10 / this.total)
    this.progressDecimal = Math.round(this.progress * 100 / this.total) - this.progressInteger * 10
    this.blockimg = '/block/block' + this.progressDecimal + '.jpg'

  }
}
</script>

<style scoped>

</style>