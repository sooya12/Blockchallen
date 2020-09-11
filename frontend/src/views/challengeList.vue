<template>
    <div class="ChallengeList">
        <!-- 상단 -->
        <div class="high">
            <div class="name">
                <strong>{{user}}님</strong>
            </div>
            <div class="topbutton">
                <v-btn @click="ToMyPage">마이페이지</v-btn>
                <v-btn @click="logout">로그아웃</v-btn>
            </div>
        </div>

        <!-- 검색 -->
        <div class="search">
            <v-text-field
                label="어떤 챌린지를 찾고 계신가요?"
                outlined
                append-icon="mdi-magnify"
                v-model="searchText"
                class="col-12 px-3"
                color="#5858FA"
                style="padding:10px"
                @click="find"
            >
            </v-text-field>
        </div>

        <!-- 챌린지 목록 -->
        <v-container>
            <v-slide-item
                v-for="(challenge,index) in challengelist"
                :key="index"
            >
                <v-btn 
                 style="width:100%; height:300px; border-radius: 50px;"
               >{{challenge}}
               </v-btn>
            </v-slide-item>
        </v-container>

         
    </div>
</template>
<script>
// import axios from 'axios'

export default{

        name:'ChallengeList',
        data(){
            return{
            user:'사랑하는 고객',
            searchText:'',
            challengelist:['챌린지1','챌린지2','챌린지3','챌린지4']
            }
        },
        created(){
          // axios
        },
         methods:{
        logout:function(){

        },
        ToMyPage:function(){
            this.$router.push('/MyPage')
        },
        find:function(){
            alert("찾기")
        }
    }
    
   
}
</script>
<style scoped>
    .ChallengeList{width:70%; text-align:center; margin:0px auto; padding:20px}
    .high{width:100%;}
    .name{float:left}
    .topbutton{float:right}
    

</style>