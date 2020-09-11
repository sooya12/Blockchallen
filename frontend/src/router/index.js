import Vue from 'vue'
import VueRouter from 'vue-router'
import ChallengeCreate from '@/views/ChallengeCreate.vue'
Vue.use(VueRouter)


const routes =[
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