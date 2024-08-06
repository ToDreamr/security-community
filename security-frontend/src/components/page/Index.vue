<script>
import TopNav from "@/components/page/TopNav.vue";
import {ElMessage} from "element-plus";
import index from "@/components/page/Index.vue";

export default {
    // eslint-disable-next-line vue/multi-word-component-names
    name:'Index',
    computed: {
      index() {
        return index
      }
    },
    components: {TopNav},
    data(){
      return{
        tableData:[],//数据展示
      }
    },
    methods:{
      ElMessage
    },
    mounted() {

    }
  }
</script>

<template>

  <el-form>
  <div class="common-layout" style=" font-family: Georgia, 'Times New Roman', Times, serif;">
    <el-container>
        <el-header >
          <TopNav/>
        </el-header>
        <!-- 改变body的页面实现切换数据页面 -->
       <el-main>
         <Body></Body>
       </el-main>
    </el-container>
  </div>
  </el-form>

</template>

<style scoped>
.toggle-button{
  background-color: #4A5064;
  font-size: 10px;
  line-height: 24px;
  color: #fff;
  text-align: center;
  letter-spacing: 0.2em;
  cursor: pointer;
}
</style>
