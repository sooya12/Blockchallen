import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate';


Vue.use(Vuex)
export default new Vuex.Store({
    plugins: [
        createPersistedState()
    ],
    state:{
<<<<<<< HEAD
          server: 'http://localhost:8080/blockchallen',
        //server: 'https://j3a102.p.ssafy.io/blockchallen',
=======
         // server: 'http://localhost:8080/blockchallen',
        server: 'https://j3a102.p.ssafy.io/blockchallen',
>>>>>>> 7af9b1deb88b3ae566ba5cabcebd4c0f6759793a
    },
    getters: {

    },
    mutations:{

    },


})