import {createRouter, createWebHistory} from 'vue-router'

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
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
    // mode:'history'
})

export default router
