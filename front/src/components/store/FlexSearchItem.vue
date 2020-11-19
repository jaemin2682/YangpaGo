<template>
  <div class="container-item">
    <template v-for="(item,idx) in datas">
      <SearchItem :key="idx" :item="item"/>
    </template>
  </div>
</template>

<script>
import SearchItem from "@/components/store/SearchItem.vue";

export default {
  components: { SearchItem },
  props: {
    datas: [],
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/sass/FlexStoreItem.scss";
</style>
