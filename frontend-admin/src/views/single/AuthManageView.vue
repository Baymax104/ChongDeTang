<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <span>授权用户为管理员</span>
      </div>
    </template>
    <el-table :data="filterTableData" :default-sort="{ prop: 'admin', order: 'descending' }" stripe style="width: 100%" v-loading="table_loading" height="77vh">
      <el-table-column label="id" prop="id" sortable />
      <el-table-column label="头像">
        <template #default="scope">
          <div style="display: flex; align-items: center">
            <el-avatar v-if="scope.row.photo" :src="scope.row.photo" />
            <el-avatar v-else>用户</el-avatar>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="用户名" prop="username" />
      <el-table-column label="性别" prop="gender" />
      <el-table-column label="生日" prop="birthday" />
      <el-table-column label="手机号" prop="phone" />
      <el-table-column label="邮箱" prop="mail" />
      <el-table-column label="权限等级" prop="admin" :rolepad = "rolePad" sortable>
        <template #default="scope">
          <el-tag :type="scope.row.admin === '1' ? 'danger' : '' " disable-transitions>{{ rolePad[scope.row.admin] }}</el-tag>
          <span>&nbsp;&nbsp;</span>
          <el-tag v-if="judgeCurrentUserRole(scope.row.id)" type="warning" disable-transitions>当前</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="right">
        <template #header>
          <el-input v-model="search" style="width: 8.5vw" placeholder="查询用户名、手机号" clearable>
            <template #prefix="">
              <el-icon><Search /></el-icon>
            </template>
          </el-input>
        </template>
        <template #default="scope">
        <!--          非管理员-->
          <div v-if="!judgeCurrentUserRole(scope.row.id)">
          <el-button size="small" type="primary" text @click="handleSetAdmin(scope.$index, scope.row)">授权</el-button>
          <el-button size="small" type="danger" text @click="handleResetAdmin(scope.$index, scope.row)">取消</el-button>
          </div>
        <!--          管理员-->
          <div v-else>
            <el-button size="small" type="primary" text disabled @click="handleSetAdmin(scope.$index, scope.row)">授权</el-button>
            <el-button size="small" type="danger" text disabled @click="handleResetAdmin(scope.$index, scope.row)">取消</el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup>
import {computed, ref} from 'vue'
import {localGet} from "../../utils";
import { setAdmin, resetAdmin, getUserList } from "../../api/auth";
import {ElMessage} from "element-plus";

// tag标签数据
const rolePad = ref({
  "0": "普通",
  "1": "管理员"
})

// 判断是否为当前管理员
const judgeCurrentUserRole = (curId) => {
  return curId === localGet("userinfo").id
}

// 表格数据
const tableData = ref([])
const table_loading = ref(true)
// 后端获取用户列表
const handleGetUserList = () => {
  getUserList().then(res => {
    console.log(res)
    tableData.value = res
    table_loading.value = false
  })
}
// 页面加载时刷新
handleGetUserList()

// 模糊搜索
const search = ref('')
const filterTableData = computed(() =>
    tableData.value.filter(
        (data) =>
            !search.value ||
            data.username.toLowerCase().includes(search.value.toLowerCase()) || //搜索姓名
            data.phone.toLowerCase().includes(search.value.toLowerCase()) // 搜索电话号码
    )
)

// 授权
const handleSetAdmin = (index, row) => {
  setAdmin(row.phone, 1).then( res => {
    ElMessage.success(`授权<${row.username}>为管理员成功`)
    handleGetUserList()
  })
}

// 取消授权
const handleResetAdmin = (index, row) => {
  resetAdmin(row.phone, 0).then( res => {
    ElMessage.success(`取消授权<${row.username}>成功`)
    handleGetUserList()
  })
}
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>