<template>
  <div class="container-item">
    <template v-for="(item,idx) in data">
        <div class="item" :key=idx >{{item.content}} {{item.amount}}</div>
    </template>
  </div>
</template>

<script>
export default {
  props: ["data"],
  methods: {
    // showAmount(idx) {
      // console.log(this)
      // var text = document.querySelector(`#item${idx}`);
      // console.log(text);
    // }
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/recipe/FlexRecipeNeeds.scss";
</style>
