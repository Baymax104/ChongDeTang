import axios from '../utils/axios'
import { encodePassword } from "../utils/aes";

// 登录
export function login(phone, password) {
    return axios.post('/api/user/login', {
        phone: phone || '',
        password: encodePassword(password)
    })
}
