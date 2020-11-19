<template>
  <div ref="store-item" class="store-item" @click="$router.push(`/product/${item.id}`)">
      <div ref="picture-item" class="picture-item">
        <img :src="item.img"/>
      </div>
      <div class="body-item">
        <h5>{{item.name}}</h5>
        <div class="description">{{item.price}} ({{item.kg}}kg)</div>
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
 @import "@/assets/product/ProductListItem.scss";
</style>
