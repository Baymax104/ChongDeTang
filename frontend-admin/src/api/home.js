import axios from "../utils/axios";

// 获取用于审核的列表
export function getAuditEventList(f) {
    return axios.get(
        '/api/user/appointment/checklist',{
            params: {
                filter: f
            }
        }
    )
}

// 审核预约
export function auditEvent(id, status) {
    return axios.post('/api/user/appointment/status', {
        id: id,
        status: status
    })
}

// 获取热搜藏品
export function getHotList() {
    return axios.get('/api/collection/hot')
}

// 获取非热搜藏品
export function getNotHotList() {
    return axios.get('/api/collection/not-hot')
}

// 设置热搜商品
export function setHotList(idList, dir) {

    let dataList = []
    if (dir === 'right'){
        idList.forEach(i => {
            dataList.push({
                id: i,
                selected: 1
            })
        })
    }
    else if (dir === 'left'){
        idList.forEach(i => {
            dataList.push({
                id: i,
                selected: 0
            })
        })
    }

    return axios.post('/api/collection/select', dataList)
}

// 首页数据加载
export function getSysInfo() {
    return axios.get('/api/meta/basis')
}

// 首页图片数据加载
export function getChartData(days) {
    return axios.post('/api/meta/order', {
        days: days
    })
}