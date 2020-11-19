<template>
  <div class="container-home">
    <h2 class="title">레시피 홈</h2>
     <div class="recommend">
        <div class="subtitle">
            <p>추천레시피</p>
        </div>
        <FlexRecipeItem
         :datas="recommend_recipe"
        />
    </div>
    <div class="popular">
      <div class="subtitle">
            <p>인기레시피</p>
        </div>
        <FlexRecipeItem
         :datas="popular_recipe"
        />
    </div>

  </div>
</template>

<script>
import FlexRecipeItem from "@/components/recipe/FlexRecipeItem.vue";
import axios from "axios";
import { mapGetters } from "vuex";
const SERVER_URL = "http://j3a404.p.ssafy.io:8081";
export default {
    components:{
      FlexRecipeItem,
    },
    data(){
      return{
        recommend_recipe:[],
        popular_recipe:[],
      }
    },
    methods:{
      ...mapGetters({
            getEmail: "user/getEmail"
        }),
      fetchRecipeData(){
        axios
                .get(
                `${SERVER_URL}/api/recipe/recipeHome?email=${this.getEmail()}`
                )
                .then(response => {
                console.log(response.data);
                this.recommend_recipe = response.data.recommend_recipe;
                this.popular_recipe = response.data.popular_recipe;
                })
                .catch(err => console.log(err));
        },
    },
    mounted(){
      this.fetchRecipeData();
    }
}
</script>

<style lang="scss" scoped>
@import "@/assets/recipe/recipeHome.scss";
img {
  width: 50px;
  height: 50px;
}
</style>