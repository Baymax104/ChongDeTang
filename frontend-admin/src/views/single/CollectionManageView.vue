<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <div style="width: 50vw">
          <span style="float:left; line-height: 100%"><b>藏品{{ "(" + filterTableData.length +")" + "个" }}:</b>&nbsp;&nbsp;&nbsp;</span>
            <el-checkbox-group v-model="checkList">
              <el-checkbox v-for="cb in cbox" :label="cb.label" :disabled="cb.disabled" :key="cb.id"></el-checkbox>
            </el-checkbox-group>
          <div style="vertical-align: baseline">
            <span><b>只看精选:&nbsp;&nbsp;&nbsp;</b></span>
            <el-switch
                v-model="onlySelected"
                active-text="开启"
                inactive-text="关闭"
                size="default"
                :loading="onlySelectedBtnLoading"
            />
            &nbsp;&nbsp;&nbsp;
            <el-button :icon="Position" size="small" @click="onClickGotoVisual">可视化设置精选藏品</el-button>
          </div>
        </div>
        <el-button type="primary" @click="onClickAddCollection">导入新藏品</el-button>
      </div>
    </template>
    <el-table :data="filterTableData"  stripe style="width: 100%" v-loading="table_loading" height="73vh">
      <el-table-column label="藏品id" prop="id" />
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
          <el-input v-model="search" placeholder="查询藏品名" clearable>
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
                  <el-dropdown-item @click="updateCollectionInfo(scope.row)">更新藏品信息</el-dropdown-item>
                  <el-dropdown-item divided @click="setSelected(scope.row)">设置精选</el-dropdown-item>
                  <el-dropdown-item @click="cancelSelected(scope.row)">取消精选</el-dropdown-item>
                  <el-dropdown-item divided style="color: firebrick" @click="deleteCollection(scope.row)">删除藏品</el-dropdown-item>
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
      title="添加新藏品"
      width="50%"
  >
    <el-form :model="colloec" label-width="120px">
      <el-form-item label="藏品标题">
        <el-input v-model="colloec.title" />
      </el-form-item>
      <el-form-item label="藏品介绍页链接">
        <el-input v-model="colloec.url" />
      </el-form-item>
      <el-form-item label="藏品图片链接">
        <input id="fileSelector" type="file" @change="uploadNew" accept=".jpg, .png"/>
        <div class="demo-image__preview">
          <el-image
              style="width: 150px; height: 150px; margin-top: 10px"
              :src="colloec.photo"
              :zoom-rate="1.2"
              :preview-src-list="[colloec.photo]"
              :initial-index="4"
              fit="fill"
          />
          (若图片有效，则可点击进行预览)
        </div>
      </el-form-item>
      <el-form-item label="藏品类别">
        <el-select v-model="colloec.type" placeholder="选择藏品类型">
          <el-option label="书法" value="sf" />
          <el-option label="牌匾" value="pb" />
          <el-option label="篆刻" value="zk" />
        </el-select>
      </el-form-item>

      <el-form-item label="是否设置精选">
        <el-switch v-model="colloec.selected" />
      </el-form-item>

    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="cancelAdd">返回</el-button>
        <el-button type="primary" @click="add_Collection">
          确认添加
        </el-button>
      </span>
    </template>
  </el-dialog>
<!--  更新-->
  <el-dialog
      v-model="updateDialogVisable"
      title="更改藏品信息"
      width="50%"
  >
    <el-form :model="updateCurrentInfo" label-width="120px">
      <el-form-item label="藏品id">
        <div>{{ updateCurrentInfo.id }}</div>
      </el-form-item>
      <el-form-item label="藏品标题">
        <el-input v-model="updateCurrentInfo.title" />
      </el-form-item>
      <el-form-item label="藏品介绍页链接">
        <el-input v-model="updateCurrentInfo.url" />
      </el-form-item>
      <el-form-item label="藏品图片链接">
        <input id="fileSelector2" type="file" @change="uploadUpdate" accept=".jpg, .png"/>
        <div class="demo-image__preview">
          <el-image
              style="width: 150px; height: 150px; margin-top: 10px"
              :src="updateCurrentInfo.photo"
              :zoom-rate="1.2"
              :preview-src-list="[updateCurrentInfo.photo]"
              :initial-index="4"
              fit="cover"

          />
          (若图片有效，则可点击进行预览)
        </div>
      </el-form-item>
      <el-form-item label="藏品类别">
        <el-select v-model="updateCurrentInfo.type" placeholder="选择藏品类型">
          <el-option label="书法" value="sf" />
          <el-option label="牌匾" value="pb" />
          <el-option label="篆刻" value="zk" />
        </el-select>
      </el-form-item>

