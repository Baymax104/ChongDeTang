import axios from "../utils/axios";
import {formatDate} from "../utils/date";

// 获取商品
export function getAllProductByAdmin() {
    return axios.get('/api/product/admin')
}

// 添加商品
export function addProductByAdmin(
    name,
    price,
    photo,
    introduction,
    storage
) {
    return axios.post('/api/product/admin', {
        name: name,
        price: price,
        launch_time: formatDate(),
        photo: photo,
        introduction: introduction,
        storage: storage
    })
}

// 更新信息
export function updateProductNumberByAdmin(
    productId,
    name,
    price,
    photo,
    introduction,
    storage
) {
    return axios.post(`/api/product/admin/${productId}`, {
        name: name,
        price: price,
        launch_time: formatDate(),
        photo: photo,
        introduction: introduction,
        storage: storage
    })
}

// 删除商品
export function deleteProductByAdmin(productId) {
    return axios.delete('/api/product/admin', {
        data: {id: productId}
    })
}
