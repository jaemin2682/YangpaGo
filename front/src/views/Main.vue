<template>
  <div class="main">
    <!-- <NavbarMini /> -->
    <router-view />
  </div>
</template>

<script>
import {mapGetters } from "vuex";
import router from '@/router/index.js'

export default {
  name: "Main",
  created(){
    if(!this.getIsLogin()){
      router.push("/logo");
    }
  },
  methods: {
    ...mapGetters({
      getIsLogin: "user/getIsLogin",
    }),
  },
};
</script>
