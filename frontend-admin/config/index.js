import {baseUrl} from "./app-key";

export default {
  development: {
    baseUrl: '/api' // 测试接口域名
  },
  beta: {
    baseUrl: baseUrl // 测试接口域名
  },
  release: {
    baseUrl: baseUrl // 正式接口域名
  }
}