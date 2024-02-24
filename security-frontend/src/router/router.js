import { createRouter, createWebHistory } from 'vue-router'

const routes=[
    {
        path:'/',
        name:'Welcome',
        component:()=>import('@/components/views/Welcom.vue'),
    },
    {
        path: '/hello',
        name: 'HelloWorld',
        component: ()=>import("@/components/LoginPage.vue"),
        children:{
            path:'/register',
            name:'register',
            component:()=>import("@/components/views/Register.vue")
        }
    },
    {
        path: '/find_back',
        name: 'find_back',
        component: ()=>import("@/components/views/PasswordReFind.vue")
    },
    {
        path: '/wxLogin',
        name: 'wxLogin',
        component: ()=>import("@/components/views/WxLogin.vue")
    },
    {
        path: '/Logout',
        name: 'logout',
        component: ()=>import("@/components/views/LogoutPage.vue")
    },
    {
        path: '/register',
        name: 'register',
        component: ()=>import("@/components/views/Register.vue")
    },
    {
        path: '/index',
        name: 'index',
        component:()=>import("@/components/page/Index.vue")
    },
    {
        name:'analysis',
        path:'/analysis',
        component:()=>import('@/components/page/content/Analysis.vue')
    },
    {
        name:'character-out-fit',
        path:'/character-out-fit',
        component:()=>import('@/components/page/content/CharacterOutFit.vue')
    },
    {
        name:'history',
        path:'/history',
        component:()=>import('@/components/page/content/Hsitory.vue')
    },
    {
        name:'predict',
        path:'/predict',
        component:()=>import('@/components/page/content/Predict.vue')
    },
    {
        name:'pvp',
        path:'/pvp',
        component:()=>import('@/components/page/content/PVP.vue')
    },
    {
        name:'team-rating',
        path:'/team-rating',
        component:()=>import('@/components/page/content/TeamRating.vue')
    },
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
    // mode:'history'
})

export default router
