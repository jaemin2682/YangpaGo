<template>
  <div class="container-item">
    <template v-for="(data,n) in datas">
      <ProductItem :key="n" :item="data"/>
    </template>
  </div>
</template>

<script>
import ProductItem from "@/components/product/ProductItem.vue";

export default {
  components: { ProductItem },
  props: {
    datas: [],
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/product/FlexProductItem.scss";
</style>
