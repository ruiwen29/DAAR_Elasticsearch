import Vue from 'vue'
import App from './App.vue'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import vuetify from '@/plugins/vuetify'


import './registerServiceWorker'
import router from './router'
Vue.use(BootstrapVue)
Vue.config.productionTip = false

new Vue({
    render: h => h(App),
    router,
    vuetify

}).$mount('#app')