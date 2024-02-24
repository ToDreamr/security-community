const { defineConfig } = require('@vue/cli-service')
const {ElementPlusResolver} = require("unplugin-vue-components/resolvers");
const AutoImport = require('unplugin-auto-import/webpack');
const Components = require('unplugin-vue-components/webpack');
// const CreateSvgIconsPlugin=require('vite-plugin-svg-icons')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer:{
    allowedHosts:'all',//解决内网穿透时显示无效请求头的问题
    proxy:{
      '/api':{
        target:"http://127.0.0.1:9200",
        changeOrigin:true,
        pathRewrite:{
          '^/api':''
        },
        '/fei':{
          target:"https://t1.akashadata.com",
          changeOrigin:true,
          pathRewrite:{
            '^/fei':''
          }
        }
      },
    },
    //设置不显示地捕捉异常
    client:{
      overlay:false
    }
  },
  lintOnSave:false,
  configureWebpack:{
    plugins:[
        AutoImport({
          resolvers:[ElementPlusResolver({importStyle:false})],
        }),
        Components({
          resolvers:[ElementPlusResolver({importStyle:false})]
        }),
        // CreateSvgIconsPlugin({
        //   iconDirs:[path.resolve(process.cwd()),'src/assets/icons'],
        //   symbolId:'icon-[dir]-[name]',
        // })
    ]
  }
})
