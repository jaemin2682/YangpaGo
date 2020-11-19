<template>
  <div class="container-home">
    <h2 class="title">'{{word}}' 검색 결과</h2>
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
import { mapGetters } from "vuex";
const SERVER_URL = "http://j3a404.p.ssafy.io:8081";
export default {
    components:{
      FlexProductListItem,
      InfiniteLoading,
    },
    data(){
      return{
        products:[],
        page: 1,
        word: ""
      }
    },
    methods:{
      ...mapGetters({
            getEmail: "user/getEmail"
        }),
        infiniteHandler($state) {
        axios
            .get(
            `${SERVER_URL}/api/productlist/search?name=${this.word}&page=${this.page}`
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
            `${SERVER_URL}/api/productlist/search?name=${this.word}&page=${this.page}`
            )
            .then(response => {
            if (response.data.length) {
                this.products = this.products.concat(response.data);
                this.page += 1;
            } 
            })
            .catch(err => console.log(err));
        },
    },
    created(){
        const params = new URL(document.location).searchParams;
        console.log(params.get('word'));
        this.word = params.get('word');
    },
    mounted(){
        
        this.fetchFirstdata();
    }
}
</script>

<style lang="scss" scoped>
@import "@/assets/recipe/recipeHome.scss";
</style>