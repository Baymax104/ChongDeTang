<template>
<h1>活动预约</h1>
  <el-button @click="getUserAddress">获取地址</el-button>
</template>

<script setup>
import axios from "../../utils/axios";

const getUserAddress = () => {
  axios.get('/api/user/address').then(res => {
    console.log("地址", res)
  })
}

</script>

<style scoped>

</style>