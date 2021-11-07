import Vue from 'vue'
import VueRouter from 'vue-router'
import Search from "../components/Search.vue"
import AddCV from "../components/AddCV.vue"

Vue.use(VueRouter)

export default new VueRouter({
    routes:[
        {
            path: '/addCV',
            component: AddCV
        },
        {
            path: '/search',
            component: Search
        }
    ]
})