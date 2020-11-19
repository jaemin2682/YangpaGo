<template>
  <div class="container-item">
    <template v-for="(data, n) in datas">
      <RecipeCard :key="n" :item="data"/>
    </template>
  </div>
</template>

<script>
import RecipeCard from "@/components/recipe/RecipeCard.vue";

export default {
  components: { RecipeCard },
  props: {
    datas: [],
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/recipe/FlexRecipeCard.scss";
</style>
