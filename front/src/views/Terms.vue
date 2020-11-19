<template>
  <div class="container">
    <div class="logo">
      <div class="title">
        <h1>가입 약관 동의</h1>
      </div>
      <div>
        <button type="button" class="btn-accept" @click="btnAgree">
          V &nbsp; &nbsp; 모두 동의합니다
        </button>
      </div>
      <div class="subtitle">
        서비스 이용약관과 개인정보 수집 및 이용 방침에 동의합니다.
      </div>
      <hr />
      <div class="subtitle">
        <span id="yangpago">양파고</span> 이용약관, 개인정보 수집 이용 동의
      </div>
      <div class="container-term">
        <div class="term">
          <span class="check">
            <input
              v-model="isTerm1"
              type="checkbox"
              class="term"
              @click="checkone(1)"
            />
          </span>
          <span class="term-link" @click="conditions">
            양파고 서비스 이용 약관 동의(필수)
          </span>
        </div>
        <div class="term">
          <span class="check">
            <input
              v-model="isTerm2"
              type="checkbox"
              class="term"
              @click="checkone(2)"
            />
          </span>
          <span class="term-link" @click="agreement">
            양파고 개인정보 수집 및 이용 방침 동의(필수)
          </span>
        </div>
      </div>
      <button class="btn btn-join" @click="moveJoin">회원가입</button>
      <div class="moveLogin">
        이미 계정이 있나요?
        <router-link to="/login">로그인</router-link>
        하기
      </div>
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
  mounted() {
    this.type = this.$route.query.type;
  },
  methods: {
    ...mapActions({
      Join: "user/join"
    }),
    conditions() {
      document.querySelector(".container-conditions").classList.remove("hide");
    },
    agreement() {
      document.querySelector(".container-agreement").classList.remove("hide");
    },
    checkone(num) {
      if (
        (this.isTerm1 && !this.isTerm2 && num == 2) ||
        (this.isTerm2 && !this.isTerm1 && num == 1)
      ) {
        document.querySelector(".btn-accept").classList.add("clicked");
      } else {
        document.querySelector(".btn-accept").classList.remove("clicked");
      }
    },
    btnAgree() {
      if (!document.querySelector(".clicked")) {
        this.isTerm1 = true;
        this.isTerm2 = true;
        document.querySelector(".btn-accept").classList.add("clicked");
      } else {
        this.isTerm1 = false;
        this.isTerm2 = false;
        document.querySelector(".btn-accept").classList.remove("clicked");
      }
    },
    moveJoin() {
      if (!this.isTerm1 || !this.isTerm2) alert("모든 약관에 동의해 주세요.");
      else this.$router.push({ name: "Join", query: { type: this.type } });
    }
  },
  data: () => {
    return {
      isTerm1: false,
      isTerm2: false,
      type: 0
    };
  }
};
</script>

<style scoped lang="scss">
/** base */
@import "@/assets/common/Base.scss";
@import "@/assets/_variables.scss";
// $minimumWidth: 950px;

.container {
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
    margin: 10px;
    text-align: left;
  } 

  button {
    width: 300px;
    height: 100px;
    background: white;
    margin-top: 20px;
    color: gray;
    font-size: 20px;
    border: 1px solid gray;
    // box-shadow: 1px 1px 1px 1px gray;
  }

  input:focus {
    outline: none !important;
    border: 1px solid cadetblue;
    box-shadow: 0 0 2px cadetblue;
  }

  .container-term {
    margin-top: 40px;
    margin-bottom: 15px;
    margin-left: 10px;
    // display: flex;
    // justify-content: space-between;
    // justify-content: baseline;
    .term-link:hover {
      opacity: 0.7;
      cursor: pointer;
    }
    .link-repassword {
      text-decoration: none;
      color: #ff5651;
    }
  }

  .term {
    font-size: 12px;
    // width: 380px;
    height: 40px;
    text-align: left;
    margin-left: 0px;
    vertical-align: center;
    // float: left;
  }

  .term-link {
    font-size: 16px;
    vertical-align: center;
  }

  .check {
    height: 50px;
    vertical-align: center;
  }

  input[type="checkbox"] {
    background: black;
    width: 20px;
    height: 20px;
    border-radius: 0px;
    margin: 0px;
    padding: 0px;
    line-height: 100px;
  }

  .btn-join {
    width: 410px;
    height: 35px;
    padding: 8px 10px;
    margin-top: 0px;
    color: white;
    border-radius: 5px;
    background-color: #6da7ff;
    font-weight: 500;
    font-size: 0.9rem;
    background-color: #99004c;
  }

  .btn-join:hover {
    background-color: rgb(219, 219, 219);
  }

  #yangpago {
    color: #99004c;
  }
  .subtitle {
    margin-bottom: 30px;
    text-align: left;
    font-size: 15px;
  }
  .btn-accept {
    height: 40px;
    width: 430px;
    margin-bottom: 30px;
    text-align: center;
  }

  .clicked {
    color: red;
  }

  hr {
    margin: 30px 0px 30px 0px;
  }

  .moveLogin {
    margin-top: 10px;
    text-align: right;
  }

  a {
    color: dodgerblue;
    text-decoration: none;
    cursor: pointer;
    &:hover {
      opacity: 0.7;
    }
  }
}
</style>
