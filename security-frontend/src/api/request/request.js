import axios from "axios";

const commonUrl = "/api";

const request = axios.create({
    baseURL: '/api',
    timeout: 2000,
    withCredentials: false
})

request.defaults.baseURL = commonUrl;
let token = sessionStorage.getItem("token");
//构建拦截器
request.interceptors.request.use(
    config => {
        if (token !== null && typeof (token) != "undefined")
            config.headers['authorization'] = token
        return config
    },
    err => {
        console.log(err);
    }
)

request.defaults.paramsSerializer = function (params) {
    let p = "";
    Object.keys(params).forEach(k => {
        if (params[k]) {
            p = p + "&" + k + "=" + params[k]
        }
    })
    return p;
}
export default request
