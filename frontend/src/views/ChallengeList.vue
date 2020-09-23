<template>

  <div class="ChallengeList">
    <!-- 상단 -->
    <div class="high">
      <div class="name">
        <strong>{{ user.nickname }}님</strong>
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
    </v-form>
    <!-- select box-->
    <!-- <v-form class="mt-3 px-3 d-flex">
        <v-select
        :items="items"
        label="정렬기준"
        ></v-select>
    </v-form> -->

    <!-- 챌린지 목록 -->
    <v-container>
      <select v-model="options" class="selectbox" @change="sortfunction($event)">
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
        >{{ challenge.name }}<br>시작일 : {{ challenge.startDate }}<br>마감일 : {{ challenge.endDate }}<br>배팅 금액 :
          {{ challenge.fee }}
        </v-btn>
      </v-slide-item>
        <!-- 무한 스크롤 -->
    <!-- <infinite-loading @infinite="infiniteHandler" spinner="circles">  </infinite-loading> -->
    </v-container>
  </div>

</template>
<script>
import axios from 'axios'
// import InfiniteLoading from 'vue-infinite-loading'


export default {
  name: 'ChallengeList',
  data() {
    return {
      user: {
      id: 0,
      challenges: [],
      email: "",
      nickname: "",
      access_token: "",
      walletAddress: "",
    },
      searchText: '',
      challengelist: [],
      limit: 0,
      options:''
    }
  },
  mounted() {
      const user = JSON.parse(sessionStorage.getItem("user"))
      this.user = user
      
  },
  components: {
    // InfiniteLoading
  },
  created() {
    // axios
        axios.get('http://localhost:8080/blockchallen/challenges',{
            params: {
              // limit: this.limit
            },
          })
            .then(res => {
              console.log(res)
              this.challengelist = res.data

            })

  },
  methods: {
    logout: function () {
      let win = window.open('https://accounts.kakao.com/logout?continue=https://accounts.kakao.com/weblogin/account')
      win.close()
      sessionStorage.removeItem("user")
      this.$router.push("/")
    },
    ToMyPage: function () {
      this.$router.push('/mypage')
    },
    sortfunction: function () {

        axios.get('http://localhost:8080/blockchallen/challenges', {
          params:{
            // limit:0,
            options:this.options
          }
        })
              .then(res => {
                 this.challengelist = res.data
                //  this.limit =2
              })
      

    },
    // infiniteHandler($state) {
    //   axios.get('http://localhost:8080/blockchallen/challenges',{
    //         params: {
    //           limit: this.limit+2
    //         },
    //       })
    //   .then(response=>{
    //     setTimeout(()=>{
    //       if(response.data.length){
    //         this.challengelist = this.challengelist.concat(response.data)
    //         $state.loaded()
    //         this.limit += 2
    //         if(this.challengelist.length/2==0){
    //           $state.complete()
    //         }
    //       }else{
    //         $state.complete()
    //       }
    //     },2000)
    //   }).catch(error=>{
    //     console.error(error)
    //   })
          
    // }
  }
}
</script>

<style scoped>

.ChallengeList {
  width: 70%;
  text-align: center;
  margin: 0px auto;
  padding: 20px
}

.high {
  width: 100%;
  padding: 20px;
  margin: 20px
}

.name {
  float: left
}

.topbutton {
  float: right
}

.selectbox {
  float: right;
  width: 20%;
  line-height: 40px;
  background: url(https://farm1.staticflickr.com/379/19928272501_4ef877c265_t.jpg) no-repeat 95% 50%;
  z-index: 1;
  margin: 20px

}

</style>
