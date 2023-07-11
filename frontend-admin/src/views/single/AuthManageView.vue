<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <span>授权用户为管理员</span>
      </div>
    </template>
    <el-table :data="pageData" stripe style="width: 100%" v-loading="table_loading" height="70vh">
      <el-table-column label="id" prop="id" />
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
      <el-table-column label="权限等级" prop="admin" :rolepad = "rolePad">
        <template #default="scope">
          <el-tag :type="scope.row.admin === '2' ? 'success' : scope.row.admin === '1' ? 'danger' : '' " disable-transitions>{{ rolePad[scope.row.admin] }}</el-tag>
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
        <!--          其他人-->
          <div v-if="!judgeCurrentUserRole(scope.row.id) ">
            <div v-if="sessionGet('userinfo').admin === '2'">
              <el-button size="small" type="primary" text @click="handleSetAdmin(scope.$index, scope.row)" :disabled="scope.row.admin === '1'">授权</el-button>
              <el-button size="small" type="danger" text @click="handleResetAdmin(scope.$index, scope.row)" :disabled="scope.row.admin === '0'">取消</el-button>
            </div>
            <div v-else>
              <div v-if="sessionGet('userinfo').admin === '1' && ((scope.row.admin === '1') || (scope.row.admin === '2'))">
                无授权权限
              </div>
              <div v-else>
                无授权权限
              </div>
            </div>
          </div>

        <!--          我自己-->
          <div v-else>
            <div>无法授权自己</div>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="sizes, prev, pager, next"
                   :total="totalPage"
                   :page-sizes="[10, 20, 50]"
                   v-model:current-page="curPage"
                   v-model:page-size="pageSize"
                   @size-change="handleSizeChange"
    />
  </el-card>
</template>

<script setup>
import {computed, ref} from 'vue'
import {sessionGet} from "../../utils";
import { setAdmin, resetAdmin, getUserList } from "../../api/auth";
import {ElMessage, ElMessageBox} from "element-plus";
import {deleteProductByAdmin} from "../../api/product";


const pageSize = ref(20)
const curPage = ref(1)
const totalPage = ref(1)
const handleSizeChange = function (val) {
  pageSize.value = val
}
// tag标签数据
const rolePad = ref({
  "0": "普通",
  "1": "管理员",
  "2": "超级管理员"
})

// 判断是否为当前管理员
const judgeCurrentUserRole = (curId) => {
  return curId === sessionGet("userinfo").id
}

// 表格数据
const tableData = ref([])
const table_loading = ref(true)
// 后端获取用户列表
const handleGetUserList = () => {
  getUserList().then(res => {
    console.log(res)
    tableData.value = res
    totalPage.value = tableData.value.length
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
  ElMessageBox.confirm(`确认授权用户<${row.username}>为管理员吗？`, '警告！', {
    distinguishCancelAndClose: true,
    confirmButtonText: '确认',
    cancelButtonText: '返回',
  }).then(()=> {
    setAdmin(row.phone, 1).then( res => {
      ElMessage.success(`授权<${row.username}>为管理员成功`)
      handleGetUserList()
    })
  }).catch(() => {

  })
}

// 取消授权
const handleResetAdmin = (index, row) => {
  ElMessageBox.confirm(`确认取消用户<${row.username}>的管理员身份吗？`, '警告！', {
    distinguishCancelAndClose: true,
    confirmButtonText: '确认',
    cancelButtonText: '返回',
  }).then(()=> {
    resetAdmin(row.phone, 0).then( res => {
      ElMessage.success(`取消授权<${row.username}>成功`)
      handleGetUserList()
    })
  }).catch(() => {

  })
}

const pageData = computed(() => {
  console.log(totalPage, "t")
  console.log(totalPage.value, "2")
  console.log(filterTableData.value.slice((curPage.value - 1) * 20, curPage.value * 20 > totalPage.value ? totalPage.value : curPage.value));
  return filterTableData.value.slice((curPage.value-1)*pageSize.value, curPage.value*pageSize.value > totalPage.value ? totalPage.value : curPage.value*pageSize.value)
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>