<template>
  <div class="container-item">
    <!-- <StoreItem :item=datas[0] />
    <StoreItem :item=datas[1] /> -->
    <template v-for="(data, idx) in datas">
        <MainItemBig :item=data :key="idx"/>
    </template>
  </div>
</template>

<script>
import MainItemBig from "@/components/main/MainItemBig.vue";

export default {
  components: { MainItemBig },
  props: {
    datas: Array
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/sass/FlexMainItem.scss";
</style>
