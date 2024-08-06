import {createApp} from 'vue'
import App from './App.vue'
import router from "@/router/router";
import ElementPlus from 'element-plus'
import {Notify} from "vant";
//需要添加这个样式才会生效
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App);
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app
    .use(ElementPlus)
    .use(router)
    .use(Notify)
    .mount('#app');
