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
    props:{
        challengeid: Number  // 챌린지 id
    },
    data(){
        return{
            // id:'', // id
            account:'', // 계정 id
            picture:'', // 사진 정보
            // regDate:'', // 등록일
            // isReported:false, // 신고여부

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
            formData.append('uid',JSON.parse(sessionStorage.getItem("user")).id)
            formData.append('cid',this.challengeid)


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