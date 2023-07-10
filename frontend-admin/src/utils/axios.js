import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '@/router/index'
import { sessionGet } from './index'
import config from '~/config'

const ERROR_MESSAGE = {
  "修改藏品错误": "更新失败：请查看当前信息是否与原始信息相同!",
  "修改商品错误": "更新失败：请查看当前信息是否与原始信息相同!",
}

// 这边由于后端没有区分测试和正式，姑且都写成一个接口。
axios.defaults.baseURL = config[import.meta.env.MODE].baseUrl
// 携带 cookie，对目前的项目没有什么作用
axios.defaults.withCredentials = true
// 请求头，headers 信息
axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
// 请求头加token, 这个项目没用， 用的是 Authorization Bearer
axios.defaults.headers['token'] = sessionGet('token') || ''
// 默认 post 请求，使用 application/json 形式
axios.defaults.headers.post['Content-Type'] = 'application/json'
// cdt用的 Bearer + 鉴权
if (sessionGet('token') !== null){
  console.log("有token", sessionGet('token'))
  axios.defaults.headers['Authorization'] = 'Bearer ' + sessionGet('token')
}
// 请求拦截器，内部根据返回值，重新组装，统一管理。
axios.interceptors.response.use(res => {
  console.log("axios封装", res)
  // if (typeof res.data !== 'object') {
  //   ElMessage.error('服务端异常！')
  //   return Promise.reject(res)
  // }
  if (res.data.status !== 'success') {
    if (res.data.status === 'not admin'){
      router.push({ path: '/login' })
      ElMessage.error('您还不是管理员！')
    }
    if (res.data.message) {
      ElMessage.error(ERROR_MESSAGE[res.data.message])
    }
    if (res.data.resultCode === 419) {
      router.push({ path: '/login' })
    }
    return Promise.reject(res.data)
  }

  return res.data.data
})

export default axios