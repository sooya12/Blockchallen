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
    <br style="clear:both;"/>

    <!-- 검색 -->


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


    <!-- 챌린지 목록 -->
    <div>
        <v-btn style="
                float: left; 
                width: 35%; 
                line-height: 40px; 
                margin: 20px" 
            @click="ccreate" class="">챌린지 만들기</v-btn>
        <select v-model="options" class="selectbox" @change="sortfunction($event)">
          <option value="" selected disabled hidden>정렬 기준</option>
          <option value="fast">빠른 시작</option>
          <option value="slow">느린 시작</option>
          <option value="expensive">비싼 배팅</option>
          <option value="cheap">저렴한 배팅</option>
          >
        </select>
      <br style="clear:both;"/>
      <div class="cards">

      <v-slide-item
          v-for="challenge in challengelist
                        .filter((challenge)=> challenge.name.indexOf(this.searchText)!=-1)"
          :key="challenge.id"
      >
            <div class="card"  >
              <div class="card-image-holder">
                <div v-if="!challenge.samplepicture">
                   <img class="card-image" src="https://source.unsplash.com/280x210/?wave" alt="wave" />
                </div>
                <!-- <v-img
                      :src=challenge.samplepicture
                      height="30vh"
                      style="margin: 2%;"

                  >
                  </v-img> -->
                  <div v-if="challenge.samplepicture">
                    <v-img :src="challenge.samplepicture" style="height:210px; width:280px;"></v-img>
                  </div>
              </div>
              <div class="card-title">
                <a @click="ToDetail(challenge.id)" class="toggle-info btn">
                
                  <span class="toggle-left"></span>
                  <span class="toggle-right"></span>
                </a>

                <h2>
                    {{challenge.name}}
                    <small>{{ challenge.fee }}</small>
                </h2>
              </div>
            </div>
        <!-- <v-btn @click="ToDetail(challenge.id)"
            style="
              width:100%; 
              height:250px; 
              border-radius: 50px;
              "
            class="white"

        >{{ challenge.name }}<br>시작일 : {{ challenge.startDate }}<br>마감일 : {{ challenge.endDate }}<br>배팅 금액 :
          {{ challenge.fee }}
        </v-btn> -->
      </v-slide-item>
        <!-- 무한 스크롤 -->
    <!-- <infinite-loading @infinite="infiniteHandler" spinner="circles">  </infinite-loading> -->
      </div>
    </div>
    
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
      options:'',
      samplepicture:'', // 샘플 픽처

      white: '#ffffff',
      lightBG: '#dce1df',
      salmon: '#ff6666',
      tealMid: '#0ebac7',
      tealContrast: '#33ffff',
      tealShade:	'#007c85',
      darkGrey: '#4f585e',

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
        axios.get(this.$store.state.server + '/challenges',{
            params: {
              // limit: this.limit
               option:''
            },
          })
            .then(res => {
              console.log(res)
              this.challengelist = res.data
              
              for (let i = 0; i < res.data.length; i++) {
                if(this.challengelist[i].samplepicture!=null)
                  this.challengelist[i].samplepicture = "data:;base64, " + this.challengelist[i].samplepicture
              }

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
    ccreate: function () {
      this.$router.push('/challenges/create')
    },
    sortfunction: function () {

        axios.get(this.$store.state.server + '/challenges', {
          params:{
            // limit:0,
            option:this.options
          }
        })
              .then(res => {
                 this.challengelist = res.data

                for (let i = 0; i < res.data.length; i++) {
                  if(this.challengelist[i].samplepicture!=null)
                    this.challengelist[i].samplepicture = "data:;base64, " + this.challengelist[i].samplepicture
                }

                //  this.limit =2
              })
    },
    ToDetail(id){
      this.$router.push('challenges/'+id)
    },
    // infiniteHandler($state) {
    //   axios.get(this.$store.state.server + '/challenges',{
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

<style>

.ChallengeList {
  width: 100%;
  max-width: 1000px;
  text-align: center;
  margin: 0px auto;
  padding: 20px;

}

.high {
  width: 100%;
  padding: 10px;
  margin: 20px;
}

.name {
  float: left;
}

.topbutton {
  float: right;
  margin-right: 20px;
}

.selectbox {
  float: right;
  width: 35%;
  line-height: 40px;
  background: url(https://farm1.staticflickr.com/379/19928272501_4ef877c265_t.jpg) no-repeat 95% 50%;
  z-index: 1;
  margin: 20px

}

/* -------------------------------- */


/* body {
  background: lightBG;
  color: darkGrey;
  text-rendering: optimizeLegibility;
} */

a.btn {
  background:#ffbf00;
  border-radius: 4px;
	box-shadow: 0 2px 0px 0 rgba(0,0,0,0.25);
  color: white;
  display: inline-block;
  padding: 6px 30px 8px;
  position: relative;
  text-decoration: none;
	transition: all 0.1s 0s ease-out;
}

a:hover {
  background: lighten(#ffbf00,2.5);
  box-shadow: 0px 8px 2px 0 rgba(0, 0, 0, 0.075);
  transform: translateY(-2px);
  transition: all 0.25s 0s ease-out;
}


.cards {

  max-width: 1000px;
  text-align: center;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  /* grid-auto-rows: auto; */
  grid-gap: 20px;


}

.card {

  display: inline-block;
  margin: 8px;
  max-width: 300px;
  perspective: 1000;
  position: relative;
  text-align: left;
  transition: all 0.3s 0s ease-in;
  width: 300px;
  z-index: 1;
  border: 2px solid #e7e7e7;
  border-radius: 4px;
  padding: .5rem;
  margin-left: auto; 
  margin-right: auto;



}


.card-image-holder {
    background: rgba(0,0,0,0.1);
    height: 0;
    padding-bottom: 75%;
  }

.card-title {
    background: white;
    padding: 6px 15px 10px;
    position: relative;
    z-index: 0;
    

}

.card-title.a.toggle-info {
      border-radius: 32px;
      height: 32px;
      padding: 0;
      position: absolute;
      right: 15px;
      top: 10px;
      width: 32px;
      
  }

a.toggle-info {
      border-radius: 32px;
      height: 32px;
      padding: 0;
      position: absolute;
      right: 15px;
      top: 10px;
      width: 32px;
}

.toggle-left{
        right: 14px;
        transform: rotate(45deg);
        background: white;
        display: block;
        height: 2px;
        position: absolute;
        top: 16px;
        transition: all 0.15s 0s ease-out;
        width: 12px;
}
.toggle-right{
        left: 14px;
        transform: rotate(-45deg);
        background: white;
        display: block;
        height: 2px;
        position: absolute;
        top: 16px;
        transition: all 0.15s 0s ease-out;
        width: 12px;
}


h2 {
      font-size: 24px;
      font-weight: 700;
      letter-spacing: -0.05em;
      margin: 0;
      padding: 0;
      

}
small {
        display: block;
        font-size: 18px;
        font-weight: 600;
        letter-spacing: -0.025em;
}


</style>
