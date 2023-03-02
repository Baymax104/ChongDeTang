import axios from "../utils/axios";

// 获取所有用户列表
export function getUserList() {
    return axios.get('/api/user/admin')
}

// 管理员授权
export function setAdmin(phone, mode) {
    return axios.post('/api/user/admin', {
        phone: phone,
        mode: mode
    })
}

// 取消授权
export function resetAdmin(phone, mode){
    return axios.post('/api/user/admin', {
        phone: phone,
        mode: mode
    })
}