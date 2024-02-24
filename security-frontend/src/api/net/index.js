import {ElMessage} from "element-plus";
import Axios from "@/api/request/request";
//还要处理相应的异常错误信息
function login(username,password,rememberMe,success){
  Axios.post('/auth/login', {
      username: username,
      password: password,
      rememberMe:rememberMe
  },{
      headers:{
          'Content-Type':'application/x-www-form-urlencoded'
      }}
  ).then(({data})=>{
      if (data.code===200&&data.data.token!=='') {
          sessionStorage.setItem("token", data.data.token);
          ElMessage.success({
              message: "登录成功，欢迎您，尊敬的 " + data.data.username + "用户!"
          })
          success(data)
          // eslint-disable-next-line no-empty
      }else {
          ElMessage.error(data.data.message)
      }
  }).catch(({data})=>{
      ElMessage.error(data.data.message)
  })
}

function    logout(){
    const token=sessionStorage.getItem('token');
            //注意后端Spring-Security框架的退出登录是一个POST请求。
            if(token!==''&&token!==null){
                Axios.post('/auth/logout',
                { 
                    headers:{
                        'authorization':token
                    }
                } 
        )
        .then(({res})=>{
            sessionStorage.removeItem("token")//除去token
            ElMessage.success("退出成功");
        })
    }else{
        ElMessage.error("不可重复退出登出 ！");
    }
}
    

export { login ,logout }
