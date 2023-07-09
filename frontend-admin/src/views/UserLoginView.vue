<template>
  <div class="login-body">
    <div class="login-container">
      <div class="head">
        <img class="logo" src="/src/assets/logo.png" />
        <div class="name">
          <div class="title">崇德堂</div>
          <div class="tips">后台管理系统</div>
        </div>
      </div>
      <el-form label-position="top" :rules="state.rules" :model="state.ruleForm" ref="loginForm" class="login-form">
        <el-form-item label="手机号" prop="phone">
          <el-input  type="text" v-model.trim="state.ruleForm.phone" autocomplete="off"></el-input>
        </el-form-item>
        <div style="height: 15px"></div>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model.trim="state.ruleForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <div style="color: #333; margin-top: 15px">登录表示您已同意<a href="https://weixin.qq.com/agreement?lang=zh_CN" target="_blank">《服务条款》</a></div>
          <el-button style="width: 100%; background-color: #ac2c28; border-color: #ac2c28" type="primary" @click="submitForm">立即登录</el-button>
<!--          <el-checkbox v-model="state.checked" @change="!state.checked" >下次自动登录</el-checkbox>-->
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { localSet } from '../utils'
import {ElMessage} from "element-plus";
import { login } from "../api/login";

const loginForm = ref(null)
const state = reactive({
  ruleForm: {
    phone: '',
    password: ''
  },
  checked: true,
  rules: {
    phone: [
      { required: 'true', message: '手机号不能为空', trigger: 'blur' }
    ],
    password: [
      { required: 'true', message: '密码不能为空', trigger: 'blur' }
    ]
  }
})
const submitForm = async () => {
  loginForm.value.validate((valid) => {
    if (valid) {
      login(state.ruleForm.phone, state.ruleForm.password).then(res => {
        if (res.admin === "1"){
          console.log("登录返回数据", res)
          localSet('token', res.token)
          localSet('userinfo', res)
          window.location.href = '/'
        }
        else {
          ElMessage.error('登录失败，您不是管理员！')
        }
      })
    } else {
      console.log('error submit!!')
      return false;
    }
  })
}
const resetForm = () => {
  loginForm.value.resetFields();
}
</script>

<style scoped>
  .login-body {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    background-color: #fff;
  }
  .login-container {
    width: 420px;
    height: 500px;
    background-color: #fff;
    border-radius: 4px;
    box-shadow: 0px 21px 41px 0px rgba(0, 0, 0, 0.2);
  }
  .head {
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 40px 0 20px 0;
  }
  .head img {
    width: 100px;
    height: 100px;
    margin-right: 20px;
  }
  .head .title {
    font-size: 28px;
    color: #ac2c28;
    font-weight: bold;
  }
  .head .tips {
    font-size: 12px;
    color: #999;
  }
  .login-form {
    width: 70%;
    margin: 0 auto;
  }
  .login-form >>> .el-form--label-top .el-form-item__label {
    padding: 0;
  }
  .login-form >>> .el-form-item {
    margin-bottom: 0;
  }
</style>