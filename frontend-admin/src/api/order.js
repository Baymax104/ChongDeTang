import axios from "../utils/axios";

// 获取所有
export function getOrdersByAdmin() {
    return axios.get('/api/order/admin')
}

// 更新状态
export function changeOrderStatus(id, status) {
    return axios.post('/api/order/change', {},{
        params:{
            id: id,
            status: status
        }
    })
}