

<template>
    <div class="main-container">
       <!--스토어 인기상품 페이지-->
      <div class="popular">
        <div class="title">
        <h1>추천 상품</h1>
        </div>
        <div class="store-guideline">
            <p> 추천 상품 </p>
            <p> * 당신의 행동 패턴을 분석하여 필요하거나 취향에 맞는 상품을 추천합니다.</p>
        </div>
        <StoreItem
         :datas="ItemData"
        />
        <infinite-loading
        slot="append"
        @infinite="infiniteHandler"
        force-use-infinite-wrapper=".el-table__body-wrapper"
      >
       </infinite-loading>
      </div>
    </div>
</template>

<script>
// import FlexProductListItem from "@/components/product/FlexProductListItem.vue";
import StoreItem from "@/components/store/FlexStoreItem.vue";
import InfiniteLoading from "vue-infinite-loading";
import axios from "axios";
import { mapGetters } from "vuex";
const SERVER_URL = "http://j3a404.p.ssafy.io:8081";
export default {
    components:{
      // FlexProductListItem,
      InfiniteLoading,
      StoreItem
    },
    data: function() {
        return {
            sellerEmail: this.$route.params.email,
            data: [],
            ItemData: [],
            page: 1
        };
    },
    methods:{
      ...mapGetters({
            getUserId: "user/getUserId",
            getEmail: "user/getEmail"
        }),
       infiniteHandler($state) {
      axios
        .get(
          `${SERVER_URL}/api/store/recommend?email=${this.sellerEmail}&page=${this.page}&userid=${this.getUserId()}`
        )
        .then(response => {
          if (response.data.length) {
            this.ItemData = this.ItemData.concat(response.data);
            $state.loaded();
            this.page += 1;
            if (this.RecipeData.length / 9 == 0) {
              $state.complete();
            }
          } else {
            $state.complete();
          }
        })
        .catch(err => console.log(err));
    },
        fetchFirstdata(){
        axios
        .get(
         `${SERVER_URL}/api/store/recommend?email=${this.sellerEmail}&page=${this.page}&userid=${this.getUserId()}`
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
  },
  mounted(){
      this.fetchFirstdata();
  }
}
</script>
<style lang="scss" scoped>
@import "@/assets/store/storeHome.scss";
</style>