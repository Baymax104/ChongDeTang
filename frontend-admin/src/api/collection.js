import axios from "../utils/axios";

// 获取热搜藏品
export function getAllCollectionByAdmin() {
    return axios.get('/api/collection/admin')
}

// 添加藏品
export function addCollection(title, url, type, photo) {
    return axios.post('/api/collection/admin', {
        title: title,
        url: url,		// 链接
        type: type,	// zk、pb、sf
        photo: photo
    })
}

// 删除藏品
export function removeCollectionByAdmin(collection_id) {
    return axios.delete('/api/collection/admin', {
        data: {id: collection_id}
    })
}

// 更新藏品
export function updateCollectionByAdmin(collection_id, title, url, type, photo) {
    return axios.post(`/api/collection/admin/${collection_id}`, {
        title: title,
        url: url,		// 链接
        type: type,	// zk、pb、sf
        photo: photo
    })
}