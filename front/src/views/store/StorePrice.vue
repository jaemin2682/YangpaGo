<template>
    <!--스토어 시세 비교 페이지-->
    <div class="price" >
        <h1>시세보다 저렴한 상품</h1>
        <div class="store-guideline">
            <p> 시세보다 저렴한 상품 </p>
            <p> * 분기별 시세를 분석한 결과를 기반으로 시세보다 저렴한 상품입니다.</p>
        </div>
        <div class="price-graph-container">
          <div class="price-graph-text">
            <div class="category">{{this.categorytext}}</div>
            <div class="text">의 가격 예측 그래프입니다</div>
          </div>
          <div class="price-graph-box">
              <div class="container-item">
                    <div @click="setOnion()"> 양파 </div>
                    <div @click="setCarrot()"> 당근 </div>
                    <div @click="setBachu()"> 배추 </div>
                    <div @click="setGarlic()"> 마늘 </div>
              </div>
              <div class="container-graph">
                  <main-line-chart class="banner-graph onion-graph" 
                  v-bind:graphname="graphname" 
                  v-bind:data="oniondata"
                  v-bind:avgdata="onionavgdata" 
                  v-bind:labels="graphlabel"
                  v-show="this.category == 0"></main-line-chart>
                  <main-line-chart class="banner-graph" 
                  v-bind:graphname="graphname" 
                  v-bind:data="carrotdata"
                  v-bind:avgdata="carrotavgdata" 
                  v-bind:labels="graphlabel"
                  v-show="this.category == 1"></main-line-chart>
                  <main-line-chart class="banner-graph" 
                  v-bind:graphname="graphname" 
                  v-bind:data="bachudata"
                  v-bind:avgdata="bachuavgdata" 
                  v-bind:labels="graphlabel"
                  v-show="this.category == 2"></main-line-chart>
                  <main-line-chart class="banner-graph" 
                  v-bind:graphname="graphname" 
                  v-bind:data="garlicdata"
                  v-bind:avgdata="garlicavgdata" 
                  v-bind:labels="graphlabel"
                  v-show="this.category == 3"></main-line-chart>
              </div>
          </div>
        </div>
        <div class="subtitle">
          시세보다 저렴한 상품
        </div>
        <StoreItem :datas="ItemData" />
         <!-- <StoreItem 
         :datas=6
        /> -->
    </div>
</template>

<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
<script type="text/javascript">
import StoreItem from "@/components/store/FlexStoreItem.vue";
import axios from "axios";
import MainLineChart from '@/views/store/MainLineChart2';
const SERVER_URL = "http://j3a404.p.ssafy.io:8081";
export default {
    components:{
      StoreItem,
      MainLineChart,
    },
    data: function() {
        return {
            sellerEmail: this.$route.params.email,
            data: [],
            ItemData: [],
            page: 1,
            graphname: '가격 추세',
            labels: [],
            graphdata: [],
            oniondata: [1715.40625, 1705.05810546875, 1708.54150390625, 1705.0257568359375, 1706.385498046875, 1702.568115234375, 1693.806396484375, 1679.8662109375, 1685.710205078125, 1685.301513671875, 1702.799560546875, 1716.7734375, 1712.994873046875, 1732.1595458984375],
            carrotdata: [3331.64794921875, 3317.736328125, 3309.42236328125, 3339.37841796875, 3298.75390625, 3363.484130859375, 3364.513671875, 3385.170654296875, 3366.322998046875, 3396.1318359375, 3404.2939453125, 3433.27197265625, 3387.472412109375, 3407.304931640625],
            bachudata: [6720.4921875, 6749.1201171875, 6688.1474609375, 6480.4267578125, 6281.455078125, 6047.9287109375, 6089.67822265625, 6003.35498046875, 6014.869140625, 5921.361328125, 5725.43115234375, 5456.39404296875, 5144.77783203125, 5104.8671875],
            garlicdata: [6204.00048828125, 6247.49755859375, 6179.69970703125, 6226.5341796875, 6279.935546875, 6234.6005859375, 6279.533203125, 6283.72509765625, 6262.98095703125, 6257.75390625, 6322.6875, 6316.01318359375, 6392.88671875, 6450.1025390625],
            graphlabel: ['10.06', '10.07', '10.08', '10.09', '10.10', '10.11', '10.12', '10.13', '10.14', '10.15', '10.16', '10.17', '10.18', '10.19'],
            onionavgdata: [1445,1445,1444,1450,1450,1451,1451,1451,1446,1446,1452,1451,1451,1451],
            carrotavgdata:[3241,3241,3232,3232,3226,3224,3224,3224,3219,3217,3231,3221,3212,3212],
            garlicavgdata:[7153,7153,7155,7155,7158,7161,7161,7161,7173,7175,7181,7184,7185,7185],
            bachuavgdata:[3885,3885,3837,3837,3768,3658,3658,3658,3407,3331,3281,3156,3021,3021],
            graphavgdata:[],
            category:0,
            categorytext:"양파",
        };
    },
    methods:{
        fetchFirstdata(){
        axios
        .get(
         `${SERVER_URL}/api/store/cheap?email=${this.sellerEmail}&page=${
            this.page
          }`
        )
        .then(response => {
          if (response.data.length) {
            console.log(response.data);
            this.ItemData = this.ItemData.concat(response.data);
            this.page += 1;
          } 
        })
        .catch(err => console.log(err));
    },
    setOnion(){
      this.category=0;
      this.categorytext = "양파";
    },
    setCarrot(){
      this.category=1;
      this.categorytext = "당근";
    },
    setBachu(){
      this.category=2;
      this.categorytext = "배추";
    },
    setGarlic(){
      this.category=3;
      this.categorytext = "마늘";
    },
  },
  mounted(){
      this.fetchFirstdata();
      this.readFile();
      this.graphdata = this.oniondata;
  }
}
</script>
<style lang="scss" scoped>
@import "@/assets/store/storePrice.scss";
</style>