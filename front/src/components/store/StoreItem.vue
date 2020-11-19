<template>
  <div ref="store-item" class="store-item"  >
      <div ref="picture-item" class="picture-item" @click="$router.push(`/product/${item.id}`)">
        <img v-bind:src="item.img"/>
      </div>
      <div class="body-item">
        <h4>{{item.name}}</h4>
        <div class="description">{{numberWithCommas(item.price)}}원({{item.kg}}kg)</div>
      </div>
  </div>
</template>

<script>
export default {
  props: {
    item: {},
  },
  methods: {
    numberWithCommas(x) {
      return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }
  }
}
</script>
<style scoped lang="scss">
 @import "@/assets/sass/storeItem.scss";
</style>
