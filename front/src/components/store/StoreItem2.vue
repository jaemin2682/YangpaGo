<template>
  <div ref="store-item" class="store-item">
      <div ref="picture-item" class="picture-item">
        <img v-bind:src="item.img"/>
        <div class="body-item">
        <p class="item-title">{{item.name}}</p>
        <p class="item-price">{{numberWithCommas(item.price)}}원(/{{item.kg}}kg)</p>
        <div class="item-more" @click="$router.push(`/product/${item.id}`)"> 자세히보기 </div>
        </div>
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
 @import "@/assets/sass/storeItem2.scss";
 img {
   height: 350px;
   width: 300px;
 }
 .body-item {
   height: 200px;
 }
</style>
