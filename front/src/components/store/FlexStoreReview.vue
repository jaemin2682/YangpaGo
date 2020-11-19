<template>
  <div class="container-item">
    <StoreReview :item=datas[0] />
    <StoreReview :item=datas[1] />
    <!-- <template v-for="(data, idx) in datas">
        <StoreReview :item="data" :key="idx"/>
    </template> -->
  </div>
</template>

<script>
import StoreReview from "@/components/store/StoreReview.vue";

export default {
  components: { StoreReview },
  props: {
    datas: []
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/sass/FlexStoreItem.scss";
</style>
