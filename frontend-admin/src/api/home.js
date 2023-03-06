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