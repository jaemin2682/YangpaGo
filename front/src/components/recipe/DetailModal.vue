<template>
  <div class="modal" v-if="show">
    <div class="background" @click="closeModal"></div>
    <div class="modal-content">
      <div class="recipe-body">
        <div class="recipe-title">
          <h1>{{this.name}}</h1>
          <div class="pick-btn" v-if="isGood==0" @click="bookmarkRecipe">
            <img src="@/assets/images/icons/crops/nonbookmark.png"/>
          </div>
          <div class="unpick-btn" v-if="isGood==1" @click="unbookmarkRecipe">
            <img src="@/assets/images/icons/crops/bookmark.png"/>
          </div>
        </div>
        <div class="recipe-needs">
          <div class="recipe-subtitle"> 재료 목록 </div>
          <FlexRecipeNeeds
          :data="ingredient"/>
        </div>
        <hr/>
        <div class="recipe-steps">
          <div class="recipe-subtitle"> 조리방법 </div>
          <FlexRecipeSteps
          :steps="step"/>
        </div>
        <div class="recipe-recommends">
          <div class="recipe-subtitle"> 추천 레시피 </div>
          <FlexRecipeCard
          :datas="recommend_recipe"
          />
        </div>
      </div>
      <div class="recipe-img">
        <img :src="picture"/>
      </div>
    </div>
    <div @click="show = false" class="closeModalBtn">
      <img class="closeImg" src="@/assets/images/icons/close.png"/>
    </div>
  </div>
</template>

<script>
import FlexRecipeNeeds from "@/components/recipe/FlexRecipeNeeds.vue";
import FlexRecipeSteps from "@/components/recipe/FlexRecipeSteps.vue";
import FlexRecipeCard from "@/components/recipe/FlexRecipeCard.vue";
import axios from "axios";
import { mapGetters } from "vuex";
const SERVER_URL = "http://j3a404.p.ssafy.io:8081";
export default {
  data() {
    return {
      command: null,
      show: false,
      tab: 1,
      needs: ["돼지고기앞다리살", "고춧가루", "참기름"," 참깨", "익은 배추김치", "대파", "후춧가루", "고추장", "다진마늘", "올리고당"],
      name:"",
      picture:"",
      type:"",
      isGood:0,
      ingredient:[],
      step:[],
      relative_product:[],
      recommend_recipe:[],
    };
  },
  props:{
    id:Number,
  },
  methods: {
     ...mapGetters({
        getEmail: "user/getEmail"
    }),
    showModal(command) {
      // Add the sent command
      this.fetchRecipeData();
      console.dir(command);
      this.command = command;
      this.show = true;
    },
    closeModal() {
      this.show = false;
      this.imageSrc = [];
      this.tab = 1;
    },
    fetchRecipeData(){
      axios
            .get(
            `${SERVER_URL}/api/recipe/detail?email=${this.getEmail()}&id=${
                this.id
            }`
            )
            .then(response => {
              this.name = response.data.name;
              this.picture = response.data.picture;
              this.type = response.data.type;
              this.isGood = response.data.isGood;
              this.ingredient = response.data.ingredient;
              this.step = response.data.step;
              this.relative_product = response.data.relative_product;
              this.recommend_recipe = response.data.recommend_recipe;
            })
            .catch(err => console.log(err));
    },
    bookmarkRecipe(){
       axios
            .post(
            `${SERVER_URL}/api/recipe/myrecipe?email=${this.getEmail()}&id=${
                this.id
            }`
            )
            .then(response => {
              console.log(response.data);
              this.isGood=1;
            })
            .catch(err => console.log(err));
    },
    unbookmarkRecipe(){
      axios
            .delete(
            `${SERVER_URL}/api/recipe/myrecipe?email=${this.getEmail()}&id=${
                this.id
            }`
            )
            .then(response => {
              console.log(response.data);
              this.isGood=0;
            })
            .catch(err => console.log(err));
    }
  },
  created(){
     
  },
  components: {
    FlexRecipeNeeds,
    FlexRecipeSteps,
    FlexRecipeCard,
  },
};
</script>


<style scoped lang="scss">
 @import "@/assets/recipe/DetailModal.scss";
.background {
  position: fixed;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.4);
}
.closeImg{
  width: 100px;
  height: 100px;
}
</style>