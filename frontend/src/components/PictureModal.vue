<template>
    <div class="pictureCertification">
        <input v-on:change='piccer()' type='file' ref='picture' id='picture' accept='.jpg, .png, .gif'>
        <p>{{picture.lastModifiedDate}}</p>
        <!-- <br style="clear:both;"/> -->
        <v-btn @click="submit">사진 올리기</v-btn>

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
            picture:'', // 사진 정보
        }
    },

    methods:{
        piccer(){
            console.log(this.$refs)
            this.picture = this.$refs.picture.files[0]
            console.log(this.picture.type)
            console.log(this.picture.lastModifiedDate.toISOString().substr(0, 10))
            console.log(this.picture.name)

           
        },
        submit(){
             const formData = new FormData()
            formData.append('picture',this.picture)
            formData.append('uid',JSON.parse(sessionStorage.getItem("user")).id)
            formData.append('cid',this.challengeid)
            formData.append('regDate',this.picture.lastModifiedDate.toISOString().substr(0, 10))


            // axios로 multipart/form data Post 요청 보내기
            axios.post(this.$store.state.server + '/certification/register', formData,{
                headers:{
                    'Content-Type' : 'multipart/form-data'
                }

            }).then((res)=>{
                if(res.data){
                    alert("사진 등록이 완료되었습니다.")
                }else{
                    alert("올바른 날짜의 사진이 아닙니다. 재업로드 해주세요.")
                }
            }).catch((err)=>{
                console.log(err)
            })

            this.$emit('close')
            window.location.reload()

        }
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