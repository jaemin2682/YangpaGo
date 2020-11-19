<template>
    <div class="main-container">
       <!--스토어 인기상품 페이지-->
      <div class="popular">
        <div class="title">
        <h1>인기 상품</h1>
        </div>
        <div class="store-guideline">
            <p> 인기 상품 </p>
            <p> * 해당 스토어에서 주문량이 많은 상품입니다.</p>
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
import StoreItem from "@/components/store/FlexStoreItem.vue";
import InfiniteLoading from "vue-infinite-loading";
import { mapGetters } from "vuex";
import axios from "axios";
const SERVER_URL = "http://j3a404.p.ssafy.io:8081";
export default {
    components:{
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
            getEmail: "user/getEmail"
        }),
       infiniteHandler($state) {
      axios
        .get(
          `${SERVER_URL}/api/store/popular?email=${this.sellerEmail}&page=${
            this.page
          }`
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
         `${SERVER_URL}/api/store/popular?email=${this.sellerEmail}&page=${
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
  },
  mounted(){
      this.fetchFirstdata();
  }
}
</script>
<style lang="scss" scoped>
@import "@/assets/store/storeHome.scss";
.store-guideline {
  margin-bottom: 10px;
}
</style>