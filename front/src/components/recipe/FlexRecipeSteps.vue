<template>
  <div class="container-item">
    <template v-for="(item,idx) in steps">
        <div class="step" :key="idx">
          <div class="itemIdx">STEP{{idx+1}}</div>
          <div class="item">{{item.content}}</div>
        </div>
    </template>
  </div>
</template>

<script>
export default {
  data() {
    return {      
    };
  },
  props:{
    steps:[],
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/recipe/FlexRecipeSteps.scss";
</style>
