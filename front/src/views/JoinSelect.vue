<template>
  <div class="container-login">
    <div class="logo">
      <div class="title">
        <h1>양파고</h1>
      </div>
      <router-link to="/terms?type=1" class="link-join">
        <button type="button">판매자 회원가입</button>
      </router-link>

      <router-link to="/terms?type=2" class="link-join">
        <button type="button">구매자 회원가입</button>
      </router-link>
    </div>

    <ConditionsModal />
    <AgreementModal />
  </div>
</template>

<script>
import { mapActions } from "vuex";
import ConditionsModal from "@/components/user/ConditionsModal.vue";
import AgreementModal from "@/components/user/AgreementModal.vue";

export default {
  components: {
    ConditionsModal,
    AgreementModal
  },
  methods: {
    ...mapActions({
      Join: "user/join"
    }),
    moveToLogin() {
      alert("move"); // 나중에
    }
  }
};
</script>

<style scoped lang="scss">
/** base */
@import "@/assets/common/Base.scss";
@import "@/assets/_variables.scss";
// $minimumWidth: 950px;

.container-login {
  position: fixed;
  z-index: 0;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0);

  .title {
    margin: 50px;
    color: #99004c;
  } 

  button {
    width: 300px;
    height: 300px;
    background: white;
    margin: 50px;
    color: gray;
    font-size: 20px;
    box-shadow: 1px 1px 1px 1px gray;
  }
}
</style>
