<template>
  <div style="width: 100%; max-width: 1000px; margin: 0 auto;">
    <v-btn
        color="#000000"
        top
        style="margin: 2%; float: right; color : #ffffff;"
        @click="logout"
    >
      로그아웃
    </v-btn>
    <v-btn
        color="#ffffff"
        top
        right
        elevation="0"
        style="margin: 2%; float: right;"
        @click="goMypage"
    >
      {{ user.nickname }} 님
    </v-btn>
    <div class="ChallengeList">
      <br style="clear:both;"/>

      <!-- 검색 -->
      <v-text-field
          label="어떤 챌린지를 찾고 계신가요?"
          outlined
          v-model="searchText"
          class="col-12 px-3"
          color="#bbbbbb"
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
                margin: 11px;
                background-color:#f39c14;
                color:white "
               @click="ccreate" class="">챌린지 만들기
        </v-btn>
        <select v-model="options" class="selectbox" @change="sortfunction($event)"
                style="margin:11px;">
          <option value="" selected disabled hidden>정렬 기준</option>
          <option value="fast">빠른 시작</option>
          <option value="slow">느린 시작</option>
          <option value="expensive">비싼 배팅</option>
          <option value="cheap">저렴한 배팅</option>
          >
        </select>
        <br style="clear:both;"/>
        <div v-if="Loading">
          <v-progress-circular
              :size="70"
              :width="7"
              color="#f39c14"
              indeterminate
          ></v-progress-circular>
        </div>
        <div class="cards" v-if="!Loading">

          <v-slide-item
              v-for="challenge in challengelist
                        .filter((challenge)=> challenge.name.indexOf(this.searchText)!=-1)"
              :key="challenge.id"
          >
            <div class="card" @click="ToDetail(challenge.id)">
              <div class="card-image-holder">
                <div v-if="!challenge.samplepicture">
                  <img class="card-image" src="https://source.unsplash.com/280x210/?wave" alt="wave"/>
                </div>
                <div v-if="challenge.samplepicture">
                  <v-img :src="challenge.samplepicture" style="height:210px; width:280px;"></v-img>
                </div>
              </div>
              <div class="card-title">
                <h2>
                  {{ challenge.name }}
                  <small>{{ challenge.fee }} ETH</small>
                </h2>
                <div class="chips">
                  <v-chip small v-if="challenge.startDate <= today && today < challenge.endDate" color="#f39c14">진행중
                  </v-chip>
                  <v-chip small v-if="challenge.endDate <= today" color="#bbbbbb">마감</v-chip>
                  <v-chip small v-if="today < challenge.expireDate+1" color="#FC766A">모집중</v-chip>
                  <v-chip small v-if="challenge.expireDate+1 <= today && today < challenge.startDate" color="#5C84B1">
                    대기중
                  </v-chip>
                </div>
              </div>
            </div>
          </v-slide-item>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from 'axios'

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
      options: '',
      samplepicture: '', // 샘플 픽처

      // 상태변수
      waiting: false, // 대기중
      ongoing: false, // 진행중
      terminate: false, // 마감
      gathering: false, // 모집중

      Loading: true,

      // 날짜
      today: new Date(Date.now() - new Date().getTimezoneOffset() * 60000).toISOString().substr(0, 10),

      // 색 변수
      white: '#ffffff',
      lightBG: '#dce1df',
      salmon: '#ff6666',
      tealMid: '#0ebac7',
      tealContrast: '#33ffff',
      tealShade: '#007c85',
      darkGrey: '#4f585e',

    }
  },
  mounted() {
    const user = JSON.parse(sessionStorage.getItem("user"))
    this.user = user

  },
  created() {
    axios.get(this.$store.state.server + '/challenges', {
      params: {
        option: ''
      },
    })
        .then(res => {
          this.challengelist = res.data

          for (let i = 0; i < res.data.length; i++) {
            if (this.challengelist[i].samplepicture != null)
              this.challengelist[i].samplepicture = "data:;base64, " + this.challengelist[i].samplepicture
          }
          this.Loading = false
        })
  },
  methods: {
    logout: function () {
      let win = window.open('https://accounts.kakao.com/logout?continue=https://accounts.kakao.com/weblogin/account')
      win.close()
      sessionStorage.removeItem("user")
      this.$router.push("/")
    },
    goMypage: function () {
      this.$router.push('/mypage')
    },
    ccreate: function () {
      this.$router.push('/challenges/create')
    },
    sortfunction: function () {
      axios.get(this.$store.state.server + '/challenges', {
        params: {
          option: this.options
        }
      })
          .then(res => {
            this.challengelist = res.data

            for (let i = 0; i < res.data.length; i++) {
              if (this.challengelist[i].samplepicture != null)
                this.challengelist[i].samplepicture = "data:;base64, " + this.challengelist[i].samplepicture
            }
          })
    },
    ToDetail(id) {
      this.$router.push('challenges/' + id)
    },
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

.selectbox {
  float: right;
  width: 35%;
  line-height: 40px;
  background: url(https://farm1.staticflickr.com/379/19928272501_4ef877c265_t.jpg) no-repeat 95% 50%;
  z-index: 1;
  margin: 20px

}

.chips {
  float: right;
}

.cards {
  max-width: 1000px;
  text-align: center;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  grid-gap: 20px;
}

.card {
  display: inline-block;
  margin: 8px;
  max-width: 300px;
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

.card:hover {
  box-shadow: 5px 8px 1px 0px #e7e7e7;
  transform: translateY(-2px);
  transition: all 0.25s 0s ease-out;
}

.card-image-holder {
  background: rgba(0, 0, 0, 0.1);
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
