<template>
  <div class="navbar-right">
    <div class="navbar-right-background hide" @click="closeNavbar"></div>
    <div class="navbar-right-container">
      <div class="navbar-header">
        <div class="navbar-header-front">
          <div class="btn-navbar-toggle">
            <button @click="closeNavbar">
              <svg
                class="icon-toggle"
                xmlns="http://www.w3.org/2000/svg"
                xmlns:xlink="http://www.w3.org/1999/xlink"
                viewBox="0 0 26 26"
              >
                <path
                  d="M1,17.8v2.4h24v-2.4H1z M1,11.8v2.4h24v-2.4H1z M1,5.8v2.4h24V5.8H1z"
                />
              </svg>
            </button>
          </div>
        </div>
      </div>
      <hr style="width:0px; margin-top:0px" />
      <div class="navbar-body">
          <div class="subtitle">최근 본 상품</div>
          <FlexRecentItem :datas="recentItemList" :isRecipe=1 />
          <div class="subtitle">최근 본 레시피</div>
          <FlexRecentItem :datas="recentRecipeList" :isRecipe=0 />
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import axios from "axios";
import FlexRecentItem from "@/components/common/FlexRecentItem.vue";
const SERVER_URL = "http://j3a404.p.ssafy.io:8081";

export default {
  props: ["showNavbar"],
  components:{
      FlexRecentItem,
  },
  data: function() {
    return {
      isCollapsed: true,
      followings: [],
      followShowLimit: 5,
      constfollowShowLimit: 5,
      recentRecipeList:[],
      recentItemList:[],
    };
  },
  methods: {
    ...mapGetters({
      getEmail: "user/getEmail"
    }),
    closeNavbar() {
      const navbar_right = document.querySelector(".navbar-right");
      const background_right = document.querySelector(".navbar-right-background");
      const body = document.querySelector("body");

      background_right.classList.add("hide");
      navbar_right.setAttribute("style", "right: -300px");
      body.classList.remove("hide-scroll");
    },
    async fetchRecentList(){
        if (this.getEmail() === "") return;

        let result = await axios.get(
            `${SERVER_URL}/api/main/search/view/recipe/{email}?email=${this.getEmail()}`
        );
        console.log(result);
        if (result) {
            this.recentRecipeList = result.data;
        }

        let result2 = await axios.get(
            `${SERVER_URL}/api/main/search/view/product/{email}?email=${this.getEmail()}`
        );
        console.log(result2);
        if (result2) {
            this.recentItemList = result2.data;
        }
    },
    moveToFollowingProfile(email) {
      this.$router.push({ name: "Blog", params: { email: email } });
      this.closeNavbar();
    }
  },
  mounted() {
    this.fetchRecentList();
  }
};
</script>

<style scoped lang="scss">
@import "@/assets/common/NavbarRight.scss";
</style>
