import axios from "axios";
const commonUrl = "/api";

const Axios=axios.create({
    baseURL: '/api',
    timeout:2000,
    withCredentials:false
})

Axios.defaults.baseURL = commonUrl;
let token = sessionStorage.getItem("token");
//构建拦截器
Axios.interceptors.request.use(
    config=>{
        if (token!==null&&typeof (token)!="undefined")
            config.headers['authorization']=token
            return config
    },
    err=>{
        console.log(err);
    }
)

// Axios.interceptors.response.use((response)=>{
//     if (response.data.code!=200){
//         return Promise.reject(response.data.message)
//     }
//     return response
// },(err)=>{
//     if (err.response.status==401){
//         setTimeout(()=>{
//            console.log("登录状态错误，status：401");
//         },200);
//        return Promise.reject("登录未完成")
//     }
//     if (err instanceof Proxy){
//         console.log(err+"接收到异常操作")
//     }
//    return Promise.reject("服务器异常")
// })

Axios.defaults.paramsSerializer = function(params) {
    let p = "";
    Object.keys(params).forEach(k => {
        if(params[k]){
            p = p + "&" + k + "=" + params[k]
        }
    })
    return p;
}
export default Axios
