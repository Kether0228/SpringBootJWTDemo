import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/login/Login.vue'
import Register from '../views/register/Register.vue'

const routes = [
  {
    path: '/',
    redirect: '/login'  // 將根路徑重定向到登入頁面
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  }
  // ... 其他路由
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router