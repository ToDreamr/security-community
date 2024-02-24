<template>
  <div class="loginPage">
    <div >
      <div>
          <h3 style="align-content: center;align-items: center ;color: white;font-size: 80px;font-family: 华文宋体,serif">
            Genshin-Impact 祈愿分析登录</h3>
      </div>
      <div class="container">
        <el-space :fill="false">
          <el-card class="el-card">
            <div class="right_box">
              <div style="margin-top: 0">
                <div style="font-size: 25px;font-weight: bold">登录</div>
                <div style="font-size: 14px;color: grey">欢迎登录原神祈愿分析，请填写相关信息</div>
              </div>
              <el-form :model="form" >
                <el-form-item>
                  <el-input  class="login-input" style="margin-top: 10px;" placeholder="用户名" v-model="form.username" maxlength="10" type="text">
                    <template #prefix>
                      <el-icon><User /></el-icon>
                    </template>
                  </el-input>
                </el-form-item>
                <el-form-item>
                  <el-input placeholder="密码" class="login-input" v-model="form.password" type="password">
                    <template #prefix>
                      <el-icon><Lock /></el-icon>
                    </template>
                  </el-input>
                </el-form-item>
                <el-form-item style="display: flex">
                  <div>
                    <el-input style=" width: 85%"  class="login-input" placeholder="验证码" v-model="form.code" type="text">
                      <template #prefix>
                        <el-icon><EditPen /></el-icon>
                      </template>
                    </el-input></div>
                  <el-form-item/>
                  <div>
                    <el-button class="el-button--info" v-model="this.imgSrc" @click="getImgCode" style="width:110%;margin-right: 80%">获取验证码</el-button></div>
                </el-form-item>
                <img :src="imgSrc" />
              </el-form>
              <el-button @click="userLogin" style="width: 284px;margin-top: 20px" type="success" plain>登录</el-button>
              <div>
                <el-button style="width: 284px;margin-top:10px" type="warning" plain @click="register">注册</el-button>
              </div>
              <el-row style="margin-top: 12px">
                <el-form-item style="margin-left: 18px">
                  <el-checkbox v-model="form.rememberMe">
                    记住我
                  </el-checkbox>
                  <el-link style="margin-left: 20px" @click="$router.push('/find_back')">
                    忘记密码？
                  </el-link>
                </el-form-item>
              </el-row>
              <div class="left"><el-image :src="require('/src/assets/wx.jpg')" style="width: 10%;height:10%"></el-image></div>
              <div class="right"><el-link type="primary" style="translate: 0 -2px" @click="$router.push('/wxLogin')">微信登录</el-link></div>
            </div>
          </el-card>
        </el-space>
      </div>
    </div>
  </div>

</template>
<script>
import Axios from "@/api/request/request";
import router from "@/router/router";

import {login} from "@/api/net";

export default {
  name: 'LoginPage',
  data(){
    return{
      form:{
        rememberMe:''
      },
      imgSrc:'',
      codeImg:'',
    }
  },
  props: {
  },
  methods:{
    gitee(){
      Axios.post('/gitee',"")
    },
    router() {
      return router
    },
    userLogin(){
      if (!this.form.username||!this.form.password){
        this.$message.error("用户名和密码不能为空！")
        return
      }
      login(this.form.username,this.form.password,this.form.rememberMe,()=>{
        router.push('/index')
      })
    },
    register(){
       router.push('/register')
    },
    getImgCode(){
      // Axios.get(`auth/captchaImage`).then(({data})=>{
      
      //   const blob = new Blob([data], { type: 'image/jpeg' }); // 创建Blob对象
      //   this.imgSrc = URL.createObjectURL(blob).slice(5); // 将Blob对象的URL赋值给imageSrc
      //   console.log(imgSrc);
      // })
      this.imgSrc="http://localhost:8081/api/auth/captchaImage";
    },
  },
  mounted() {

  }
}
</script>

<style scoped>
.loginPage
{
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    background: url("../assets/login.jpg");
    background-size: cover;
    .login-input{
      margin-right: 5%;
      margin-left: 5%;
      height: 40px 
    }
  .container{
    display: inherit;
    box-shadow: 0 5px 20px 2px rgba(255, 255, 255, 0.1);
    .el-card{
      border-radius: 30px;
      margin-top: 400px;margin-bottom: 400px;height: 600px;
      box-shadow: 0 5px 20px 2px rgba(255, 255, 255, 0.1);
      .left_box{
        width: 286px;
      }
      .right_box{
        width: 344px;
        padding: 20px;
        position: relative;
      }
    }

  }
}
</style>
