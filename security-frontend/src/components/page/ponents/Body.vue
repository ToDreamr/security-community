<template >
  <el-container style="margin-top: 15%;margin-left: 0; font-family: Georgia, 'Times New Roman', Times, serif;">
    <el-main>
  <!--    <el-slider v-model="this.size" />-->
      <el-space
          wrap :size="size"
              :fill="true"
      >
        <el-card class="border-box">
          <template #header>
            <el-image :src="require('@/assets/paim.jpg') " style="width: 80px ;height: 80px"></el-image>
            <span style="font-size: 20px ;width: 100%;height: 100%">    欢迎来到祈愿分析个人网站</span>
          </template>
          <div>
            <el-form>
              <el-input v-model="linkText" style="height:80px" placeholder="https://webstatic.mihoyo.com/hk4e/event/e20190909gacha/index.html?............=hk4e_cn#/log."></el-input>
              <el-button style="margin-top: 8px ;width: 100%; height: 150%;" type="prime">提交链接</el-button>
            </el-form>
          </div>
        </el-card>

        <el-card>
          <span style=" font-family: Georgia, 'Times New Roman', Times, serif;font-size: 40px ;width: 100%;height: 100%;font-style: content: '\f117';"> 本期up角色</span>
          <span style="font-family: Georgia, 'Times New Roman', Times, serif;font-size: 40px ;width: 100%;height: 100%;font-style: content: '\f117';">  2024/02/21 10:00 - 2024/03/15 17:59</span>
          <el-image :src="require('@/assets/img/三叶.png') " style="width: 100%;height: 100%"></el-image>
        </el-card>
      </el-space>
    </el-main>
<!-- 左边的布局措施-->
    <el-aside v-if="just" style="margin-top: 20px ;margin-left: 0">
      <el-space
          wrap :size="size"
          :fill="true"
      >
      <el-card class="border-box">
        <span style="width: 100%;height: 100%">公告栏，这里会发布管理员的公告</span>
        <el-carousel>
          <el-carousel-item v-for="item in this.messageData" :key="item">
            <el-card style="width: 100%; height: 100%;">{{item.contents}}</el-card>
          </el-carousel-item>
        </el-carousel>
      </el-card>
      </el-space>
    </el-aside>
  </el-container>
</template>

<script >

import Axios from "@/api/request/request";

export default {
  name: 'Body',
  data(){
    return{
      size:'30',
      linkText:'',
      messageData:[],
      just: true
    }
  },
  methods:{
    getMessage(){
      Axios.get(`/admin/publish`).then(({data})=>{
        console.log(data);
        this.messageData=data.data
      })
    },
    isWindows(){
      if (window.innerHeight<900||window.innerWidth<400){
        this.just=!this.just
      }
    }
  },
  mounted() {
    this.getMessage()
    this.isWindows()
  }
}
</script>
<style scoped>
.border-box{
  height: 300px;
}
</style>
