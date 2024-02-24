<script >
import Axios from "@/api/request/request";
import router from "@/router/router";
export default {

  data(){
    return{
     isLogined : false//用冒号表达式赋值
    }
  },
  methods: {
    router() {
      return router
    },
    logOut() {
       router.push('/logout')
    },
    isLogin() {
      const token = sessionStorage.getItem('token');
      token !== null ? this.isLogined = true : this.isLogined = false
      console.log(this.isLogined);
    },
  },
  mounted() {
    this.isLogin();//查询当前用户是否登录
  }
}

</script>

<template>
  <div style="font-size: 30px;">
    <div>
      <h1>原神祈愿分析</h1>
    </div>
  </div>
    <div aria-label="A complete example of page header">
      <el-page-header  style="font-size: 30px;">
        <template #breadcrumb>
          <el-breadcrumb separator="-">
            <el-breadcrumb-item :to="{ path: '/register' }">
              首页
            </el-breadcrumb-item>
            <el-breadcrumb-item>抽卡详情页</el-breadcrumb-item>
          </el-breadcrumb>
        </template>
        <template #content>
          <div class="flex items-center">
            <el-avatar
                class="mr-3"
                :size="0"
                src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
            />
            <span class="text-large font-600 mr-3"> 本地工具 </span>
            <span
                class="text-sm mr-2"
                style="color: var(--el-text-color-regular)"
            >
          </span>
            <el-tag>默认标签</el-tag>
          </div>
        </template>
        <template #extra>
          <el-form>
          <div class="flex items-center">
            <el-form-item>
            <el-button v-if="this.isLogined" type="primary" class="ml-2" @click="logOut">退出登录</el-button>

            <el-button v-else type="success" class="ml-2" @click="router().push('/')">登录</el-button></el-form-item>
          </div>
        </el-form>

</template>

        <el-descriptions :column="3" size="small" class="mt-4">
          <el-descriptions-item label="用户名"
          style="font-size: 30px;"
          >应急食品</el-descriptions-item
          >
          <el-descriptions-item label="专项擅长"
          style="font-size: 30px;"
          >寻找食物</el-descriptions-item
          >
        </el-descriptions>
        <p class="mt-4 text-sm"   style="font-size: 30px;">
          这是一个自己写的垃圾网站，啥也不是
        </p>

      </el-page-header>
    </div>
</template>

<style scoped>

</style>
