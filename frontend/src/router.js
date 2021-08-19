
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import TicketManager from "./components/TicketManager"

import PaymentManager from "./components/PaymentManager"

import KickManager from "./components/KickManager"

import MessageManager from "./components/MessageManager"


import KickboardView from "./components/KickboardView"
export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/tickets',
                name: 'TicketManager',
                component: TicketManager
            },

            {
                path: '/payments',
                name: 'PaymentManager',
                component: PaymentManager
            },

            {
                path: '/kicks',
                name: 'KickManager',
                component: KickManager
            },

            {
                path: '/messages',
                name: 'MessageManager',
                component: MessageManager
            },


            {
                path: '/kickboardViews',
                name: 'KickboardView',
                component: KickboardView
            },


    ]
})
