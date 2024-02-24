<script >
 export default {
   name:'IndexLeft',
   data(){
     return{
       tableData:[],//数据展示
       menuList:[
         {
           id:'1',
           name:'抽卡分析',
           url:'/analysis',
           icon:''
         },
         {
           id:'2',
           name:'抽卡预测',
           url:'/predict',
           icon:''
         },
         {
           id:'3',
           name:'排行榜',
           url:'/pvp',
           icon:''
         },
         {
           id:'4',
           name:'历史卡池',
           url:'/history',
           icon:''
         },
         {
           id:'5',
           name:'队伍评分',
           url:'/team-rating',
           icon:''
         },
         {
           id:'6',
           name:'角色出装',
           url:'/character-outfit',
           icon:''
         }
       ],
       isCollapse: false,
       menuActive:false,
       iframUrl:'',
       goBackFlag:''
     }
   },
   methods:{
     //左侧窗口的折叠与展开：
     toggleCollapse(){
       this.isCollapse=!this.isCollapse
     },
     menuHandLer(item,goBackFLag){
       //切换目录的逻辑操作
       this.menuActive=true;
       this.iframUrl=item.url
       this.goBackFlag=goBackFLag;
       // console.log(this.iframUrl)
       this.$emit('framUrl',this.iframUrl)
     }
   }
 }
</script>

<template>

  <!-- 侧边栏菜单区域 -->
  <el-menu background-color="#333744" text-color="#fff"
           active-text-color="#409BFF" :unique-opened="true" :collapse="isCollapse"
           :collapse-transition="false" style="height: 5000px"
  >
    <el-menu-item>  

    </el-menu-item>
    <el-menu-item v-for="item in menuList"
      :index=item.id :key="item.id" @click="menuHandLer(item,false)"
    >
<!-- 为左侧菜单添加点击事件-->
      <template #default>
        <span :href="item" style="margin-top: 0 ;  ;font-family: 华文宋体,serif">
        <!--为每一个菜单添加路由控制右侧页面的走向-->
          {{item.name}}
        </span>
      </template>
    </el-menu-item>
  </el-menu>
</template>

<style scoped>

</style>
