<template>
  <div class="container-base">
      <ProductHeader />
      <hr/>
      <div class="store-container">
        <div class="navbar-container">
            <ProductNavbar/>
        </div>
        <div class="main-container">
            <router-view></router-view>
        </div>
      </div>
  </div>
</template>

<script>
import ProductHeader from "@/components/product/ProductHeader.vue";
import ProductNavbar from "@/components/product/ProductNavbar.vue";
export default {
  name: "Recipe",
  components: {
    ProductHeader,
    ProductNavbar,
  }
};
</script>

<style scoped lang="scss">
 @import "@/assets/recipe/recipe.scss";
</style>

