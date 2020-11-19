<template>
  <div class="container-base">
    <!-- 배너 -->
    <div class="banner">
       <vueper-slides class="slides" :slide-ratio="1/5" :autoplay="true">
      <vueper-slide class="slide"
        :key="1">
        <template v-slot:content class="slide-content">
            <div class="banner-items">
              <div class="banner-item discription">
                <div>
                  분기별 인기품목<br>
                  가격 변동 상황
                </div>
                <br>
                <div>
                  <span class="crop-name">양파</span>
                  <span class="crop-unit"> 단위(1kg)</span>
                </div>
              </div>
              <div class="banner-item">
                <h4>가격 추세</h4>
                <main-line-chart class="banner-graph" v-bind:graphname="graphname" v-bind:data="oniondata" v-bind:labels="graphlabel"></main-line-chart>
              </div>
          </div>
        </template>
      </vueper-slide>
      <vueper-slide
        :key="2">
        <template v-slot:content class="slide-content">
            <div class="banner-items">
              <div class="banner-item discription">
                <div>
                  분기별 인기품목<br>
                  가격 변동 상황
                </div>
                <br>
                <div>
                  <span class="crop-name">당근</span>
                  <span class="crop-unit"> 단위(1kg)</span>
                </div>
              </div>
              <div class="banner-item">
                <h4>가격 추세</h4>
                <main-line-chart class="banner-graph" v-bind:graphname="graphname" v-bind:data="garlicdata" v-bind:labels="graphlabel"></main-line-chart>
              </div>
          </div>
        </template>
      </vueper-slide>
      <vueper-slide
        :key="3">
         <template v-slot:content class="slide-content">
            <div class="banner-items">
              <div class="banner-item discription">
                <div>
                  분기별 인기품목<br>
                  가격 변동 상황
                </div>
                <br>
                <div>
                  <span class="crop-name">배추</span>
                  <span class="crop-unit"> 단위(1포기)</span>
                </div>
              </div>
              <div class="banner-item">
                <h4>가격 추세</h4>
                <main-line-chart class="banner-graph" v-bind:graphname="graphname" v-bind:data="bachudata" v-bind:labels="graphlabel"></main-line-chart>
              </div>
          </div>
        </template>
      </vueper-slide>
      <vueper-slide
        :key="4">
         <template v-slot:content class="slide-content">
            <div class="banner-items">
              <div class="banner-item discription">
                <div>
                  분기별 인기품목<br>
                  가격 변동 상황
                </div>
                <br>
                <div>
                  <span class="crop-name">마늘</span>
                  <span class="crop-unit"> 단위(1kg)</span>
                </div>
              </div>
              <div class="banner-item">
                <h4>가격 추세</h4>
                <main-line-chart class="banner-graph" v-bind:graphname="graphname" v-bind:data="garlicdata" v-bind:labels="graphlabel"></main-line-chart>
              </div>
          </div>
        </template>
      </vueper-slide>
    </vueper-slides>
    </div>

    <div class="main-container">
      <!-- 추천 레시피 -->
      
      <div class="recipes-container">
        <h3>레시피 추천 재료</h3>
        <div class="recipes">
          <template v-for="(recipe,idx) in recRecipes">
              <router-link class="recipe" tag="button" :to="`/recipe/search?keyword=${recipe.ingedient}`" :key="idx">
              <div class="recipe-body">
                <img class="crops-icon" :src="recipe.icon"/>
              </div>
              <div class="recipe-footer">
                {{recipe.ingedient}}
              </div>
            </router-link>
        </template>
          <router-link class="recipe" tag="button" to="/recipe/all">
            <div class="recipe-body">
              <img class="more-img" src="@/assets/images/icons/dot_icon.svg"/>
            </div>
            <div class="recipe-footer">
              레시피 더보기
            </div>
          </router-link>
        </div>
      </div>

      <!-- 이 달의 과잉 생산품 -->
      <div class="crops-container">
        <h3>이 달의 과잉 생산품</h3>
        <div class="crops">
            <FlexMainItemBig 
            :datas="overItems"
            />
            <div class="more-btn-container more" @click="$router.push('/productlist/over')">
              <img src="@/assets/images/icons/next.svg"/>
            </div>
        </div>
      </div>
      
      <!-- 못생겼지만 괜찮아 -->
      <div class="crops-container">
        <h3>못생겼지만 괜찮아</h3>
        <div class="crops">
            <FlexMainItemBig 
            :datas="bgradeItems"
            />
            <div class="more-btn-container more" @click="$router.push('/productlist/bgrade')">
              <img src="@/assets/images/icons/next.svg"/>
            </div>
        </div>
      </div>

      <!-- 아이템 리스트 -->
      <div class="crops-container">
        <h3>아이템 리스트</h3>
        <div class="crops">
            <FlexMainItemBig 
            :datas="items"
            />
            <div class="more-btn-container more" @click="$router.push('/productlist/all')">
              <img src="@/assets/images/icons/next.svg"/>
            </div>
        </div>
      </div>
      
    </div>

  </div>
