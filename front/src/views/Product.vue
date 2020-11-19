<template>
  <div class="container-base">
     <StoreHeader />
     <hr/>
     <ProductMain/>
  </div>
</template>

<script>
import StoreHeader from "@/components/store/StoreHeader.vue";
import ProductMain from "@/components/product/Product.vue";
export default {
  name: "Product",
  components: {
    StoreHeader,
    ProductMain,
  }
};
</script>