<!--      <el-form-item label="是否设置精选">-->
<!--        <el-switch v-model="updateCurrentInfo.selected" />-->
<!--      </el-form-item>-->

      <el-form-item label="被收藏数">
        <div>{{ updateCurrentInfo.userCollect }} </div>
      </el-form-item>

    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="cancelUpdate">返回</el-button>
        <el-button type="primary" @click="confirmUpdateCollectionInfo">
          确认添加
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import {computed, onUpdated, reactive, ref, watch} from 'vue'
import { ArrowDown, Position } from '@element-plus/icons-vue'
import {
  addCollection,
  getAllCollectionByAdmin,
  removeCollectionByAdmin,
  updateCollectionByAdmin
} from "../../api/collection";
import {setHotList} from "../../api/home";
import {ElDialog, ElMessageBox} from "element-plus";
import router from "../../router";
import cos from "../../utils/cos";
import {cosConfig} from "../../../config/app-key";

// tag数据
const val2tag = {
  "sf": ["书法", ""],
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
  tableData.value.sort((a, b) => b.id - a.id)
  console.log(res)
}
// 页面加载时刷新
handleGetCollectionList()

const onClickGotoVisual = () => router.push('/collection_filtrate')
// update collection
const updateDialogVisable = ref(false)
const updateCurrentInfo = reactive({
  id: "",
  photo: "",
  selected: false,
  title: "",
  type: "",
  url: "",
  userCollect: ""
})
const updateCollectionInfo = async function (collection) {
  // popout
  updateDialogVisable.value = true
  // load info
  updateCurrentInfo.id = collection.id
  updateCurrentInfo.photo = collection.photo
  updateCurrentInfo.selected = collection.selected === "1"
  updateCurrentInfo.title = collection.title
  updateCurrentInfo.type = collection.type
  updateCurrentInfo.url = collection.url
  updateCurrentInfo.userCollect = collection.userCollect
}

const confirmUpdateCollectionInfo = async function () {
  // update
  await updateCollectionByAdmin(
      updateCurrentInfo.id,
      updateCurrentInfo.title,
      updateCurrentInfo.url,
      updateCurrentInfo.type,
      updateCurrentInfo.photo
  )
  // // update selected
  // if (updateCurrentInfo.selected) {
  //   await setHotList([updateCurrentInfo.id], 'right')
  // }
  // else {
  //   await setHotList([updateCurrentInfo.id], 'left')
  // }
  // close popout
  cancelUpdate()
  await handleGetCollectionList()
  console.log("更新")
}
const cancelUpdate = function () {
  updateCurrentInfo.id = ""
  updateCurrentInfo.photo = ""
  updateCurrentInfo.selected = false
  updateCurrentInfo.title = ""
  updateCurrentInfo.type = ""
  updateCurrentInfo.url = ""
  updateCurrentInfo.userCollect = ""
  updateDialogVisable.value = false
}

const setSelected = async function (collection) {
  await setHotList([collection.id], 'right')
  await handleGetCollectionList()
  console.log("精选", collection)
}

const cancelSelected = async function (collection) {
  await setHotList([collection.id], 'left')
  await handleGetCollectionList()
  console.log("取消", collection)
}

const deleteCollection = async function (collection) {
  ElMessageBox.confirm(`确认删除藏品<${collection.title}>吗？`, '警告！', {
    distinguishCancelAndClose: true,
    confirmButtonText: '确认',
    cancelButtonText: '返回',
  }).then(()=> {
    const f = async function() {
      await removeCollectionByAdmin(collection.id)
      await handleGetCollectionList()
      console.log("删除", collection)
    }
    f()
  }).catch(() => {

  })
}

const centerDialogVisible = ref(false)
const onClickAddCollection = () => centerDialogVisible.value = true

const colloec = reactive({
  title: "",
  url: "",
  type: "",
  photo: "",
  selected: 0
})
function randomString(length) {
  const chars = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_=-';
  let result = '';
  for (let i = length; i > 0; --i) {
    result += chars[Math.floor(Math.random() * chars.length)];
  }
  return result;
}


