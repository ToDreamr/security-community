<template>
  <template>
    <el-icon><Avatar /></el-icon>
  </template>
  <div class="lg">
  <div style="text-align: center;margin: 0 20px" >
    <div style="margin-top: 100px">
      <div style="font-size: 25px;font-weight: bold ;color: white">注册新用户</div>
      <div style="font-size: 14px;color: blanchedalmond">欢迎注册原神祈愿分析，请填写相关信息</div>
    </div>
    <div style="margin-top: 15px">
      <el-space :fill="false"  >
      <el-card style="height: 400px;border-radius: 30px">
        <el-container>
          <el-main>
           <el-form :model="form" :rules="rules" @validate="onValidate" ref="formRef">
             <el-form-item>
               <el-input v-model="form.username" placeholder="用户名">
                 <template #prefix>
                   <el-icon><User /></el-icon>
                 </template>
               </el-input>
             </el-form-item>
             <el-form-item>
               <!-- 表单输入事件绑定，数据将会实时-->
               <el-input v-model="form.email" placeholder="邮箱">
                 <template #prefix>
                   <el-icon><Message /></el-icon>
                 </template>
               </el-input>
             </el-form-item>
             <el-form-item>
               <el-input v-model="form.password" placeholder="密码" type="password">
                 <template #prefix>
                 <el-icon><Lock /></el-icon>
                 </template>
               </el-input>
             </el-form-item>
             <el-form-item>
               <el-input v-model="form.code" placeholder="验证码">
                 <template #prefix>
                   <el-icon><EditPen /></el-icon>
                 </template>
               </el-input>
             </el-form-item>
           </el-form>
          </el-main>
          <el-footer>
              <el-button style="width: 100%"  type="success" @click="register" plain>立即注册</el-button>
            <div style="margin-top: 12px;" >
              <el-button style="width: 100%" type="warning" @click="code"  plain>
                获取验证码
              </el-button>
            </div>
          </el-footer>
        </el-container>
      </el-card>
      </el-space>
    </div>
  </div>
    <div class="foot">
      <el-divider/>
      <div class="wx">
        <div class="left"><el-image :src="require('/src/assets/wx.jpg')" style="width: 3%;height: 3%"></el-image></div>
        <div class="right"><el-link type="primary" style="translate: 0 -2px" @click="router().push('/wxLogin')">微信登录</el-link></div>
      </div>
      <div style="margin-top: 20px">
        <span style="font-size: 14px;line-height: 15px;color: grey">已有账号? </span>
        <el-link type="primary" style="translate: 0 -2px" @click="router().push('/')">立即登录</el-link>
      </div>
    </div>
  </div>
</template>

<script>

import router from "@/router/router";
import request from "@/api/request/request";

export default {
  setup(){

  },
  // eslint-disable-next-line vue/multi-word-component-names
  name:'Register',
  data(){
    return{
      form:{
        username:'',
        password:'',
        email:'',
        code:'',
      },
    }
  },
  methods:{
    router() {
      return router
    },
    register(){
      if (!this.form.username||!this.form.password) {
        this.$message.error("用户名和密码不能为空！");
        return
      }
            request.post("/auth/register",{
              username:this.form.username,
              password:this.form.password,
              email:this.form.email,
              code:this.form.code
            }).then(({data})=>{
              if (data){
                this.$message.success("注册成功")
                router.push('/')
              }
            }).catch((err)=>{
              this.$message.error(err)
            })
          },
    code(){
      var email = this.form.email;
      if (!email){
        this.$message.error("邮箱不能为空！");
        return
      }
      if (!this.isValidEmail(email)){
        this.$message.error("输入邮箱格式有误")
      }else {
        request.post(`/auth/code?email=${email}`).then(({data})=>{
          if (data){
            this.$message.success("获取验证码成功，请前往邮箱查收！")
          }
        }).catch((err)=>{
          this.$message.error(err)
        });
      }
    },
    isValidEmail(email) {
      // 定义邮箱的正则表达式
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      // 使用正则表达式进行匹配验证
      return emailRegex.test(email);
    }
  },
  computed: {
  },
}
</script>

<style scoped>
.lg{
  background: url("../../assets/login.jpg") no-repeat;
  background-size: cover;
  .foot{
    width: 100%;
    background-color: white;
    align-items: center;
  }
}

</style>
