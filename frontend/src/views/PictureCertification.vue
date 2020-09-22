<template>
    <div class="pictureCertification">
        <button @click="certification" type="button">
            인증하기
        </button>

        <div v-show="isModal">
            <input v-on:change='piccer()' type='file' ref='picture' id='picture' accept='.jpg, .png, .gif'>
            <!-- <label class="btn btn-primary btn-file">
                사진 가져오기 <input type="file" style="display:none;">
            </label> -->
            <p>{{picture.lastModifiedDate}}</p>
        </div>
    </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'pictureCertification',
    data(){
        return{
            id:'',
            challenge:'',
            account:'',
            picture:'', 
            regDate:'',
            isReported:'',
            isModal:false,
            del_password:''
        }
    },
    methods:{

        piccer(){
            console.log(this.$refs)
            // console.log(this.picture)
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
        certification:function(){
            this.isModal= !this.isModal
        }
    }
}
</script>

<style scoped>

</style>