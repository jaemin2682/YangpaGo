<template>
    <main>
      <div class="store-header-container">
        <div class="store-title" @click="$router.push(`/store/${storeInfo.email}`)">
          <p><b>{{storeInfo.store_name}}</b></p>
        </div>
        <div class="store-info-container">
          <p> 스토어평점 {{storeInfo.store_score}} | </p>
          <p> 긍정리뷰 {{storeInfo.review_percent}}% | </p>
        </div>
      </div>
    </main>
</template>

<script>
export default {
  components:{
  },
  props: {
    storeInfo:[],
  },
  data() {
    return {
      graphname: '가격 추세',
      labels: ['4.21', '5.05', '5.19', '6.02', '6.16', '6.30', '(현재)'],
      data: [43080, 40060, 36760, 36760,29760, 36760,40890]
    }
  },
}
</script>
<style lang="scss" scoped>
@import "@/assets/store/storeHeader.scss";
</style>