<template>
  <div ref="store-item" class="store-item">
      <div ref="picture-item" class="picture-item" id="picture" @click="$router.push(`/product/${item.id}`)">
        <img v-bind:src="item.img"/>
      </div>
      <div class="body-item">
        <h3>{{item.name}}</h3>
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
.store-item{
    flex-grow: 0;
    display: flex;
    flex-direction: column;
    border-radius: 2%;
    width: 22%;
    height: 300px;
    padding: 30px 50px;
    // text-align: center;
    .picture-item{
        border: 1px solid black;
        overflow: hidden;
        width: 100%;
        height: 100%;
        line-height: 100%;
        display: flex;
        justify-content:center;
        align-items:center;
        background-color: white;
        img{
            min-width: 100%;
            min-height: 100px;
            max-width: 100%;
            max-height: 100%;
        }
    }
}
</style>
