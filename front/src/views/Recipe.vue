<template>
  <div class="container-base">
      <RecipeHeader />
      <hr/>
      <div class="store-container">
        <div class="navbar-container">
            <RecipeNavbar/>
        </div>
        <div class="main-container">
            <router-view></router-view>
        </div>
      </div>
  </div>
</template>

<script>
import RecipeHeader from "@/components/recipe/RecipeHeader.vue";
import RecipeNavbar from "@/components/recipe/RecipeNavbar.vue";
export default {
  name: "Recipe",
  components: {
    RecipeHeader,
    RecipeNavbar,
  }
};
</script>

<style scoped lang="scss">
 @import "@/assets/recipe/recipe.scss";
</style>

