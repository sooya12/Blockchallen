<template>
    <div class="pictureCertification">
        <!-- <input v-on:change='piccer()' type='file' ref='picture' id='picture' accept='.jpg, .png, .gif'> -->
        <v-img :src="imageUrl" v-if="imageUrl" style="width:auto; max-height:40vh; max-width:30vw; min-width:20vw;"></v-img>
        <br style="clear:both;"/>
            <v-file-input
                v-model="picture"
                accept="image/png, image/jpeg, image/bmp, image/gif"
                prepend-icon="mdi-camera"
                :rules="picturelimit"

            >

            </v-file-input>
        
        <!-- <v-img :src="imageUrl" v-if="imageUrl" style="width:100%; "></v-img> -->
        <!-- <p>{{picture.lastModifiedDate}}</p> -->
        <br style="clear:both;"/>
        <v-btn @click="submit" :disabled="!picture&&!isClick" v-if="!isClick">사진 올리기</v-btn>
        <div v-if="isClick">
          <div class="loader loader--style3" title="2">
            <svg version="1.1" id="loader-1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
                 width="40px" height="40px" viewBox="0 0 50 50" style="enable-background:new 0 0 50 50;" xml:space="preserve">
                  <path fill="#000" d="M43.935,25.145c0-10.318-8.364-18.683-18.683-18.683c-10.318,0-18.683,8.365-18.683,18.683h4.068c0-8.071,6.543-14.615,14.615-14.615c8.072,0,14.615,6.543,14.615,14.615H43.935z">
                    <animateTransform attributeType="xml"
                                      attributeName="transform"
                                      type="rotate"
                                      from="0 25 25"
                                      to="360 25 25"
                                      dur="0.6s"
                                      repeatCount="indefinite"/>
                  </path>
            </svg>
          </div>

        </div>
    </div>

</template>

<script>
import axios from 'axios'
import Web3 from 'web3'

var web3 = new Web3('http://j3a102.p.ssafy.io:8545')
var sha256 = require('js-sha256');

export default {
    name: 'pictureCertification',
    props:{
        challengeid: Number  // 챌린지 id
    },
    data(){
        return{
            isClick:false,
            picture:'', // 사진 정보
            imageUrl:'', // 사진 url
            picturelimit:[
                value => !value || value.size < 16000000 || '이미지가 선택되지 않았거나 이미지 크기는 16MB 이하여야 합니다.!',
            ],
        }
    },

    methods:{
        piccer(){
            console.log(this.$refs)
            this.picture = this.$refs.picture.files[0]
            this.imageUrl = URL.createObjectURL(this.picture)
            console.log(this.picture.type)
            console.log(this.picture.lastModifiedDate.toISOString().substr(0, 10))
            console.log(this.picture.name)

        },
        submit(){
            this.isClick=true
            const formData = new FormData()
            const offset = new Date().getTimezoneOffset()*60000
            const rdate = new Date(this.picture.lastModifiedDate - offset)

            formData.append('picture',this.picture)
            formData.append('uid',JSON.parse(sessionStorage.getItem("user")).id)
            formData.append('cid',this.challengeid)
            formData.append('regDate',rdate.toISOString().substr(0, 10))

            console.log(this.picture.lastModifiedDate.toISOString().substr(0, 10))
            console.log(rdate.toISOString().substr(0, 10)) // 변경된 시간

            console.log(this.picture.lastModifiedDate)
            console.log(this.picture.lastModifiedDate.toISOString())
            console.log(this.picture.lastModifiedDate.toISOString().substr(0, 10))


            // axios로 multipart/form data Post 요청 보내기
            axios.post(this.$store.state.server + '/certification/register', formData,{
                headers:{
                    'Content-Type' : 'multipart/form-data'
                }

            }).then((res)=>{
                console.log(res)
                this.$emit('close')
                window.location.reload()
                if(res.data){
                    alert("사진 등록이 완료되었습니다.")
                }else{
                    this.isClick=false
                    alert("올바른 날짜의 사진이 아닙니다. 재업로드 해주세요.")
                }
            }).catch((err)=>{
                console.log(err)
                this.isClick=false
            })


        },
        //////////////////// 스마트 컨트랙트 ///////////////////////////
        sendCertification(){
            let hashingDatabefore = sha256.hex(this.picture.name) // 이름말고 바이트배열
            let hashingData='0x'+hashingDatabefore

            web3.eth.personal.unlockAccount("0x51ec5dfdf9a50762ba34d3ac08abb43cdb54d597", "pass5", 600).then(()=>{
              web3.eth.sendTransaction({
                from: "0x51ec5dfdf9a50762ba34d3ac08abb43cdb54d597",
                gasPrice: "200000000",
                gas: "1000000",
                to: '0x51465bd3f63f275ed5fde5918d31c2a51650b964', 
                value: "0",
                data: hashingData // deploying a contracrt
              }).then((res)=>{
                console.log(res)
              })
              .catch(()=>{console.log('error')})
            
            })
            //////////////////////////////////
            // web3.eth.personal.unlockAccount("0x51ec5dfdf9a50762ba34d3ac08abb43cdb54d597", "pass5", 600).then(()=>{
            //     web3.eth.getTransaction(this.transactionHash).then((res)=>{

            //     this.checkflag=true
            //     this.sameCheck=hashingData==String(res.input)
            //     })
            //     .catch(()=>{console.log('error')})

            // })


            }
    },
    watch:{
        picture:function (newVal){
            if(newVal==null){
                this.imageUrl=null
                return;
            }
            this.imageUrl = URL.createObjectURL(newVal);

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

.loader{
  margin: 0 0 2em;
  height: 100px;
  width: 20%;
  text-align: center;
  padding: 1em;
  margin: 0 auto 1em;
  display: inline-block;
  vertical-align: top;
}
</style>