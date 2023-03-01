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
      component: () => import(/* webpackChunkName: "dashboard" */ '../views/DashBoardView.vue')
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
    //  消息推送配置
    {
      path: '/message_configuration',
      name: 'message_configuration',
      component: () => import('../views/home/MessageConfigurationView.vue')
    }
  ]
})

export default router