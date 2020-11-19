<template>
  <div ref="review-container" class="review-container">
      <div class="review-body">
          <div class="review-img">
              <img v-bind:src="item.review_img"/>
          </div>
          <div class="star-point">
            <div class="star-box">
                <span class="star-rating">
                    <span v-bind:style="computedStarWidth"></span>
                </span>
            </div>
            <div class="star-rate"> {{item.score}} </div>
        </div>
          <div class="review-description">
              <div class="review-text">
                  {{item.content}}
              </div>
          </div>
      </div>
      <div class="review-item">
        <div ref="picture-item" class="picture-item">
            <img v-bind:src="item.product_img"/>
        </div>
        <div class="item-description">
            <div class="title">{{item.name}}</div>
            <div class="description">{{numberWithCommas(item.price)}}(/{{item.kg}}kg)</div>
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
    
  },
  computed:{
    computedStarWidth(){
      var starwidth = "width:"+(parseFloat(this.item.score/5.0))*100 + "%";
      return starwidth;
    }
  }
}
</script>

<style scoped lang="scss">
 @import "@/assets/sass/storeReview.scss";
</style>
