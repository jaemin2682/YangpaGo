<template>
  <div class="container-item">
    <template v-for="(data,n) in datas">
      <ProductListItem :key="n" :item="data"/>
    </template>
  </div>
</template>

<script>
import ProductListItem from "@/components/product/ProductListItem.vue";

export default {
  components: { ProductListItem },
  props: {
    datas: Array,
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/product/FlexProductListItem.scss";
</style>
