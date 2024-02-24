<template>
      <router-view/>
</template>

<script>

import Welcome from "@/components/views/Welcom.vue";

export default {
  name: 'App',
  components: {
    Welcome
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial,宋体 sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 0;
}
header {
  line-height: 1.5;
}
</style>
