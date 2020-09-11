import Vue from 'vue'
import VueRouter from 'vue-router'
import ChallengeCreate from '@/views/ChallengeCreate.vue'
import Home from '@/views/Home.vue'
<<<<<<< HEAD
import ChallengeList from '@/views/ChallengeList.vue'
import MyPage from '@/views/MyPage.vue'
=======
import SignUp from '@/views/SignUp.vue'
>>>>>>> 525cf67460bc3088f52b4d4d1014d30d62a3cce7

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'home',
        component: Home,
    },
    {
<<<<<<< HEAD
        path:'/ChallengeList',
        name:'ChallengeList',
        component:ChallengeList
    },
    {
        path:'/MyPage',
        name:'MyPage',
        component:MyPage
    }
=======
        path: '/signup',
        name: 'signup',
        component: SignUp,
    },
    {
        path: '/challenges/create',
        name: "ChallengeCreate",
        component: ChallengeCreate
    },
>>>>>>> 525cf67460bc3088f52b4d4d1014d30d62a3cce7
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router