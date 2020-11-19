<template>
  <div class="container-login">
    <div class="logo">
      <div class="title">
        <h1>양파고</h1>
      </div>
      <div v-if="this.$route.query.type == 1">
        <ul class="tabs">
          <li @click="moveTab(1)" class="seller current">판매자 회원가입</li>
          <li @click="moveTab(2)" class="consumer">구매자 회원가입</li>
        </ul>
        <div class="tab-content seller-content current">
          <form class="container-login-form" @submit.prevent="join">
            <div class="nickname">
              <div class="label">
                <label>닉네임</label>
              </div>
              <input
                required
                name="nickname"
                placeholder="닉네임"
                v-model="nickname"
                autocomplete="email"
              />
            </div>
            <div class="email">
              <div class="label">
                <label>이메일 주소</label>
              </div>
              <input
                required
                name="email"
                type="email"
                placeholder="이메일 주소를 입력하세요."
                v-model="email"
                autocomplete="email"
              />
            </div>
            <div class="password">
              <div class="label">
                <label>비밀번호</label>
              </div>
              <input
                v-model="password"
                required
                minlength="8"
                maxlength="20"
                type="password"
                name="password"
                placeholder="비밀번호를 입력하세요"
                pattern="^((?=\S*?[A-Za-z0-9]).{0,})\S$"
                title="영문 숫자 혼합 8자 이상"
                autocomplete="new-password"
              />
            </div>
            <div class="password-confirm">
              <div class="label pc">
                <label>비밀번호 확인</label>
              </div>
              <input
                v-model="passwordConfirm"
                required
                type="password"
                name="password-confirm"
                placeholder="비밀번호를 한번 더 입력하세요"
                class="password-confirm"
                autocomplete="new-password"
              />
            </div>
            <button class="btn btn-join" type="submit">회원가입</button>
            <div class="moveLogin">
              이미 계정이 있나요?
              <router-link to="/login">로그인</router-link>
              하기
            </div>
          </form>
        </div>
        <!-- seller tab end -->

        <div class="tab-content consumer-content">
          <form class="container-login-form" @submit.prevent="join">
            <div class="nickname">
              <div class="label">
                <label>닉네임</label>
              </div>
              <input
                required
                name="nickname"
                placeholder="닉네임"
                v-model="nickname"
                autocomplete="email"
              />
            </div>
            <div class="email">
              <div class="label">
                <label>이메일 주소</label>
              </div>
              <input
                required
                name="email"
                type="email"
                placeholder="이메일 주소를 입력하세요."
                v-model="email"
                autocomplete="email"
              />
            </div>
            <div class="password">
              <div class="label">
                <label>비밀번호</label>
              </div>
              <input
                v-model="password"
                required
                minlength="8"
                maxlength="20"
                id="password"
                type="password"
                name="password"
                placeholder="비밀번호를 입력하세요"
                pattern="^((?=\S*?[A-Za-z0-9]).{0,})\S$"
                title="영문 숫자 혼합 8자 이상"
                autocomplete="new-password"
              />
            </div>
            <div class="password-confirm">
              <div class="label pc">
                <label>비밀번호 확인</label>
              </div>
              <input
                v-model="passwordConfirm"
                required
                id="password-confirm"
                type="password"
                name="password-confirm"
                placeholder="비밀번호를 한번 더 입력하세요"
                class="password-confirm"
                autocomplete="new-password"
              />
            </div>
            <button class="btn btn-join" type="submit">회원가입</button>
            <div class="moveLogin">
              이미 계정이 있나요?
              <router-link to="/login">로그인</router-link>
              하기
            </div>
          </form>
        </div>
        <!-- consumer tab end -->
      </div>

      <div v-if="this.$route.query.type == 2">
        <ul class="tabs">
          <li @click="moveTab(1)" class="seller">판매자 회원가입</li>
          <li @click="moveTab(2)" class="consumer current">구매자 회원가입</li>
        </ul>
        <div class="tab-content seller-content">
          <form class="container-login-form" @submit.prevent="join">
            <div class="nickname">
              <div class="label">
                <label>닉네임</label>
              </div>
              <input
                required
                name="nickname"
                placeholder="닉네임"
                v-model="nickname"
                autocomplete="email"
              />
            </div>
            <div class="email">
              <div class="label">
                <label>이메일 주소</label>
              </div>
              <input
                required
                name="email"
                type="email"
                placeholder="이메일 주소를 입력하세요."
                v-model="email"
                autocomplete="email"
              />
            </div>
            <div class="password">
              <div class="label">
                <label>비밀번호</label>
              </div>
              <input
                v-model="password"
                required
                minlength="8"
                maxlength="20"
                type="password"
                name="password"
                placeholder="비밀번호를 입력하세요"
                pattern="^((?=\S*?[A-Za-z0-9]).{0,})\S$"
                title="영문 숫자 혼합 8자 이상"
                autocomplete="new-password"
              />
            </div>
            <div class="password-confirm">
              <div class="label pc">
                <label>비밀번호 확인</label>
              </div>
              <input
                v-model="passwordConfirm"
                required
                type="password"
                name="password-confirm"
                placeholder="비밀번호를 한번 더 입력하세요"
                class="password-confirm"
                autocomplete="new-password"
              />
            </div>
            <button class="btn btn-join" type="submit">회원가입</button>
            <div class="moveLogin">
              이미 계정이 있나요?
              <router-link to="/login">로그인</router-link>
              하기
            </div>
          </form>
        </div>
        <!-- seller tab end -->

        <div class="tab-content consumer-content current">
          <form class="container-login-form" @submit.prevent="join">
            <div class="nickname">
              <div class="label">
                <label>닉네임</label>
              </div>
              <input
                required
                name="nickname"
                placeholder="닉네임"
                v-model="nickname"
                autocomplete="email"
              />
            </div>
            <div class="email">
              <div class="label">
                <label>이메일 주소</label>
              </div>
              <input
                required
                name="email"
                type="email"
                placeholder="이메일 주소를 입력하세요."
                v-model="email"
                autocomplete="email"
              />
            </div>
            <div class="password">
              <div class="label">
                <label>비밀번호</label>
              </div>
              <input
                v-model="password"
                required
                minlength="8"
                maxlength="20"
                id="password"
                type="password"
                name="password"
                placeholder="비밀번호를 입력하세요"
                pattern="^((?=\S*?[A-Za-z0-9]).{0,})\S$"
                title="영문 숫자 혼합 8자 이상"
                autocomplete="new-password"
              />
            </div>
            <div class="password-confirm">
              <div class="label pc">
                <label>비밀번호 확인</label>
              </div>
              <input
                v-model="passwordConfirm"
                required
                id="password-confirm"
                type="password"
                name="password-confirm"
                placeholder="비밀번호를 한번 더 입력하세요"
                class="password-confirm"
                autocomplete="new-password"
              />
            </div>
            <button class="btn btn-join" type="submit">회원가입</button>
            <div class="moveLogin">
              이미 계정이 있나요?
              <router-link to="/login">로그인</router-link>
              하기
            </div>
          </form>
        </div>
        <!-- consumer tab end -->
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import axios from "axios";

