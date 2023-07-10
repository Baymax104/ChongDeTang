<template>
  <PhotoTransfer
      :photo-left="leftData" :photo-right="rightData"
      @handleBtnClick="(direction, idlist) => changeStatus(direction, idlist)"
  >
    <template v-slot:transfer-header-text>
      设置热销藏品
    </template>
    <template v-slot:transfer-footer-text>

    </template>
    <template v-slot:inner-list-left-header-text>
      未上架：&nbsp;{{ leftData.length }}件藏品
    </template>
    <template v-slot:inner-list-left-footer-text>

    </template>
    <template v-slot:inner-list-right-header-text>
      已上架：&nbsp;{{ rightData.length }}件藏品
    </template>
    <template v-slot:inner-list-right-footer-text>

    </template>
  </PhotoTransfer>
</template>

<script setup>

import PhotoTransfer from "../../components/transfer/PhotoTransfer.vue";
import {ref} from "vue";
import {getHotList, getNotHotList, setHotList} from "../../api/home";
import {ElMessage} from "element-plus";

const leftData = ref([])
const rightData = ref([])
const loadData = async () => {
  let resData = await Promise.all([getHotList(), getNotHotList()])
  leftData.value = []
  rightData.value = []
  resData[1].forEach(rd => {
    leftData.value.push({
      id: rd.id,
      picSrc: rd.photo,
      picInfo: rd.title
    })
  })
  resData[0].forEach(rd => {
    rightData.value.push({
      id: rd.id,
      picSrc: rd.photo,
      picInfo: rd.title
    })
  })
}
loadData()


const changeStatus = (d, idl) => {
  if (idl.length !== 0){
    setHotList(idl, d).then(res=> {
      console.log("change-ok")
      loadData()
      ElMessage.success(`${d === "right" ? "上架" : "下架"}了${idl.length}件藏品`)
    })
    console.log(d, idl)
  }
}
</script>

<style scoped>

</style>