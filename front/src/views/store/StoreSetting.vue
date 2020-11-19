<template>
  <div class="container-login">
    <div class="logo">
      <div class="title">
        <h1>스토어 설정</h1>
      </div>
      <div>
        <div class="tab-content consumer-content">
          <form class="container-login-form" @submit.prevent="addstore">
            <div class="container-input">
              <div class="label">
                <label>상호명</label>
              </div>
              <input
                required
                name="store_name"
                placeholder="상호명"
                v-model="storeinfo.store_name"
              />
            </div>
             <div class="container-input">
              <div class="label">
                <label>대표자</label>
              </div>
              <input
                required
                name="rep_name"
                placeholder="대표자"
                v-model="storeinfo.rep_name"
              />
            </div>
            <div class="container-input">
              <div class="label">
                <label>사업자 구분</label>
              </div>
              <input
                required
                name="type"
                placeholder="사업자 구분"
                v-model="storeinfo.type"
              />
            </div>
            <div class="container-input">
              <div class="label">
                <label>사업자등록번호</label>
              </div>
              <input
                required
                name="number"
                placeholder="사업자등록번호"
                v-model="storeinfo.number"
              />
            </div>
            <div class="container-input">
              <div class="label">
                <label>전화번호</label>
              </div>
              <input
                required
                name="tell"
                placeholder="전화번호"
                v-model="storeinfo.tell"
              />
            </div>
            <div class="container-input">
              <div class="label">
                <label>영업소재지</label>
              </div>
              <input
                required
                name="address"
                placeholder="영업소재지"
                v-model="storeinfo.address"
              />
            </div>
            <div class="container-input">
              <div class="label">
                <label>농장주소</label>
              </div>
              <input
                required
                name="origin"
                placeholder="농장주소"
                v-model="storeinfo.origin"
              />
            </div>
            <button class="btn btn-join" type="submit">스토어 정보 설정하기</button>
          </form>
        </div>
        <!-- consumer tab end -->
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import axios from "axios";
const SERVER_URL = "http://j3a404.p.ssafy.io:8081";

export default {
  mounted() {
    this.storeinfo.email = this.getUserInfo().email;
  },
  methods: {
    ...mapGetters({
      getUserInfo: "user/getUserInfo"
    }),
    addstore() {
        console.log(this.storeinfo);'='
      axios
          .post(
                `${SERVER_URL}/api/store/add`, this.storeinfo
                )
                .then(response => {
                console.log(response.data);
                })
          .catch(err => console.log(err));
        this.$router.push({ name: "Main" });
    },
    
  },
  created() {

  },
  data: () => {
    return {
      storeinfo: {
        email: "",
        store_name: "",
        address: "",
        number: "",
        origin: "",
        rep_name: "",
        tell: "",
        type: "",
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
  width: 200vh;
  height: 100vh;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0);

  .title {
    margin: 100px 50px;
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
    width: 100%;
    height: 20px;
  }

  input:focus {
    outline: none !important;
    border: 1px solid cadetblue;
    box-shadow: 0 0 2px cadetblue;
  }

  .btn-join {
    width: 100%;
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

  .moveLogin {
    margin-top: 10px;
    text-align: right;
  }

  .container-login-form{
    display: flex;
    background: #ededed;
    padding: 15px;
    flex-wrap: wrap;
    flex-direction: row;
    flex-basis: auto;
    width: 80%;
    align-self:center;
    padding-left: 50vh;
  }

  .container-input{
    width:50%;
    padding:10px;
  }
}
</style>
