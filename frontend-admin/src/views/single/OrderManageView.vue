<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
<!--        <el-select v-model="filterOption" class="m-2" placeholder="全部" size="large" @change="handleFilterChange">-->
<!--          <el-option-->
<!--              v-for="item in options"-->
<!--              :key="item.value"-->
<!--              :label="item.label"-->
<!--              :value="item.value"-->
<!--          />-->
<!--        </el-select>-->
        <div style="width: 50vw">
          <span style="float:left; line-height: 100%"><b>订单{{ "(" + filterTableData.length +")" + "个" }}:</b>&nbsp;&nbsp;&nbsp;</span>
          <el-checkbox-group v-model="checkList">
            <el-checkbox v-for="cb in cbox" :label="cb.label" :disabled="cb.disabled" :key="cb.id"></el-checkbox>
          </el-checkbox-group>
          <div style="vertical-align: baseline">
            <span><b>下单时间区间:&nbsp;&nbsp;&nbsp;</b></span>
            <el-date-picker
                v-model="dateRange"
                type="daterange"
                range-separator="To"
                start-placeholder="最早日期"
                end-placeholder="最晚日期"
                size="default"
            />
          </div>
          <div style="margin-top: 5px">
            <span><b>订单总价区间:&nbsp;&nbsp;&nbsp;</b></span>
<!--            <el-input v-model="priceStart" class="w-50 m-2" placeholder="最低价格" style="width: 9vw"/>-->
            <el-input-number v-model="priceStart" :precision="2" :step="0.1" class="w-50 m-2" placeholder="最低价格" style="width: 9vw" :min="0"/>
            -
