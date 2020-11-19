<template>
  <div class="container-login">
    <div class="logo">
      <div class="title">
        <h1>양파고</h1>
      </div>

      <ul class="tabs">
        <li @click="moveTab(1)" class="seller current">판매자 로그인</li>
        <li @click="moveTab(2)" class="consumer">구매자 로그인</li>
      </ul>

      <div class="tab-content seller-content current">
        <form class="container-login-form" @submit.prevent="login">
          <div class="input-Email">
            <input
              required
              name="email"
              type="email"
              placeholder="이메일 주소를 입력하세요."
              v-model="userInfo.email"
              autocomplete="email"
            />
          </div>
          <div class="input-password">
            <input
              required
              name="password"
              type="password"
              minlength="8"
              placeholder="비밀번호를 입력해 주세요"
              v-model="userInfo.password"
              autocomplete="current-password"
            />
          </div>

          <div class="login-footer">
            <a
              class="link-repassword"
              href="javascript:void(0)"
              @click="moveToRepassword"
              >비밀번호를 잊으셨나요?</a
            >
            <router-link to="/joinselect" class="link-join">
              아직 회원이 아니시라면?
            </router-link>
          </div>
          <button class="btn btn-login" type="submit">로그인</button>
        </form>
      </div>
      <!-- seller tab end -->
      <div class="tab-content consumer-content">
        <form class="container-login-form" @submit.prevent="login">
          <div class="input-Email">
            <input
              required
              name="email"
              type="email"
              placeholder="이메일 주소를 입력하세요."
              v-model="userInfo.email"
              autocomplete="email"
            />
          </div>
          <div class="input-password">
            <input
              required
              name="password"
              type="password"
              minlength="8"
              placeholder="비밀번호를 입력해 주세요"
              v-model="userInfo.password"
              autocomplete="current-password"
            />
          </div>

          <div class="login-footer">
            <a
              class="link-repassword"
              href="javascript:void(0)"
              @click="moveToRepassword"
              >비밀번호를 잊으셨나요?</a
            >
            <router-link to="/joinselect" class="link-join">
              아직 회원이 아니시라면?
            </router-link>
          </div>
          <button class="btn btn-login" type="submit">로그인</button>
        </form>
      </div>
      <!-- consumer tab end -->
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import axios from "axios";

export default {
  name: "Login",
  data() {
    return {
      userInfo: {
        email: "",
        password: "",
        type: 1
      }
    };
  },
  methods: {
    ...mapGetters({
      getIsLogin: "user/getIsLogin",
      getEmail: "user/getEmail",
      getProfile: "user/getProfile"
    }),
    ...mapActions({
      Login: "user/login",
      fetchUserInfo: "user/fetchUserInfo"
    }),
    moveTab(num) {
      if (num == 1) {
        document.querySelector(".consumer").classList.remove("current");
        document.querySelector(".seller").classList.add("current");
        document.querySelector(".consumer-content").classList.remove("current");
        document.querySelector(".seller-content").classList.add("current");
      } else if (num == 2) {
        document.querySelector(".seller").classList.remove("current");
        document.querySelector(".consumer").classList.add("current");
        document.querySelector(".seller-content").classList.remove("current");
        document.querySelector(".consumer-content").classList.add("current");
      }
      this.userInfo.type = num;
    },
    // 비밀번호 재설정
    moveToRepassword() {
      this.closeLogin();
      document.querySelector(".container-repassword").classList.remove("hide");
    },
    async login() {
      try {
        const res = await axios.get(
          `${this.$apiServer}/auth/userInfo?email=${this.userInfo.email}`
        );
        if (res.data === "") {
          alert("등록되지 않은 이메일 입니다.");
          return;
        }
      } catch (error) {
        console.log(error);
      }

      try {
        const result = await this.Login(this.userInfo);
        if (result) {
          await this.fetchUserInfo(this.getEmail());
          this.$router.push({ name: "Main" });
          alert("로그인 성공");
        } else {
          alert("비밀번호가 틀리거나 다른 분류의 유저입니다.");
        }
      } catch (error) {
        console.log(error);
      }
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

  input {
    background-color: white;
    // background-color: rgb(0, 0, 0, 0) !important;
    border: 1px solid #cccccc;
    border-radius: 5px;
    padding: 8px 5px;
    margin-bottom: 16px;
    width: 400px;
  }

  input[type="password"] {
    margin-bottom: 0px;
  }

  input:focus {
    outline: none !important;
    border: 1px solid cadetblue;
    box-shadow: 0 0 2px cadetblue;
  }

  .btn-login {
    margin-top: 60px;
    width: 400px;
    padding: 8px 10px;
    color: white;
    border-radius: 5px;
    background-color: #6da7ff;
    font-weight: 500;
    font-size: 0.9rem;
    background-color: #99004c;
  }

  .btn-login:hover {
    background-color: rgb(219, 219, 219);
  }

  .login-footer {
    display: flex;
    justify-content: space-between;
    justify-content: baseline;
    margin-top: 12px;
    a:hover {
      opacity: 0.7;
    }
    .link-repassword {
      text-decoration: none;
      color: #ff5651;
    }
    .link-join {
      text-decoration: none;
      color: gray;
    }
  }

  ul.tabs {
    background-color: rgb(0, 0, 0, 0) !important;
    padding: 8px 5px;
    margin-bottom: 16px;
    width: 420px;
    margin-left: 10px;
  }

  ul.tabs li {
    width: 50%;
    background: none;
    color: #222;
    display: inline-block;
    padding: 10px 15px;
    cursor: pointer;
    border: 1px solid #cccccc;
  }

  ul.tabs li.current {
    background: #99004c;
    color: white;
  }

  .tab-content {
    display: none;
    background: #ededed;
    padding: 15px;
  }

  .tab-content.current {
    display: inherit;
  }
}
</style>
