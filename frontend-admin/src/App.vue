<template>
    <div class="layout">
        <el-container v-if="state.showMenu" class="container">
            <el-aside class="aside">
                <div class="head">
                    <div>
                        <img src="/src/assets/logo.png" alt="logo">
                        <span>崇德堂</span>
                    </div>
                </div>
                <div class="line" />
                <el-menu background-color="#222832" text-color="#fff" :router="true" :default-openeds="state.defaultOpen"
                    :default-active='state.currentPath'>

                  <el-menu-item index="/dashboard">
                    <el-icon><Plus /></el-icon>
                    <span>数据分析看板</span>
                  </el-menu-item>

                    <el-sub-menu index="2">
                        <template #title><el-icon><setting /></el-icon>
                            <span>软件首页管理</span>
                        </template>
                        <el-menu-item-group>
                            <el-menu-item index="/event_audit"><el-icon>
                                    <Picture />
                                </el-icon>活动预约审核</el-menu-item>
                            <el-menu-item index="/collection_filtrate"><el-icon>
                                    <StarFilled />
                                </el-icon>藏品精选筛选</el-menu-item>
                        </el-menu-item-group>
                    </el-sub-menu>


                  <el-sub-menu index="3">
                    <template #title><el-icon><setting /></el-icon>
                      <span>软件业务管理</span>
                    </template>
                    <el-menu-item-group>
                      <el-menu-item index="/collection_manage">
                        <el-icon><setting /></el-icon>
                        <span>藏品管理</span>
                      </el-menu-item>
                      <el-menu-item index="/product_manage">
                        <el-icon><setting /></el-icon>
                        <span>商品管理</span>
                      </el-menu-item>
                      <el-menu-item index="/order_manage">
                        <el-icon><setting /></el-icon>
                        <span>订单管理</span>
                      </el-menu-item>
                    </el-menu-item-group>
                  </el-sub-menu>

                  <el-menu-item index="/auth_manage">
                    <el-icon><setting /></el-icon>
                    <span>人员权限管理</span>
                  </el-menu-item>
                </el-menu>
            </el-aside>
            <el-container class="content">
                <Header />
                <div class="main">
                    <router-view />
                </div>
                <Footer />
            </el-container>
        </el-container>
        <el-container v-else class="container">
            <router-view />
        </el-container>
    </div>
</template>

<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import Header from '../src/components/layout/Header.vue'
import Footer from '../src/components/layout/Footer.vue'
import { sessionGet, pathMap } from './utils'
import { ElMessage } from "element-plus";

const noMenu = ['/login']
const router = useRouter()
const state = reactive({
    showMenu: true,
    defaultOpen: ['1', '2', '3', '4'],
    currentPath: '/',
})

router.beforeEach((to, from, next) => {
    if (to.path == '/login') {
        // 如果路径是 /login 则正常执行
        next()
    } else {
        // 如果不是 /login，判断是否有 token
        if (!sessionGet('token')) {
            // 如果没有，则跳至登录页面
            ElMessage.error("登录过期，请重新登录！")
            next({ path: '/login' })
        } else {
            // 否则继续执行
            next()
        }
    }
    state.showMenu = !noMenu.includes(to.path)
    state.currentPath = to.path
    document.title = pathMap[to.name]
})
</script>

<style scoped>
.layout {
    min-height: 100vh;
    background-color: #ffffff;
}

.container {
    height: 100vh;
}

.aside {
    width: 200px !important;
    background-color: #222832;
}

.head {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 50px;
}

.head>div {
    display: flex;
    align-items: center;
}

.head img {
    width: 50px;
    height: 46px;
    margin-right: 10px;
}

.head span {
    font-size: 20px;
    color: #ffffff;
}

.line {
    border-top: 1px solid hsla(0, 0%, 100%, .05);
    border-bottom: 1px solid rgba(0, 0, 0, .2);
}

.content {
    display: flex;
    flex-direction: column;
    max-height: 100vh;
    overflow: hidden;
}

.main {
    height: calc(100vh - 100px);
    overflow: auto;
    padding: 10px;
}
</style>

<style>
body {
    padding: 0;
    margin: 0;
    box-sizing: border-box;
}

.el-menu {
    border-right: none !important;
}

.el-submenu {
    border-top: 1px solid hsla(0, 0%, 100%, .05);
    border-bottom: 1px solid rgba(0, 0, 0, .2);
}

.el-submenu:first-child {
    border-top: none;
}

.el-submenu [class^="el-icon-"] {
    vertical-align: -1px !important;
}

a {
    color: #409eff;
    text-decoration: none;
}

.el-pagination {
    text-align: center;
    margin-top: 20px;
}

.el-popper__arrow {
    display: none;
}</style>