export default {
  mounted() {
    this.type = this.$route.query.type;
  },
  methods: {
    ...mapActions({
      Join: "user/join",
      Login: "user/login",
      fetchUserInfo: "user/fetchUserInfo"
    }),
    moveToLogin() {
      alert("move"); // 나중에
    },
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
      this.type = num;
    },
    async join() {
      //닉네임 확인 api 만들기
      if (!(this.password === this.passwordConfirm)) {
        alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.")
      }
      try {
        const result = await axios.get(
          `${this.$apiServer}/auth/checkEmailDuplication?email=${this.email}`
        );
        if (result.status === 200) {
          try{
            const JoinResult = await this.Join({
              email: this.email,
              password: this.password,
              nickname: this.nickname,
              type: this.type
              });
              if(JoinResult){
                 alert("회원가입을 축하합니다.");
              }

              //회원가입 성공 시 로그인
              try{
                  const LoginResult = await this.Login({
                                        email: this.email,
                                        password: this.password,
                                        nickname: this.nickname,
                                        type: this.type
                                      });
                  if(LoginResult){
                      alert("자동 로그인 성공");
                      if(this.type == 1){ //구매자
                        this.$router.push({ name: "StoreSetting" });
                      }
                      else this.$router.push({ name: "Main" });
                  }
              }catch(error){
                console.log(error);
              }
          }catch (error) {
          console.log(error);
          }
         
          // 회원가입 성공 시 로그인도
          // ㅠ 못하겠
        }
      } catch (error) {
        alert("이미 사용중인 이메일 입니다.");
      }
    }
  },
  data: () => {
    return {
      email: "",
      nickname: "",
      password: "",
      passwordConfirm: "",
      isTerm: false,
      type: 1,
      dom: {
        passwordConfirmErrMsg: ""
      },
      userInfo: {
        email: "",
        password: "",
        type: 1
      }
    };
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

  .label {
    text-align: left;
    font-size: 9pt;
    margin-left: 3px;
    margin-bottom: 5px;
  }

  .pc {
    padding-top: 15px;
  }

  input {
    background-color: white;
    // background-color: rgb(0, 0, 0, 0) !important;
    border: 1px solid #cccccc;
    border-radius: 5px;
    padding: 8px 5px;
    margin-bottom: 16px;
    width: 400px;
    height: 20px;
  }

  .term {
    font-size: 12px;
    width: 380px;
    text-align: left;
    margin-left: 10px;
    margin-top: 2px;
    vertical-align: center;
  }

  input[type="checkbox"] {
    background: black;
    width: 20px;
    height: 20px;
    border-radius: 10px;
    margin: 0px;
    padding: 0px;
  }
  input[type="password"] {
    margin-bottom: 0px;
  }

  input:focus {
    outline: none !important;
    border: 1px solid cadetblue;
    box-shadow: 0 0 2px cadetblue;
  }

  .btn-join {
    width: 410px;
    padding: 8px 10px;
    margin-top: 30px;
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

  .container-term {
    margin-top: 40px;
    margin-bottom: 15px;
    margin-left: 10px;
    display: flex;
    justify-content: space-between;
    justify-content: baseline;
    a:hover {
      opacity: 0.7;
    }
    .link-repassword {
      text-decoration: none;
      color: #ff5651;
    }
  }

  .moveLogin {
    margin-top: 10px;
    text-align: right;
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
