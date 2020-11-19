<template>
  <div class="container-base">
      <UserPageHeader />
      <hr/>
      <div class="store-container">
        <div class="navbar-container">
            <UserPageNavbar/>
        </div>
        <div class="main-container">
            <router-view></router-view>
        </div>
      </div>
  </div>
</template>

<script>
import UserPageHeader from "@/components/user/UserPageHeader.vue";
import UserPageNavbar from "@/components/user/UserPageNavbar.vue";
export default {
  name: "UserPage",
  components: {
    UserPageHeader,
    UserPageNavbar,
  }
};
</script>

<style scoped lang="scss">
 @import "@/assets/recipe/recipe.scss";
</style>

