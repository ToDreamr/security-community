<script setup>
import LoginCode from "@/components/prQrcode/index.vue"
</script>

<template>
  <div class="wxLogin">
    <h3>扫码登录，二维码 1 分钟内有效</h3>
    <login-code  :size="400"/>
  </div>
</template>

<style scoped>
.wxLogin{
  align-content: center;
  margin-top: 50px;
}
</style>
