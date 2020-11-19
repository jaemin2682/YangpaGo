<template>
  <div class="container-home">
    <h2 class="title">과잉 생산 농산물</h2>
     <div class="recommend">
        <FlexProductListItem
         :datas="overItems"
        />
    </div>
  </div>
</template>

<script>
import FlexProductListItem from "@/components/product/FlexProductListItem.vue";
import axios from "axios";
const SERVER_URL = "http://j3a404.p.ssafy.io:8081";
export default {
    components:{
      FlexProductListItem,
    },
    data(){
      return{
        overItems:[],
        page: 1
      }
    },
    methods:{
        fetchOverItems(){
          axios
          .get(
            `${SERVER_URL}/api/main/over`
          )
          .then(response => {
            if (response.data.length) {
              this.overItems = response.data;
            } 
          })
          .catch(err => console.log(err));
        }
    },
    mounted(){
      this.fetchOverItems();
    }
}
</script>

<style lang="scss" scoped>
@import "@/assets/recipe/recipeHome.scss";
</style>