function uploadUpdate(e) {
  const uuid = randomString(64)
  const file = e.target.files && e.target.files[0];
  const fileExtension = e.target.files[0].name.slice(e.target.files[0].name.lastIndexOf('.') + 1)

  /* 直接调用 cos sdk 的方法 */
  cos.uploadFile({
    Bucket: cosConfig['bkt'], /* 填写自己的 bucket，必须字段 */
    Region: cosConfig['rg'],     /* 存储桶所在地域，必须字段 */
    Key: `img/products/${uuid}.${fileExtension}`,              /* 存储在桶里的对象键（例如1.jpg，a/b/test.txt），必须字段 */
    Body: file, // 上传文件对象
    SliceSize: 1024 * 1024 * 5,     /* 触发分块上传的阈值，超过5MB 使用分块上传，小于5MB使用简单上传。可自行设置，非必须 */
  }, function (err, data) {
    console.log("err", err)
    console.log("data", data)
    if (data.statusCode === 200) {
      updateCurrentInfo.photo = 'https://' + data.Location
    }
  });
}

function uploadNew(e) {
  const uuid = randomString(64)
  const file = e.target.files && e.target.files[0];
  const fileExtension = e.target.files[0].name.slice(e.target.files[0].name.lastIndexOf('.') + 1)

  /* 直接调用 cos sdk 的方法 */
  cos.uploadFile({
    Bucket: cosConfig['bkt'], /* 填写自己的 bucket，必须字段 */
    Region: cosConfig['rg'],     /* 存储桶所在地域，必须字段 */
    Key: `img/products/${uuid}.${fileExtension}`,              /* 存储在桶里的对象键（例如1.jpg，a/b/test.txt），必须字段 */
    Body: file, // 上传文件对象
    SliceSize: 1024 * 1024 * 5,     /* 触发分块上传的阈值，超过5MB 使用分块上传，小于5MB使用简单上传。可自行设置，非必须 */
  }, function (err, data) {
    console.log("err", err)
    console.log("data", data)
    if (data.statusCode === 200) {
      colloec.photo = 'https://' + data.Location
    }
  });
}

// 添加藏品
const add_Collection = async function () {
  const res = await addCollection(colloec.title, colloec.url, colloec.type, colloec.photo)
  console.log("tjcp", res)
  if (colloec.selected) {
    await setHotList([res], 'right')
  }
  else {
    await setHotList([res], 'left')
  }
  cancelAdd()
  await handleGetCollectionList()
}
const cancelAdd = () => {
  centerDialogVisible.value = false
  colloec.title = ""
  colloec.url = ""
  colloec.type = ""
  colloec.photo = ""
  colloec.selected = 0
}

const onSubmit = () => {
  console.log('submit!')
}

const checkList = ref(['全部', "书法", "牌匾", "篆刻"])
const cbox = reactive([
  {"id": 1, "label": "全部", "disabled": false},
  {"id": 2, "label": "书法", "disabled": false},
  {"id": 3, "label": "牌匾", "disabled": false},
  {"id": 4, "label": "篆刻", "disabled": false}
])

watch(checkList, (n, o) => {
  if (o.includes("全部") && !n.includes("全部")) {
    checkList.value = []
  }
  if (!o.includes("全部") && n.includes("全部")) {
    checkList.value = ['全部', "书法", "牌匾", "篆刻"]
  }
  if (n.length !== 4) {
    const index = checkList.value.indexOf("全部")
    if (index > -1) {
      checkList.value.splice(index, 1)
    }
  }
  if (n.length === 3 && checkList.value.indexOf("全部") === -1 && o.length !== 4) {
    checkList.value = ['全部', "书法", "牌匾", "篆刻"]
  }
})

const onlySelected = ref(false)
const onlySelectedBtnLoading = ref(false)
// ['全部', "书法", "牌匾", "篆刻", "精选"]
// 模糊搜索
const search = ref('')
const filterOption = {
  "书法": ["t", "sf"],
  "牌匾": ["t", "pb"],
  "篆刻": ["t", "zk"],
}
const filterTableData = computed(() => {

  const tData = [...checkList.value]
  let res = ref([...tableData.value])
  if (tData.indexOf("全部") === -1) {

    console.log("需要筛选")
    res.value = []
    for (let t of tData) {
      // 类别
      if (filterOption[t][0] === "t") {
        res.value.push(...tableData.value.filter(data => data.type === filterOption[t][1]))
        console.log(`类别${filterOption[t][1]}`, res)
      }
    }
  }

  if (onlySelected.value) {
    res.value = res.value.filter(data => data.selected === "1")
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