<!--            <el-input v-model="priceEnd" class="w-50 m-2" placeholder="最高价格" style="width: 9vw"/>-->
            <el-input-number v-model="priceEnd" :precision="2" :step="0.1"  class="w-50 m-2" placeholder="最高价格" style="width: 9vw" :min="0"/>
            &nbsp;&nbsp;&nbsp;
            <el-button :icon="RefreshLeft" size="small" @click="onClickResetPrice">重置价格区间</el-button>
          </div>
        </div>
      </div>
    </template>
    <el-table :data="pageData" :default-sort="{ prop: 'admin', order: 'descending' }" stripe style="width: 100%" v-loading="tableLoading" height="62vh">
      <el-table-column label="订单id" prop="id" />
      <el-table-column label="下单用户" prop="userId" />
      <el-table-column label="下单时间" prop="orderDate" />
      <el-table-column label="订单状态">
        <template #default="scope">
          <el-tag :type="tagStyle[scope.row.status]">{{ orderStatus[scope.row.status] }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="收获地址">
        <template #default="scope">
          {{ scope.row.address.province + '-' + scope.row.address.city + '-' + scope.row.address.detail }}
        </template>
      </el-table-column>
      <el-table-column label="收货人">
        <template #default="scope">
          {{ scope.row.address.consignee }}
        </template>
      </el-table-column>

      <el-table-column label="订单金额(元)">
        <template #default="scope">
          {{ getOrderMoney(scope.row) }}
        </template>
      </el-table-column>

      <el-table-column align="right">
        <template #header>
          <el-input v-model="search" placeholder="查询收货人" clearable>
            <template #prefix="">
              <el-icon><Search /></el-icon>
            </template>
          </el-input>
        </template>
        <template #default="scope">
          <el-button size="small" type="primary" text @click="handleAuditOp(scope.row)">查看详情</el-button>
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
  <el-dialog v-model="centerDialogVisible" title="订单详情信息"  center style="width: 70vw">
    <el-descriptions
        class="margin-top"
        title="收货信息"
        :column="2"
        :size="size"
        border
    >
      <template #extra>
        <el-tag size="large" :type="tagStyle[dialogText.status]" disable-transitions>{{ '【订单状态】' + orderStatus[dialogText.status] }}</el-tag>
      </template>

      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <tickets />
            </el-icon>
            收货人
          </div>
        </template>
        {{ dialogText.address.consignee }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <office-building />
            </el-icon>
            联系方式
          </div>
        </template>
        {{ dialogText.address.phone }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <iphone />
            </el-icon>
            收货地址
          </div>
        </template>
        {{ dialogText.address.province + '-' + dialogText.address.city + '-' + dialogText.address.detail }}
      </el-descriptions-item>
    </el-descriptions>
<!--    订单-->
    <div style="max-height: 30vh; overflow: scroll">
    <el-descriptions
        class="margin-top"
        :title="index === 0 ? (dialogText.shoppings.length > 2) ? '订单信息(显示不全，请向下滚动)' : '订单信息' : null"
        :column="2"
        :size="size"
        border
        v-for="(shopping, index) in dialogText.shoppings"
        :key="shopping.id"
    >
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <tickets />
            </el-icon>
            商品名称
          </div>
        </template>
        {{ shopping.product.name }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <office-building />
            </el-icon>
            商品图片(点击预览)
          </div>
        </template>
        <div class="demo-image__preview">
          <el-image
              style="width: 50px; height: 50px; margin-top: 10px"
              :src="photoPrefix + shopping.product.photo"
              :zoom-rate="1.2"
              :preview-src-list="[photoPrefix + shopping.product.photo]"
              :initial-index="4"
              fit="fill"
          />
        </div>
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <office-building />
            </el-icon>
            商品单价
          </div>
        </template>
        {{ shopping.product.price }}
      </el-descriptions-item>

      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <iphone />
            </el-icon>
            商品数量
          </div>
        </template>
        {{ shopping.number }}
      </el-descriptions-item>
    </el-descriptions>
    </div>
    <el-descriptions
        class="margin-top"
        :title="'结算'"
        :column="1"
        :size="size"
        border
    >
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <tickets />
            </el-icon>
            订单总价
          </div>
        </template>
        {{ getOrderTotal() }}
      </el-descriptions-item>
      </el-descriptions>

    <template #footer>

      <span class="dialog-footer">
        <el-button @click="centerDialogVisible = false">返回列表</el-button>
        修改订单状态:
        <el-button type="danger" @click="handleEvent('FAIL')" :disabled="btnDisabled.fail">
          用户已取消，订单结束
        </el-button>
        <el-button type="warning" @click="handleEvent('PROCESSING')" :disabled="btnDisabled.processing">
          仓库已发货，订单进行中
        </el-button>
        <el-button type="primary" @click="handleEvent('SUCCESS')" :disabled="btnDisabled.success">
          用户已收货，订单完成
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import {computed, reactive, ref, watch} from 'vue'
import {auditEvent, getAuditEventList} from "../../api/home";
import {ElMessage, ElMessageBox} from "element-plus";
import {changeOrderStatus, getOrdersByAdmin} from "../../api/order";
import {photoPrefix} from "../../../config/app-key";
import { RefreshLeft } from '@element-plus/icons-vue'
import {deleteProductByAdmin} from "../../api/product";


const pageSize = ref(20)
const curPage = ref(1)
const totalPage = ref(1)
const handleSizeChange = function (val) {
  pageSize.value = val
}

const onClickResetPrice = function () {
  priceStart.value = undefined
  priceEnd.value = undefined
}

const getOrderMoney = (od) => {
  let res = 0
  od.shoppings.map(x => {
    res += x.product.price * x.number
  })
  return res
}
const tagStyle = {
  "PROCESSING": "warning",
  "SUCCESS": "",
  "FAIL": "danger"
}
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

// 订单状态
const orderStatus = {
  "PROCESSING": "进行中",
  "SUCCESS": "已完成",
  "FAIL": "已取消"
}
// 筛选框
const filterOption = ref('')
const options = [
  {
    value: '',
    label: '全部',
  },
  {
    value: 'SUCCESS',
    label: '订单已完成',
  },
  {
    value: 'PROCESSING',
    label: '订单进行中',
  },
  {
    value: 'FAIL',
    label: '订单失败'
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
const handleGetUserList = async () => {
  const res = await getOrdersByAdmin()
  console.log(res)
  tableData.value = res;
  tableData.value.sort((a, b) => {
    const aa = new Date(a.orderDate)
    const bb = new Date(b.orderDate)

    if (aa.getTime() === bb.getTime()) {
      return getOrderMoney(b) - getOrderMoney(a)
    }
    return bb -aa
  })
  totalPage.value = tableData.value.length
  tableLoading.value = false
}
// 页面加载时刷新
handleGetUserList()

// 多选框逻辑
const checkList = ref(['全部', "进行中", "已完成", "已取消"])
const cbox = reactive([
  {"id": 1, "label": "全部", "disabled": false},
  {"id": 2, "label": "进行中", "disabled": false},
  {"id": 3, "label": "已完成", "disabled": false},
  {"id": 4, "label": "已取消", "disabled": false}
])
const dateRange = ref([])
const priceStart = ref()
const priceEnd = ref()
watch(checkList, (n, o) => {
  if (o.includes("全部") && !n.includes("全部")) {
    checkList.value = []
  }
  if (!o.includes("全部") && n.includes("全部")) {
    checkList.value = ['全部', "进行中", "已完成", "已取消"]
  }
  if (n.length !== 4) {
    const index = checkList.value.indexOf("全部")
    if (index > -1) {
      checkList.value.splice(index, 1)
    }
  }
  if (n.length === 3 && checkList.value.indexOf("全部") === -1 && o.length !== 4) {
    checkList.value = ['全部', "进行中", "已完成", "已取消"]
  }
})



// 模糊搜索
const search = ref('')
const filterTableData = computed(() => {
  const tData = [...checkList.value]
  let res = ref([...tableData.value])
  if (tData.indexOf("全部") === -1) {

    console.log("需要筛选")
    res.value = []
    for (let t of tData) {
      if (t === "进行中") {
        res.value.push(...tableData.value.filter(data => data.status === 'PROCESSING'))
      }
      else if(t === "已完成") {
        res.value.push(...tableData.value.filter(data => data.status === 'SUCCESS'))
      }
      else if(t === "已取消") {
        res.value.push(...tableData.value.filter(data => data.status === 'FAIL'))
      }
    }
  }
  // 日期筛选
  if (dateRange.value && dateRange.value.length) {
    console.log("需要日期")
    let std = new Date(dateRange.value[0])
    let edd = new Date(dateRange.value[1])

    res.value = res.value.filter(data => {
      let rld = new Date(data.orderDate)
      return std <= rld && rld <= edd
    })
  }
      console.log("price", priceStart.value, priceEnd.value)
  // 价格筛选
  if (priceStart.value !== undefined && priceEnd.value !== undefined) {
    // priceStart.value = Number(priceStart.value)
    // priceEnd.value = Number(priceEnd.value)
    if (priceStart.value <= priceEnd.value) {
      res.value = res.value.filter(data => {
        console.log("价格筛选", getOrderMoney(data), priceStart.value)
        return getOrderMoney(data) >= priceStart.value && getOrderMoney(data) <= priceEnd.value
      })
    }
    else {
      res.value = []
    }
  }

  return res.value.filter(
      (data) =>
          !search.value ||
          data.address.consignee.toLowerCase().includes(search.value.toLowerCase()) //搜索姓名
  )

}

)

// 弹出框数据
const dialogText = ref({})
const btnDisabled = {
  success: true,
  processing: true,
  fail: true
}
const handleAuditOp = obj => {
  console.log('pop', obj)
  centerDialogVisible.value = true
  dialogText.value = obj
  btnDisabled.success = (dialogText.value.status === 'SUCCESS')
  btnDisabled.processing = (dialogText.value.status === 'PROCESSING')
  btnDisabled.fail = (dialogText.value.status === 'FAIL')
  console.log(dialogText.value)
}

// 修改状态
const handleEvent = async (t) => {
  if (t === "FAIL") {
    ElMessageBox.confirm(`确认更新订单状态为<取消订单>吗？`, '警告！', {
      distinguishCancelAndClose: true,
      confirmButtonText: '确认',
      cancelButtonText: '返回',
    }).then(()=> {
      const f = async function () {
        await changeOrderStatus(dialogText.value.id, t)
        await handleGetUserList()
        ElMessage.success(`更新状态成功：订单${dialogText.value.id}`)
      }
      f()
    }).catch(() => {

    })
  }
  else {
    console.log('change', dialogText.value.id)
    await changeOrderStatus(dialogText.value.id, t)
    await handleGetUserList()
    ElMessage.success(`更新状态成功：订单${dialogText.value.id}`)
  }
  centerDialogVisible.value = false
}

// 计算订单总价
const getOrderTotal = function () {
  return dialogText.value.shoppings.reduce((total, cur) => {
    console.log(cur)
    return total + cur.number * cur.product.price
  }, 0)
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