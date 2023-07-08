<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <div style="width: 50vw">
          <span style="float:left; line-height: 100%"><b>商品{{ "(" + filterTableData.length +")" + "个" }}:</b>&nbsp;&nbsp;&nbsp;</span>
          <el-checkbox-group v-model="checkList">
            <el-checkbox v-for="cb in cbox" :label="cb.label" :disabled="cb.disabled" :key="cb.id"></el-checkbox>
          </el-checkbox-group>
          <div style="vertical-align: baseline">
            <span><b>上架时间区间:&nbsp;&nbsp;&nbsp;</b></span>
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
            <span><b>商品价格区间:&nbsp;&nbsp;&nbsp;</b></span>
            <el-input v-model="priceStart" class="w-50 m-2" placeholder="最低价格" style="width: 9vw"/>
            -
            <el-input v-model="priceEnd" class="w-50 m-2" placeholder="最高价格" style="width: 9vw"/>
          </div>
        </div>
        <el-button type="primary" @click="onClickAddProduct">上架新商品</el-button>
      </div>
    </template>
    <el-table :data="filterTableData"  stripe style="width: 100%" v-loading="table_loading" height="69vh">
      <el-table-column label="商品id" prop="id" sortable />
      <el-table-column label="商品图">
        <template #default="scope">
          <div style="display: flex; align-items: center; width: 80%">
            <el-avatar v-if="scope.row.photo" :src="scope.row.photo" shape="square" fit="fill" :size="200"/>
            <el-avatar v-else shape="square" fit="fill">藏品</el-avatar>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="商品名" prop="name" />
      <el-table-column label="商品描述" prop="introduction"></el-table-column>
      <el-table-column label="上架时间" prop="launchTime"></el-table-column>
      <el-table-column label="商品价格" prop="price">
      </el-table-column>
      <el-table-column label="库存状态" prop="storage">
      <template #default="scope">
        <el-tag v-if="scope.row.storage === 0" type="danger">无库存</el-tag>
        <el-tag v-else>有库存</el-tag>
      </template>
    </el-table-column>
      <el-table-column label="库存" prop="storage" />
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
                  <el-dropdown-item @click="updateProductInfo(scope.row)">更新商品信息</el-dropdown-item>
                  <el-dropdown-item divided style="color: firebrick" @click="deleteProduct(scope.row)">删除商品</el-dropdown-item>
                </el-dropdown-menu>
              </div>
            </template>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
  <el-dialog
      v-model="centerDialogVisible"
      title="上架新商品"
      width="50%"
  >
    <el-form :model="newProductData" label-width="120px">
      <el-form-item label="商品名称">
        <el-input v-model="newProductData.name" />
      </el-form-item>
      <el-form-item label="商品价格">
        <el-input v-model="newProductData.price" />
      </el-form-item>
      <el-form-item label="商品图片">
        <el-input v-model="newProductData.photo" />
        <div class="demo-image__preview">
          <el-image
              style="width: 150px; height: 150px; margin-top: 10px"
              :src="updateProductData.photo"
              :zoom-rate="1.2"
              :preview-src-list="[updateProductData.photo]"
              :initial-index="4"
              fit="cover"
          />
          (若链接有效，则可点击图片进行预览)
        </div>
      </el-form-item>
      <el-form-item label="商品介绍">
        <el-input v-model="newProductData.introduction"/>
      </el-form-item>

      <el-form-item label="商品库存">
        <el-input-number v-model="newProductData.storage" :min="0" :max="99" />
      </el-form-item>

    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="cancelAdd">返回</el-button>
        <el-button type="primary" @click="add_Product">
          确认添加
        </el-button>
      </span>
    </template>
  </el-dialog>
<!--  更新-->
  <el-dialog
      v-model="updateDialogVisable"
      title="上架新商品"
      width="50%"
  >
    <el-form :model="updateProductData" label-width="120px">
      <el-form-item label="商品名称">
        <el-input v-model="updateProductData.name" />
      </el-form-item>
      <el-form-item label="商品价格">
        <el-input-number v-model="updateProductData.price" :precision="2"/>
      </el-form-item>
      <el-form-item label="商品图片">
        <el-input v-model="updateProductData.photo" />
        <div class="demo-image__preview">
          <el-image
              style="width: 150px; height: 150px; margin-top: 10px"
              :src="updateProductData.photo"
              :zoom-rate="1.2"
              :preview-src-list="[updateProductData.photo]"
              :initial-index="4"
              fit="cover"
          />
          (若链接有效，则可点击图片进行预览)
        </div>
      </el-form-item>
      <el-form-item label="商品介绍">
        <el-input v-model="updateProductData.introduction" autosize type="textarea"/>
      </el-form-item>

      <el-form-item label="商品库存">
        <el-input-number v-model="updateProductData.storage" :min="0" :max="99" />
      </el-form-item>

    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="cancelUpdate">返回</el-button>
        <el-button type="primary" @click="confirmUpdateProductInfo">
          确认修改
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import {computed, reactive, ref, watch} from 'vue'
import {ArrowDown} from '@element-plus/icons-vue'
import {
  addProductByAdmin,
  deleteProductByAdmin,
  getAllProductByAdmin,
  updateProductNumberByAdmin
} from "../../api/product";


