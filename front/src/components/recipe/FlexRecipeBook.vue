<template>
  <div class="container-item">
    <template v-for="(data,idx) in datas">
      <RecipeBookItem :key="idx" :recipedata="data"/>
    </template>
  </div>
</template>

<script>
import RecipeBookItem from "@/components/recipe/RecipeBookItem.vue";

export default {
  components: { RecipeBookItem },
  props: {
    datas: [],
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/recipe/FlexRecipeBook.scss";
</style>
