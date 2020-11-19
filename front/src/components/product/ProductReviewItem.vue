<template>
  <div ref="review-item" class="review-item">
      <div ref="review-positive" class="review-positive">
        <div class="text"> 리뷰 분석 결과 </div>
        <div class="positive" v-if="item.analysis_result==3">
         <img src="@/assets/images/icons/crops/smle.png"/>
          <div class="positive-text"> 긍정적입니다 </div>
        </div>
        <div class="soso" v-if="item.analysis_result==2">
         <img src="@/assets/images/icons/crops/soso.png"/>
          <div class="positive-text"> 보통입니다 </div>
        </div>
        <div class="negative" v-if="item.analysis_result==1">
         <img src="@/assets/images/icons/crops/sad.png"/>
          <div class="positive-text"> 부정적입니다 </div>
        </div>
      </div>
      <div class="body-item">
        <div class="review-username">{{item.nickname}}</div>
        <div class="star-point">
            <div class="star-box">
                <span class="star-rating">
                    <span v-bind:style="computedStarWidth"></span>
                </span>
            </div>
            <div class="star-rate"> {{item.score}} </div>
        </div>
        <div class="review-content"> {{item.content}} </div>
        <div class="review-img">
            <img :src="this.item.path"/>
        </div>
      </div>
  </div>
</template>

<script>
export default {
  props: {
    item: {},
  },
  data(){
    return{
      width:"",
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
 @import "@/assets/product/ProductReviewItem.scss";
</style>