const dateRange = ref([])
const priceStart = ref()
const priceEnd = ref()

// 表格数据
const tableData = ref([])
const table_loading = ref(true)
// 后端获取藏品列表
const handleGetProductList = async () => {
  const res = await getAllProductByAdmin()
  table_loading.value = false
  tableData.value = res
  console.log(res)
}
// 页面加载时刷新
handleGetProductList()


// 点击上架新商品按钮
const centerDialogVisible = ref(false)
const onClickAddProduct = () => centerDialogVisible.value = true

// 弹窗逻辑
const newProductData = reactive({
  name: "",
  price: "",
  photo: "",
  introduction: "",
  storage: 0
})
// update info
const updateProductData = reactive({
  id: "",
  name: "",
  price: "",
  photo: "",
  introduction: "",
  storage: 0,
})
const updateDialogVisable = ref(false)
const updateProductInfo = function (product) {
  // popout
  updateDialogVisable.value = true
  // data
  updateProductData.id = product.id
  updateProductData.name = product.name
  updateProductData.price = product.price
  updateProductData.photo = product.photo
  updateProductData.introduction = product.introduction
  updateProductData.storage = product.storage
}
const confirmUpdateProductInfo = async function () {
  console.log("confirm")

  await updateProductNumberByAdmin(
      updateProductData.id,
      updateProductData.name,
      String(updateProductData.price),
      updateProductData.photo.replace('https://cdt-1309988842.cos.ap-beijing.myqcloud.com/', ''),
      updateProductData.introduction,
      updateProductData.storage,
  )

  await handleGetProductList()
  cancelUpdate()
}
const cancelUpdate = function () {
  updateProductData.id = ""
  updateProductData.name = ""
  updateProductData.price = ""
  updateProductData.photo = ""
  updateProductData.introduction = ""
  updateProductData.storage = 0
  updateDialogVisable.value = false
}






// 弹窗取消
const cancelAdd = () => {
  newProductData.name = ""
  newProductData.price = ""
  newProductData.photo = ""
  newProductData.introduction = ""
  newProductData.storage = 0
  centerDialogVisible.value = false
}
// 新增商品
const add_Product = async () => {
  await addProductByAdmin(
      newProductData.name,
      newProductData.price,
      newProductData.photo,
      newProductData.introduction,
      newProductData.storage
  )
  await handleGetProductList()
  cancelAdd()
}
// 删除商品
const deleteProduct = async (product) => {
  await deleteProductByAdmin(product.id)
  await handleGetProductList()
}


// 多选框逻辑
const checkList = ref(['全部', "有库存", "无库存"])
const cbox = reactive([
  {"id": 1, "label": "全部", "disabled": false},
  {"id": 2, "label": "有库存", "disabled": false},
  {"id": 3, "label": "无库存", "disabled": false},
])

watch(checkList, (n, o) => {
  if (o.includes("全部") && !n.includes("全部")) {
    checkList.value = []
  }
  if (!o.includes("全部") && n.includes("全部")) {
    checkList.value = ['全部', "有库存", "无库存"]
  }
  if (n.length !== 3) {
    const index = checkList.value.indexOf("全部")
    if (index > -1) {
      checkList.value.splice(index, 1)
    }
  }
  if (n.length === 2 && checkList.value.indexOf("全部") === -1 && o.length !== 3) {
    checkList.value = ['全部', "有库存", "无库存"]
  }
})


// 模糊搜索与筛选
const search = ref('')
const filterTableData = computed(() => {

  const tData = [...checkList.value]
  let res = ref([...tableData.value])
  if (tData.indexOf("全部") === -1) {

    console.log("需要筛选")
    res.value = []
    for (let t of tData) {
      if (t === "有库存") {
        res.value.push(...tableData.value.filter(data => data.storage > 0))
      }
      else if(t === "无库存") {
        res.value.push(...tableData.value.filter(data => data.storage === 0))
      }
    }
  }
  // 日期筛选
  if (dateRange.value.length) {
    console.log("需要日期")
    let std = new Date(dateRange.value[0])
    let edd = new Date(dateRange.value[1])

    res.value = res.value.filter(data => {
      let rld = new Date(data.launchTime)
      return std <= rld && rld <= edd
    })
  }

  // 价格筛选
  if (priceStart.value && priceEnd.value) {
    if (priceStart.value < priceEnd.value) {
      res.value = res.value.filter(data => data.price >= priceStart.value && data.price <= priceEnd.value)
    }
  }

  return res.value.filter(
      (data) =>
          !search.value ||
          data.title.toLowerCase().includes(search.value.toLowerCase()) //搜索藏品名
  )
})

</script>

<style scoped>
.dialog-footer button:first-child {
  margin-right: 10px;
}
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
</style>