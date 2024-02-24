<template>
  <div class="logoutPage">
    <div >
      <div class="container">
        <el-space :fill="false">
          <el-card class="el-card">
            <div>
              <div style="margin-top: 0">
                <div style="font-size: 40px;font-weight: bold">退出登录</div>
              </div>
              <div>             
                 <el-image :src="require('../../assets/img/三叶.png')"
                  style="width: 400px;height: 400px ;
                  border-radius: 15px"></el-image>
              </div>
            </div>
            <div>              
                <el-button @click="userLogout" style="width: 80%; height: 45px;margin-top: 10px" 
                type="primary" plain >登出</el-button>
              </div>
              <div>              
                <el-button @click="userLogin" style="width: 80%; height: 45px;margin-top: 10px" 
                type="success" plain>登录</el-button>
              </div>
          </el-card>
        </el-space>
      </div>

    </div>
  </div>

</template>
<script>
import router from "@/router/router";
import {logout} from "@/api/net"
export default {
  name: 'LogoutPage',
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
        router() {
          return router
        },

        userLogout(){
          logout()
        },
        userLogin(){
          router.push('/')
        }
  },
  mounted() {

  }
}
</script>

<style scoped>
.logoutPage
{
  background: url("../../assets/img/三叶.png");
  background-size: cover;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 1500px;
  .el-card{
    height: 600px;
    width: 500px;
    margin-top: 50px;
    /* 盒子毛玻璃效果 */
    /* background-color: white;
    background-color: transparent;
    backdrop-filter: blur(30px); */
  }
}
</style>
