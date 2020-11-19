<template>
  <div class="container-item">
    <template v-for="(data,idx) in datas">
      <CartItem :key="idx"/>
    </template>
  </div>
</template>

<script>
import CartItem from "@/components/common/CartItem.vue";

export default {
  components: { CartItem },
  props: {
    datas: {
        type: Array,
        required: true,
    }
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/common/FlexCartStore.scss";
</style>
