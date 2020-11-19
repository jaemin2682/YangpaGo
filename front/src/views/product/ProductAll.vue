<template>
  <div class="container-home">
    <h2 class="title">전체 상품 리스트</h2>
     <div class="recommend">
        <FlexProductListItem
         :datas="products"
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
import FlexProductListItem from "@/components/product/FlexProductListItem.vue";
import InfiniteLoading from "vue-infinite-loading";
import axios from "axios";
const SERVER_URL = "http://j3a404.p.ssafy.io:8081";

export default {
    components:{
      FlexProductListItem,
      InfiniteLoading,
    },
    data: function() {
    return {
      data: [],
      products: [],
      page: 1
        };
    },
  methods:{
       infiniteHandler($state) {
      axios
        .get(
          `${SERVER_URL}/api/productlist/all?page=${
            this.page
          }`
        )
        .then(response => {
          if (response.data.length) {
            this.products = this.products.concat(response.data);
            $state.loaded();
            this.page += 1;
            if (this.products.length / 9 == 0) {
              $state.complete();
            }
            console.log("여기"+response.data.length)
          } else {
            $state.complete();
          }
        })
        .catch(err => console.log(err));
    },
    fetchFirstdata(){
        axios
        .get(
          `${SERVER_URL}/api/productlist/all?page=${
            this.page
          }`
        )
        .then(response => {
          if (response.data.length) {
              console.log(response.data)
            this.products = this.products.concat(response.data);
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
@import "@/assets/recipe/recipeHome.scss";
</style>