<template>
  <div class="container-item">
    <template v-for="(data,n) in datas">
      <img :src="data" :key="n" class="sub-image-box"/>
    </template>
  </div>
</template>

<script>
export default {
  props: {
    datas: [],
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/product/FlexSubImagebox.scss";
</style>
