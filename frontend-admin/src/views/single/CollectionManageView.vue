<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <div>
          <span>藏品{{ "(" + filterTableData.length +")" + "个" }}</span>
        </div>
        <el-button type="primary">导入新藏品</el-button>
      </div>
    </template>
    <el-table :data="filterTableData"  stripe style="width: 100%" v-loading="table_loading">
      <el-table-column label="藏品id" prop="id" sortable />
      <el-table-column label="藏品图">
        <template #default="scope">
          <div style="display: flex; align-items: center">
            <el-avatar v-if="scope.row.photo" :src="scope.row.photo" shape="square" fit="fill"/>
            <el-avatar v-else shape="square" fit="fill">藏品</el-avatar>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="藏品名" prop="title" />
      <el-table-column label="藏品介绍页">
        <template #default="scope">
          <el-link :href="scope.row.url" target="_blank">{{ scope.row.url }}</el-link>
        </template>
      </el-table-column>
      <el-table-column label="藏品类别" prop="type">
        <template #default="scope">
          <el-tag :type="val2tag[scope.row.type][1]">
            {{ val2tag[scope.row.type][0] }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="是否为精选" prop="selected" />
      <el-table-column label="被用户收藏数" prop="userCollect" />
      <el-table-column align="right">
        <template #header>
          <el-input v-model="search" placeholder="查询" clearable>
            <template #prefix="">
              <el-icon><Search /></el-icon>
            </template>
          </el-input>
        </template>
        <template #default="scope">
          <el-dropdown>
            <span class="el-dropdown-link">
              操作
              <el-icon class="el-icon--right">
                <arrow-down />
              </el-icon>
            </span>
            <template #dropdown>
              <div>
                <el-dropdown-menu>
                  <el-dropdown-item>更新藏品信息</el-dropdown-item>
                  <el-dropdown-item divided>设置精选</el-dropdown-item>
                  <el-dropdown-item>取消精选</el-dropdown-item>
                  <el-dropdown-item divided style="color: firebrick">删除藏品</el-dropdown-item>
                </el-dropdown-menu>
              </div>
            </template>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup>
import {computed, ref} from 'vue'
import {localGet} from "../../utils";
import { setAdmin, resetAdmin, getUserList } from "../../api/auth";
import { ArrowDown } from '@element-plus/icons-vue'
import {getAllCollectionByAdmin} from "../../api/collection";

// tag数据
const val2tag = {
  "sf": ["书法", "primary"],
  "pb": ["牌匾", "warning"],
  "zk": ["篆刻", "danger"]
}
// 表格数据
const tableData = ref([])
const table_loading = ref(true)
// 后端获取藏品列表
const handleGetCollectionList = async () => {
  const res = await getAllCollectionByAdmin()
  table_loading.value = false
  tableData.value = res
  console.log(res)
}
// 页面加载时刷新
handleGetCollectionList()

// 模糊搜索
const search = ref('')
const filterTableData = computed(() =>
    tableData.value.filter(
        (data) =>
            !search.value ||
            data.title.toLowerCase().includes(search.value.toLowerCase()) //搜索藏品名
    )
)

</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.el-dropdown-link{
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
.el-dropdown-link:focus {outline: 0;}
.example-showcase {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
.demo-image__error .image-slot {
  font-size: 30px;
}
.demo-image__error .image-slot .el-icon {
  font-size: 30px;
}
.demo-image__error .el-image {
  width: 100%;
  height: 200px;
}
.demo-image__preview {
  z-index: 9;
}
</style>