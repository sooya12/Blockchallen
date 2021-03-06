import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex)

export default new Vuex.Store({
    plugins: [
        createPersistedState()
    ],
    state:{
        // server: 'http://localhost:8080/blockchallen',
        server: 'https://j3a102.p.ssafy.io/blockchallen',
    },
})