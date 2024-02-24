<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "TeamRating",
  data(){
    return{
      fits : ['fill', 'contain', 'cover', 'none', 'scale-down']
    }
  }
})
</script>

<template>
   <el-space :fill="true">
     <el-card>
       <span>队伍列表</span>
       <div v-for="fit in this.fits" :key="fit" class="block">
         <el-image style="width: 100px ;height: 100px" >

         </el-image>
       </div>
     </el-card>
   </el-space>
</template>

<style scoped>
  .block {
    padding: 30px 0;
    text-align: center;
    border-right: solid 1px var(--el-border-color);
    display: inline-block;
    width: 20%;
    box-sizing: border-box;
    vertical-align: top;
  }
</style>
