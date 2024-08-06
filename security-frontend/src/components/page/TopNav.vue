<script>
import request from "@/api/request/request";

export default {
    data(){
      return{
        isLogin : false,
        userInfo:{
          username:'',
          avatar:'',
          email:'',
          gender:''
        }
      }
    },
    methods:{
      fetchUserInfo(){
        var username = sessionStorage.getItem("username")
        request.get(`/ua/userInfo`,{
            params:{
              username:username
            }
          },{
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
          }
        }).then(({data})=>{
          console.log(data);
          this.userInfo.username = data.username
          this.userInfo.avatar = data.avatar
          this.userInfo.email = data.email
          this.userInfo.gender = data.gender
        })
      },
      printUserInfo(){
        console.log(this.userInfo)
      }
    },
    mounted() {
      this.fetchUserInfo()
    }
  }
</script>

<template>
  {{userInfo}}
  <button @click="printUserInfo">用户信息</button>
</template>
<style scoped>

</style>
