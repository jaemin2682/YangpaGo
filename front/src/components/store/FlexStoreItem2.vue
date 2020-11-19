<template>
  <div class="container-item">
    <StoreItem :item=datas[0] />
    <StoreItem :item=datas[1] />
    <!-- <template v-for="(data, idx) in datas">
        <StoreItem :item=data :key="idx"/>
    </template> -->
  </div>
</template>

<script>
import StoreItem from "@/components/store/StoreItem2.vue";

export default {
  components: { StoreItem },
  props: {
    datas: []
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/sass/FlexStoreItem.scss";
</style>
