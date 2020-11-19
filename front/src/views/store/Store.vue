<template>
  <div class="container-base">
     <StoreHeader :storeInfo="storeinfo"/>
     <hr/>
     <div class="store-container">
        <div class="navbar-container">
            <StoreNavbar :isIseller="isIseller"/>
        </div>
        <div class="main-container">
            <router-view></router-view>
        </div>
      </div>
  </div>
</template>

<script>
import StoreHeader from "@/components/store/StoreHeader.vue";
import StoreNavbar from "@/components/store/StoreNavbar.vue";
import axios from "axios";
import { mapGetters } from "vuex";
const SERVER_URL = "http://j3a404.p.ssafy.io:8081";
export default {
  name: "Store",
  components: {
    StoreHeader,
    StoreNavbar,
  },
  data(){
    return{
      sellerEmail: this.$route.params.email,
      storeinfo:[],
      isIseller:false,
    }
  },
  mounted(){
    this.fetchIsISeller();
  },
  methods:{
    ...mapGetters({
      getUserInfo: "user/getUserInfo"
    }),
    fetchStoreData(){
        axios
          .get(
                `${SERVER_URL}/api/store/storeInfo?email=${this.sellerEmail}`
                )
                .then(response => {
                console.log(response.data);
                this.storeinfo = response.data;
                })
          .catch(err => console.log(err));
    },
    fetchIsISeller(){
      let email = this.getUserInfo().email;
      if(email===this.sellerEmail)
        this.isIseller = true;
    },
  },
  created(){
    this.fetchStoreData();
  }
};
</script>

<style scoped lang="scss">
 @import "@/assets/recipe/recipe.scss";
</style>

