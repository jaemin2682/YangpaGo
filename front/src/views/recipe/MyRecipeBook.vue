<template>
   <div class="recommend">
        <div class="title">
            <h2>나의 레시피 북</h2>
            <button  class="OpenBtn" @click="openDetailModal()">열기</button>
        </div>
        <RecipeBook ref="RecipeBook" :listArray="recipes"/>
    </div>
</template>

<script>
import RecipeBook from "@/components/recipe/RecipeBook.vue";
import axios from "axios";
import { mapGetters } from "vuex";
const SERVER_URL = "http://j3a404.p.ssafy.io:8081";
export default {
    components:{
      RecipeBook,
    },
    data(){
        return{
            pageArray:["테스트 레시피 제목 1", "테스트 레시피 제목 2", "테스트 레시피 제목 3",
            "테스트 레시피 제목 4",
            "테스트 레시피 제목 5",
            "테스트 레시피 제목 6",
            "테스트 레시피 제목 7",
            "테스트 레시피 제목 8",
            "테스트 레시피 제목 9",
            "테스트 레시피 제목 10",
            "테스트 레시피 제목 11",
            "테스트 레시피 제목 12",
            "테스트 레시피 제목 13",],
            recipes:[],
        }
    },
    methods:{
        ...mapGetters({
            getEmail: "user/getEmail"
        }),
        openDetailModal() {
        this.$refs.RecipeBook.showModal();
        },
        fetchRecipeBookData(){
        axios
                .get(
                `${SERVER_URL}/api/recipe/myrecipe?email=${this.getEmail()}`
                )
                .then(response => {
                console.log(response.data);
                this.recipes = response.data;
                })
                .catch(err => console.log(err));
        },
    },
    mounted(){
        this.$refs.RecipeBook.showModal();
    },
    created(){
        this.fetchRecipeBookData();
    }
}
</script>

<style lang="scss" scoped>
@import "@/assets/recipe/recipeRecommend.scss";
</style>