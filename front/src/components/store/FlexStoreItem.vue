<template>
  <div class="container-item">
    <template v-for="(item,idx) in datas">
      <StoreItem :key="idx" :item="item"/>
    </template>
  </div>
</template>

<script>
import StoreItem from "@/components/store/StoreItem.vue";

export default {
  components: { StoreItem },
  props: {
    datas: [],
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/sass/FlexStoreItem.scss";
</style>
