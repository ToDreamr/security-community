const path=require('path')
module.exports={
    entry:'./src/main.js',//配置入口的文件
    output:{
        path:path.resolve(__dirname,'./dist'), //输出路径
        filename:'bundle.js'
    }
    //使用命令进行打包,webpack打包工具同时也可以打包css，html等静态文件
}
