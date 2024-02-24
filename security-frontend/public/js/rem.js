//自适应窗口布局
function remSize(){
    var deviceWidth=document.documentElement.clientWidth||window.innerWidth
    if (deviceWidth>=750){
        deviceWidth=750
    }
    if (deviceWidth<=320){
        deviceWidth=320
    }
    //1rem=100px
    document.documentElement.style.fontSize=(deviceWidth/7.5)+'px'
    //设置字体大小
    document.querySelector('body').style.fontSize=0.3+"rem"
}
remSize()
//当窗口 变化时
window.onresize=function () {
   remSize()
}
