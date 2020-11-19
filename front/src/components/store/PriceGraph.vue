<template>
  <div class="container-content">
    <div class="container-item">
        <p> 감자 </p>
        <p> 파 </p>
        <p> 양파 </p>
        <p> 마늘 </p>
        <p> 부추 </p>
        <p> 당근 </p>
        <p> 토마토 </p>
        <p> 애호박 </p>
    </div>
    <div class="container-graph">
        <div class="graph">
        <line-chart></line-chart>
        </div>
    </div>
  </div>
</template>

<script>
import LineChart from "@/components/store/LineChart.vue";
export default {
    components: { LineChart },
}
</script>
<style lang="scss" scoped>
@import "@/assets/sass/priceGraph.scss";
</style>