</template>

<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
<script type="text/javascript">
import MainLineChart from './MainLineChart';
import axios from "axios";
import FlexMainItemBig from "@/components/main/FlexMainItemBig.vue";
import FlexMainItemSmall from "@/components/main/FlexMainItemSmall.vue";
import { VueperSlides, VueperSlide } from 'vueperslides'
import 'vueperslides/dist/vueperslides.css'
const SERVER_URL = "http://j3a404.p.ssafy.io:8081";
export default {
  // name: 'Home',
  components:{
    MainLineChart,
    FlexMainItemBig,
    VueperSlides,
    VueperSlide
  },
  data() {
    return {
      graphname: '가격 추세',
      labels: ['4.21', '5.05', '5.19', '6.02', '6.16', '6.30', '(현재)'],
      data: [43080, 40060, 36760, 36760,29760, 36760,40890],
      overItems: [],
      bgradeItems:[],
      items: [],
      recRecipes: [{ingedient: "양파", icon: "http://j3a404.p.ssafy.io/images/postRep/20201006180911_onion.png"},
                   {ingedient: "옥수수", icon: "http://j3a404.p.ssafy.io/images/postRep/20201006180911_corn.png"},
                   {ingedient: "콩", icon: "http://j3a404.p.ssafy.io/images/postRep/20201006180911_bean.png"},
                   {ingedient: "마늘", icon: "http://j3a404.p.ssafy.io/images/postRep/20201006180911_garlic.png"}],
      oniondata: [1698,1645,1673,1783,2007,2060, 1693.806396484375, 1716.7734375],
      carrotdata: [3181,2834,2767,2780,3806,4174 ,3364.513671875, 3433.27197265625],
      bachudata: [3573,3235,3721,5853, 8581, 8316, 6688.1474609375, 5456.39404296875],
      garlicdata: [5308, 5288, 5845, 6691,7070,7153, 6579.533203125, 6316.01318359375],
      graphlabel: ['05.18','06.18','07.18','08.18','09.18','10.08(현재)', '10.13(예상)', '10.18(예상)'],
    }
  },
  methods:{
    fetchOverItems(){
        // Todo : 과잉 농산물 데이터 통신 코드 작성
    },
    fetchBgradeItems(){
        axios
        .get(
          `${SERVER_URL}/api/main/search/bgrade`
        )
        .then(response => {
          if (response.data.length) {
            this.bgradeItems = response.data;
            console.log(response.data);
          } 
        })
        .catch(err => console.log(err));
    },
    fetchAllItems(){
        axios
        .get(
          `${SERVER_URL}/api/main/search/product/all`
        )
        .then(response => {
          if (response.data.length) {
            this.items = response.data;
            console.log(response.data);
            this.items = this.items.slice(0, 3);
          } 
        })
        .catch(err => console.log(err));
    },
    fetchOverItems(){
        axios
        .get(
          `${SERVER_URL}/api/main/over`
        )
        .then(response => {
          if (response.data.length) {
            this.overItems = response.data;
            console.log(response.data);
            this.overItems = this.overItems.slice(0, 3);
          } 
        })
        .catch(err => console.log(err));
    }
  },
  created(){
    this.fetchBgradeItems();
    this.fetchAllItems();
    this.fetchOverItems()
  }
};
</script>

<style scoped lang="scss">
@import "@/assets/_variables.scss";
@import "@/assets/common/Base.scss";
@import "@/assets/common/Home.scss";
</style>
