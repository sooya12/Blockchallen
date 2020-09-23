<template>
    <div class="pictureCertification">
        <input v-on:change='piccer()' type='file' ref='picture' id='picture' accept='.jpg, .png, .gif'>
        <p>{{picture.lastModifiedDate}}</p>
        <br style="clear:both;"/>
        <v-btn @click="$emit('close')">확인</v-btn>
    </div>

</template>

<script>
import axios from 'axios'

export default {
    name: 'pictureCertification',
    data(){
        return{
            picture:'', 
        }
    },
    methods:{

        piccer(){
            console.log(this.$refs)
            this.picture = this.$refs.picture.files[0]
            console.log(this.picture.type)
            console.log(this.picture.lastModifiedDate)
            console.log(this.picture.name)

        },
        submit(){

            const formData = new FormData()
            formData.append('picture',this.picture)

            // axios로 multipart/form data Post 요청 보내기
            axios.post('http://localhost:8080/blockchallen/certification/register', formData,{
                headers:{
                    'Content-Type' : 'multipart/form-data'
                }

            }).then((res)=>{
                console.log(res)
                this.$router.push({name:'picture'})
            }).catch((err)=>{
                console.log(err)
            })
        },
    }
}
</script>

<style scoped>
.pictureCertification{
  text-align: center;
  margin: 0px auto;
  padding: 100px;
}
</style>