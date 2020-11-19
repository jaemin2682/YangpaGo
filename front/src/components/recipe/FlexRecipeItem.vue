<template>
  <div class="container-item">
    <template v-for="(item, idx) in datas">
      <RecipeItem :key="idx" :item="item"/>
    </template>
  </div>
</template>

<script>
import RecipeItem from "@/components/recipe/RecipeItem.vue";

export default {
  components: { RecipeItem },
  props: {
    datas: [],
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/recipe/FlexRecipeItem.scss";
</style>
