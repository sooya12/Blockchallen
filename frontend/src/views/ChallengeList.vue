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
                append-icon="mdi-magnify"
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
        <!-- <v-form class="mt-3 px-3 d-flex">
            <v-select
            :items="items"
            label="정렬기준"
            ></v-select>
        </v-form> -->

        <!-- 무한 스크롤 -->
        <infinite-loading @infinite="infiniteHandler" spinner="circles"></infinite-loading>
        <!-- 챌린지 목록 -->
        <v-container>
            <select class="selectbox" @change="sortfunction($event)">
                <option value="fast">빠른 시작</option>
                <option value="slow">느린 시작</option>
                <option value="expensive">비싼 배팅</option>
                <option value="cheap">저렴한 배팅</option>
            >
                
            
            </select>
                <v-slide-item
                    v-for="challenge in challengelist
                        .filter((challenge)=> challenge.name.indexOf(this.searchText)!=-1)"
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
import InfiniteLoading from 'vue-infinite-loading'

export default{

        name:'ChallengeList',
        data(){
            return{
            user:'',
            searchText:'',
            challengelist:[],
            limit:0
            }
        },
        computed:{
            InfiniteLoading
            // challengelist.sort((a,b)=>a.startDate>b.startDate?1:-1)

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
        methods: {
          logout: function () {
          },
          ToMyPage: function () {
            this.$router.push('/mypage')
          },
          sortfunction:function(event){
                if(event.target.value=="fast"){
                    alert("소팅1")
                }else if(event.target.value=="slow"){
                    alert("소팅2")
                }else if(event.target.value=="expensive"){
                    alert("소팅3")
                }else if(event.target.value=="cheap"){
                    alert("소팅4")
                }
                // ag{
                //     sortrlwns: etv
                // }
                // .t (res ){
                //     this.challengelist=res.data;
                // }
                
            },
            infiniteHandler($state){
                http
                    .get(api, {
                        params:{
                            limit: this.limit
                        },
                    })
                    .then((response)=>{
                        setTimeout(()=>{
                            if(response.data.length){
                                this.challengelist=this.challengelist.concat(response.data)
                                this.limit +=3
                                $state.loaded()

                            }else{
                                $state.complete()
                            }
                        }, 1000)
                    })
                    .catch(()=>{})
            }


          }
        }


</script>
<style scoped>
    .ChallengeList{width:70%; text-align:center; margin:0px auto; padding:20px}
    .high{width:100%;padding:20px;margin:20px}
    .name{float:left}
    .topbutton{float:right}
    .selectbox{
        float: right; 
        width: 20%; 
        line-height: 40px; 
        background: url(https://farm1.staticflickr.com/379/19928272501_4ef877c265_t.jpg) 
        no-repeat 95% 50%;  z-index: 1; 
        margin:20px
        
    } 
    /* .selectbox:hover{
        background-color:#e6e6ff;
    } */
    /* .selectbox-selected{
        background-color:#e6e6ff;
    } */
    /* option:checked{
        background-color:#e6e6ff;
    } */


</style>