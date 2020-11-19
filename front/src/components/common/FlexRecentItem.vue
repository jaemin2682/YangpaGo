<template>
  <div class="container-item">
    <div v-if="isRecipe==0">
        <template v-for="(data,n) in datas" >
        <RecentRecipe :key="n" :item="data"/>
        </template>
    </div>
     <div v-else>
        <template v-for="(data,n) in datas" >
        <RecentItem :key="n" :item="data"/>
        </template>
    </div>
  </div>
</template>

<script>
import RecentRecipe from "@/components/common/RecentRecipe.vue";
import RecentItem from "@/components/common/RecentItem.vue";
export default {
  components: { RecentRecipe , RecentItem},
  props: {
    datas: [],
    isRecipe:Number,
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/common/FlexRecentItem.scss";
</style>