import Vue from 'vue'
import VueRouter from 'vue-router'
import ChallengeCreate from '@/views/ChallengeCreate.vue'
import Home from '@/views/Home.vue'


Vue.use(VueRouter)

const routes =[
        {
            path: '/',
            name: 'home',
            component: Home,
        },
        {
            path: '/challenges/create',
            name : "ChallengeCreate",
            component : ChallengeCreate
        },
]

const router = new VueRouter({
    mode : 'history',
    base : process.env.BASE_URL,
    routes
})

export default router