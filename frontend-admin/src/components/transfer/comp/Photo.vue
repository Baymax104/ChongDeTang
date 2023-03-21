<template>
  <div class="container" @click.prevent="onClickCheckBox(props.cardinfo.id)">
    <el-card :body-style="{ padding: '5px' }">
      <img
          :src="props.cardinfo.picSrc"
          style="width: auto; height: auto"
          class="image"
      />
      <div style="padding: 4px;">
        <el-checkbox v-model="checked" label="选择商品" size="large" />
        <div class="bottom">
          {{ props.cardinfo.picInfo }}
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const emit = defineEmits(['change'])
const checked = ref(false)
const onClickCheckBox = function (id) {
  if (id){
    checked.value = !checked.value
    // 选择，就添加到列表里
    if (checked.value){
      emit('change', 'select', id)
    }
    else {
      emit('change', 'cancel', id)
    }
  }
}
const props = defineProps({
  cardinfo: {
    type: Object,
    required: true
  }
})

defineExpose({
  onClickCheckBox
})

</script>

<style scoped>
  .container {
    width: 10vw;
    height: 15vw;
    border: 50px;
    cursor: pointer;
  }
  .image {
    width: auto;
    height: auto;
    max-height: 100%;
    max-width: 100%;
  }
  .bottom {
    display: flex;
    justify-content: space-between;
    align-items: center;
    overflow: auto;
  }
</style>
