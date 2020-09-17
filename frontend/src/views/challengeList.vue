<template>
    <div class="ChallengeList">
        <!-- 상단 -->
        <div class="high">
            <div class="name">
                <strong>{{user.nickname}}님</strong>
            </div>
            <div class="topbutton">
                <v-btn @click="ToMyPage">마이페이지</v-btn>
                <v-btn @click="logout">로그아웃</v-btn>
            </div>
        </div>

        <!-- 검색 -->
        <v-form class="mt-3 px-3 d-flex">
            
             <v-text-field
                label="어떤 챌린지를 찾고 계신가요?"
                outlined
                v-model="searchText"
                class="col-12 px-3"
                color="#5858FA"
                style="padding:10px;"

            >
            </v-text-field>

            <div>
                <v-btn 
                    @click="find" 
                    text class="mt-3 font-weight-bold" 
                    color="#5858FA"
                    ><font-awesome-icon icon="search"  
                        color="#5858FA" size='2x'/></v-btn>
            </div>
        </v-form>
        <!-- select box-->
        <v-form class="mt-3 px-3 d-flex">
            <v-select
            :items="items"
            label="정렬기준"
            ></v-select>
        </v-form>

        <!-- 챌린지 목록 -->
        <v-container>
            <v-slide-item
                v-for="challenge in challengelist.filter((challenge)=> challenge.name.indexOf(this.searchText)!=-1)"
                :key="challenge.id"
            >
                <v-btn 
                 style="width:100%; height:300px; border-radius: 50px;"
               >{{challenge.name}}<br>시작일 : {{challenge.startDate}}<br>마감일 : {{challenge.endDate}}<br>배팅 금액 : {{challenge.fee}}
               </v-btn>
            </v-slide-item>
        </v-container>

         
    </div>
</template>
<script>
import axios from 'axios'

export default{

        name:'ChallengeList',
        data(){
            return{
            user:'',
            searchText:'',
            challengelist:[],
            items:['빠른시작','느린시작','비싼배팅','저렴한배팅']
            }
        },
        computed:{
           
            // challengelist: function(){
            //     return  this.challengelist.filter((challenge)=> challenge.name.indexOf(this.searchText)!=-1)
            // }
        },
        created(){
          // axios
            axios.get('/jsontest/Account.json')
            .then(res=> {
                console.log(res) 
                this.user = res.data
            }),
          axios.get('/jsontest/Challenge.json')
            .then(res=> {
                console.log(res) 
                this.challengelist = res.data.ChallengeList
            })
        },
         methods:{
            logout:function(){

             },
             ToMyPage:function(){
            this.$router.push('/MyPage')
            },
              find:function(){
                if(this.challengelist.name.includes(this.searchText)){

                    alert("찾음")
                }else{
                    alert("없어")
                }
            }

    }
    
   
}
</script>
<style scoped>
    .ChallengeList{width:70%; text-align:center; margin:0px auto; padding:20px}
    .high{width:100%;padding:20px;margin:20px}
    .name{float:left}
    .topbutton{float:right}
    

</style>