import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
  history: createWebHistory(), // hash模式：createWebHashHistory，history模式：createWebHistory
  routes: [
    {
      path: '/',
      redirect: '/dashboard'
    },
    //  登录
    {
      path: '/login',
      name: 'login',
      component: () => import(/* webpackChunkName: "login" */ '../views/UserLoginView.vue')
    },
    //  监控面板
    {
      path: '/dashboard',
      name: 'dashboard',
      component: () => import(/* webpackChunkName: "dashboard" */ '../views/single/DashBoardView.vue')
    },

    //  活动预约审核
    {
      path: '/event_audit',
      name: 'event_audit',
      component: () => import('../views/home/EventAuditView.vue')
    },
    //  藏品精选筛选
    {
      path: '/collection_filtrate',
      name: 'collection_filtrate',
      component: () => import('../views/home/CollectionFiltrateView.vue')
    },
      // 权限管理
    {
      path: '/auth_manage',
      name: 'auth_manage',
      component: () => import('../views/single/AuthManageView.vue')
    },
      // 藏品管理
    {
      path: '/collection_manage',
      name: 'collection_manage',
      component: () => import('../views/single/CollectionManageView.vue')
    },
      // 商品管理
    {
      path: '/product_manage',
      name: 'product_manage',
      component: () => import('../views/single/ProductManageView.vue')
    },
      // 订单管理
    {
      path: '/order_manage',
      name: 'order_manage',
      component: () => import('../views/single/OrderManageView.vue')
    },
  ]
})

export default router