<template>
  <div ref="store-item" class="store-item">
      <div ref="picture-item" class="picture-item">
        <img :src="item.img"/>
      </div>
      <div class="body-item">
        <h5>{{item.name}}</h5>
      </div>
  </div>
</template>

<script>
export default {
  props: {
    item: {},
  }
}
</script>

<style scoped lang="scss">
 @import "@/assets/common/RecentItem.scss";
</style>