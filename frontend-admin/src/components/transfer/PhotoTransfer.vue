<template>
  <div class="photo-transfer-container">
    <div class="photo-transfer-header">
      <slot name="transfer-header-text">标题</slot>
    </div>
    <div class="photo-transfer-body">
      <PhotoList
          ref="leftListProps"
          :photo-list="props.photoLeft"
      >
        <template v-slot:list-header-text>
          <slot name="inner-list-left-header-text">列表标题--</slot>
        </template>
        <template v-slot:list-footer-text>
          <slot name="inner-list-left-footer-text"></slot>
        </template>
      </PhotoList>
      <div class="photo-transfer-btn-group">
        <el-button @click="onClickBtn('left')">&lt;</el-button>
        <el-button @click="onClickBtn('right')">&gt;</el-button>
      </div>
      <PhotoList
          ref="rightListProps"
          :photo-list="props.photoRight"
      >
        <template v-slot:list-header-text>
          <slot name="inner-list-right-header-text">列表标题--</slot>
        </template>
        <template v-slot:list-footer-text>
          <slot name="inner-list-right-footer-text"></slot>
        </template>
      </PhotoList>
    </div>
    <div class="photo-transfer-footer">
      <slot name="transfer-footer-text"></slot>
    </div>
  </div>
</template>

<script setup>
import PhotoList from "./comp/PhotoList.vue";
import {ref, onMounted} from "vue";

let leftListProps = ref([]);
let rightListProps = ref([])

onMounted(()=> {
  console.log(leftListProps.value.selectedList);
  console.log(rightListProps.value.selectedList);
})

const props = defineProps({
  photoLeft: {
    type: Array,
    required: true
  },
  photoRight: {
    type: Array,
    required: true
  }
})

const emit = defineEmits(['handleBtnClick'])

const getList = (direction) => direction === 'left' ? leftListProps.value.selectedList : rightListProps.value.selectedList
const onClickBtn = (direction) => {
  if (direction === 'left') {
    // 触发左侧方法
    console.log("to-left")
    console.log(rightListProps.value.selectedList)
    emit('handleBtnClick', 'left', getList('right'))
    rightListProps.value.selectedList = []
  }
  else {
    // 触发右侧方法
    console.log("to-right")
    emit('handleBtnClick', 'right', getList('left'))
    console.log(leftListProps.value.selectedList)
    leftListProps.value.selectedList = []
  }
}


defineExpose({
  leftSearchData: leftListProps.value.searchData,
  rightSearchData: rightListProps.value.searchData
})
</script>

<style scoped>
.photo-transfer-container {
  border: 1px solid lightgray;
  height: 85vh;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  flex-wrap: nowrap;
}
.photo-transfer-header {
  border: lightgray solid 1px;
  background-color: lightgray;
  line-height: 50px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-left: 20px;
  padding-right: 20px;
}
.photo-transfer-body {
  display: flex;
  flex-wrap: nowrap;
  justify-content: space-around;
  align-items: center;
}
.photo-transfer-footer {
  border: lightgray solid 1px;
  background-color: lightgray;
  display: flex;
  justify-content: center;
  align-items: center;
}

.photo-transfer-btn-group {
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  padding: 2vh;
}
</style>