<template>
  <div class="list-container">

    <div class="list-header">
        <slot name="list-header-text">列表标题</slot>
<!--      <el-input v-model="searchData" placeholder="快捷筛选" clearable style="width: 50%">-->
<!--        <template #prefix="">-->
<!--          <el-icon><Search /></el-icon>-->
<!--        </template>-->
<!--      </el-input>-->
    </div>
    
    <div class="list-body">
      <Photo
          ref="changeCheckStatusPer"
          @change="(t, id) => handleChangeCheckedList(t, id)"
          v-for="item in props.photoList" :key="item.id" class="list"
          :cardinfo="item">
      </Photo>
    </div>

    <div class="list-footer">
      <slot name="list-footer-text"></slot>
    </div>
  </div>


</template>

<script setup>
import {onMounted, ref} from 'vue'
import Photo from "./Photo.vue";

let changeCheckStatusPer = ref()


const searchData = ref('')
const selectedList = ref([])
const handleChangeCheckedList = function (t, id) {
  if (t === 'select'){
    selectedList.value.push(id)
  }
  else if (t === 'cancel'){
    selectedList.value.map((val, idx) => {
      if (val === id){
        selectedList.value.splice(idx, 1)
      }
    })
  }
}
const props = defineProps({
  photoList :{
    type: Array,
    required: true
  }
})

defineExpose({
  selectedList,
  searchData
})
</script>

<style scoped>
  .list-container {
    border: 1px solid lightgray;

  }

  .list-header {
    border: 1px lightgray solid;
    background-color: lightgray;
    line-height: 50px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-left: 20px;
    padding-right: 20px;
  }
  
  .list-body {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    padding: 1vh 0.5vh;
    max-width: 35vw;
    height: 60vh;
    overflow: auto;
  }

  .list-footer {
    border: 1px lightgray solid;
    background-color: lightgray;
    display: flex;
    justify-content: center;
    align-items: center;
  }
</style>