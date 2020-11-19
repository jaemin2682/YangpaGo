<template>
   <div class="recommend">
        <div class="title">
            <h2>추천레시피</h2>
        </div>
        <FlexRecipeItem
         :datas="RecipeData"
        />
        <infinite-loading
        slot="append"
        @infinite="infiniteHandler"
        force-use-infinite-wrapper=".el-table__body-wrapper"
      >
      </infinite-loading>
    </div>
</template>

<script>
import FlexRecipeItem from "@/components/recipe/FlexRecipeItem.vue";
import InfiniteLoading from "vue-infinite-loading";
import { mapGetters } from "vuex";
import axios from "axios";

const SERVER_URL = "http://j3a404.p.ssafy.io:8081";

export default {
    components:{
      FlexRecipeItem,
      InfiniteLoading,
    },
    methods:{
        ...mapGetters({
        getEmail: "user/getEmail"
        }),
       infiniteHandler($state) {
            axios
                .get(
                `${SERVER_URL}/api/recipe/recommend?email=${this.getEmail()}&page=${
                    this.page
                }`
                )
                .then(response => {
                if (response.data.length) {
                    this.RecipeData = this.RecipeData.concat(response.data);
                    $state.loaded();
                    this.page += 1;
                    if (this.RecipeData.length / 10 == 0) {
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
            `${SERVER_URL}/api/recipe/recommend?email=${this.getEmail()}&page=${
                    this.page
                }`
            )
            .then(response => {
            if (response.data.length) {
                this.RecipeData = this.RecipeData.concat(response.data);
                this.page += 1;
            } 
            })
            .catch(err => console.log(err));
        },
    },
    data: function() {
        return {
        data: [],
        RecipeData: [],
        page: 1
            };
    },
     mounted(){
      this.fetchFirstdata();
    }
}
</script>

<style lang="scss" scoped>
@import "@/assets/recipe/recipeRecommend.scss";
</style>