<script >
   export default {
     data(){
       const form={
         username:'',
         password:''
       }
       return{
         form
       }
     },
     methods:{

     }
   }
</script>

<template>
  <div class="refound">

  <el-space>
    <el-card style="margin-top: 400px;margin-bottom: 400px ;width:20%;border-radius:30px;height: 750px;display: flex;box-shadow: white">
      <div style="margin-top: 0">
        <div style="font-size: 25px;font-weight: bold">找回密码</div>
        <div style="font-size: 14px;color: grey">请填写相关信息</div>
      </div>
      <el-image :src="require('../../assets/refound.png')" style="width: 300px;height: 300px ;border-radius: 15px" fit="fill"></el-image>
      <div style="margin-top: 8%;">
        <el-form :model="form" >
          <el-form-item>
            <el-input style=" margin-right: 5%;margin-left: 5%;margin-top: 30px;height: 30px"
                      placeholder="用户名" v-model="form.username" maxlength="10" type="text">
              <template #prefix>

                <el-icon><User /></el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-input style=" margin-right: 5%;margin-left: 5%; height: 30px "
                      placeholder="密码" v-model="form.password" type="password">
              <template #prefix>
                <el-icon><Lock /></el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-input style=" margin-left: 5%;margin-left: 5%; height: 30px ;width: 50%" placeholder="验证码"
                      v-model="form.code" type="text">
              <template #prefix>
                <el-icon><EditPen /></el-icon>
              </template>
            </el-input>
            <el-button class="el-button--info"  style="width: 35% ;margin-left: 5%">获取验证码</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div><el-button type="primary" style="width: 90%">提交申请</el-button></div>
      <div class="left">
        <el-image :src="require('/src/assets/wx.jpg')" style="width: 10%;height:10%;margin-top: 8px">
        </el-image>
      </div>
      <div class="right"><el-link type="primary" style="translate: 0 -2px">微信登录</el-link></div>
      <div style="margin-left: 70%">
        <el-icon size="20"><Position /></el-icon>
        <el-link type="primary" style="translate: 0 -2px;margin-left: 8px" @click="$router.push('/')" >登录</el-link></div>
    </el-card>
  </el-space>
  </div>
</template>

<style scoped>
.refound
{
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background: url("../../assets/login.jpg");
  background-size: cover;
}

</style>
