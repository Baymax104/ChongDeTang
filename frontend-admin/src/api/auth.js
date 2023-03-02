import axios from "../utils/axios";

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