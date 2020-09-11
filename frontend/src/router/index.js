import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '@/views/Home.vue'
import ChallengeList from '@/views/ChallengeList.vue'
import MyPage from '@/views/MyPage.vue'

Vue.use(VueRouter)

const routes =[
    {
        path: '/',
        name: 'home',
        component: Home,
    },
    {
        path:'/ChallengeList',
        name:'ChallengeList',
        component:ChallengeList
    },
    {
        path:'/MyPage',
        name:'MyPage',
        component:MyPage
    }
]

const router = new VueRouter({
    mode : 'history',
    base : process.env.BASE_URL,
    routes,
})

export default router