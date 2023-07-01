<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <el-select v-model="filterOption" class="m-2" placeholder="全部" size="large" @change="handleFilterChange">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </div>
    </template>
    <el-table :data="filterTableData" :default-sort="{ prop: 'admin', order: 'descending' }" stripe style="width: 100%" v-loading="tableLoading">
      <el-table-column label="用户id" prop="userId" sortable />
      <el-table-column label="预约人" prop="subscriber" />
      <el-table-column label="预约时间" prop="date" />
      <el-table-column label="预约人数" prop="number" />
      <el-table-column label="创建时间" prop="orderTime" />
      <el-table-column label="手机号" prop="phone" />
      <el-table-column label="订单金额" prop="orderMoney" :rolepad = "rolePad" />
      <el-table-column label="订单状态" prop="status">
        <template #default="scope">
          <el-tag :type="rolePadTag[scope.row.status]" disable-transitions>{{ rolePad[scope.row.status] }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="right">
        <template #header>
          <el-input v-model="search" placeholder="查询" clearable>
            <template #prefix="">
              <el-icon><Search /></el-icon>
            </template>
          </el-input>
        </template>
        <template #default="scope">
          <el-button size="small" type="primary" text @click="handleAuditOp(scope.row)">操作</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
  <el-dialog v-model="centerDialogVisible" title="确认你的操作"  center >
    <el-descriptions
        class="margin-top"
        title="用户预约信息"
        :column="2"
        :size="size"
        border
    >
      <template #extra>
        <el-tag size="large" :type="rolePadTag[dialogText.status]" disable-transitions>{{ rolePad[dialogText.status] }}</el-tag>
      </template>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <iphone />
            </el-icon>
            预约人
          </div>
        </template>
        {{ dialogText.subscriber }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <tickets />
            </el-icon>
            预约人数
          </div>
        </template>
        {{ dialogText.number }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <office-building />
            </el-icon>
            创建时间
          </div>
        </template>
        {{ dialogText.date }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <location />
            </el-icon>
            预约时间
          </div>
        </template>
        {{ dialogText.orderTime }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <office-building />
            </el-icon>
            手机号
          </div>
        </template>
        {{ dialogText.phone }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <office-building />
            </el-icon>
            订单金额
          </div>
        </template>
        ¥{{ dialogText.orderMoney === null ? "0.00" : dialogText.orderMoney }}
      </el-descriptions-item>
    </el-descriptions>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="centerDialogVisible = false">返回列表</el-button>
        <el-button type="danger" @click="handleRejectEvent">
          拒绝预约
        </el-button>
        <el-button type="primary" @click="handlePassEvent">
          通过预约
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { computed, ref } from 'vue'
import {auditEvent, getAuditEventList} from "../../api/home";
import { ElMessage } from "element-plus";

// 弹出框样式
const size = ref('default')
const iconStyle = computed(() => {
  const marginMap = {
    large: '8px',
    default: '6px',
    small: '4px',
  }
  return {
    marginRight: marginMap[size.value] || marginMap.default,
  }
})
// 控制是否弹出
const centerDialogVisible = ref(false)

// 筛选框
const filterOption = ref('')
const options = [
  {
    value: '',
    label: '全部',
  },
  {
    value: 'SUCCESS',
    label: '审核成功',
  },
  {
    value: 'PROCESSING',
    label: '待审核',
  },
  {
    value: 'FAIL',
    label: '审核失败'
  }
]
const handleFilterChange = val => {
  filterOption.value = val;
  handleGetUserList();
}

// tag标签数据
const rolePad = ref({
  "PROCESSING": "待审核",
  "SUCCESS": "审核完成",
  "FAIL": "审核失败"
})
const rolePadTag = ref({
  "PROCESSING": "warning",
  "SUCCESS": "",
  "FAIL": "danger"
})

// 表格数据
const tableData = ref([])
const tableLoading = ref(true)
// 后端获取用户列表
const handleGetUserList = () => {
  getAuditEventList(filterOption.value).then( res => {
    tableData.value = res;
    tableLoading.value = false
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
            data.subscriber.toLowerCase().includes(search.value.toLowerCase()) || //搜索姓名
            data.phone.toLowerCase().includes(search.value.toLowerCase()) // 搜索电话号码
    )
)

// 弹出框数据
const dialogText = ref('')
const handleAuditOp = obj => {
  centerDialogVisible.value = true
  dialogText.value = obj
}

// 不通过审核
const handleRejectEvent = () => {
  auditEvent(dialogText.value.id, "FAIL").then(res => {
    ElMessage.warning('<拒绝审核>操作成功')
    centerDialogVisible.value = false
    handleGetUserList()
  })
}

// 通过审核
const handlePassEvent = () => {
  auditEvent(dialogText.value.id, "SUCCESS").then(res => {
    ElMessage.success('<通过审核>操作成功')
    centerDialogVisible.value = false
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
.dialog-footer button:first-child {
  margin-right: 10px;
}
.cell-item {
  display: flex;
  align-items: center;
}
.margin-top {
  margin-top: 10px;
}
</style>