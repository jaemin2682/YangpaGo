<template>
  <div class="container-home">
    <h2 class="title"> 내 정보 </h2>
    <table class="info-table">
        <tr class="row"> 
            <th>가입정보</th>
            <td v-if="this.type == 1">판매자</td>
            <td v-if="this.type == 2">구매자</td>
        </tr>
        <tr class="row">
            <th>이메일</th>
            <td>{{this.email}}</td>
        </tr>
        <tr class="row">
            <th>스토어개설</th>
            <td v-if="this.isStore == 0">스토어 개설 미완료</td>
            <td v-if="this.isStore == 1">스토어 개설 완료</td>
        </tr>
    </table>
    <button @click="resetPassword">비밀번호 변경 </button>
    <button @click="unregister">회원 탈퇴 </button>
  </div>
</template>

<script>
import axios from "axios";
import { mapActions, mapGetters } from "vuex";
const SERVER_URL = "http://j3a404.p.ssafy.io:8081";
export default {
    data() {
        return {
            type:0,
            email:"",
            isStore:0
        };
    },
    methods: {
        ...mapGetters({
            getEmail: "user/getEmail"
        }),
        ...mapActions({
            logout: "user/logout",
            Login: "user/login",
        }),
        getInfo() {
            axios.get(`${SERVER_URL}/api/mypage/info?email=${this.getEmail()}`)
                .then(response => {
                    this.type = response.data.type;
                    this.email = response.data.email;
                    this.isStore = response.data.isStore;
                })
                .catch(err => console.log(err));
        },
        resetPassword() {
            var newPassword = prompt("변경하실 새 비밀번호를 입력하세요.")
            if(newPassword) {
                axios.post(`${SERVER_URL}/api/auth/resetPassword`, {
                    email: this.getEmail(),
                    password: newPassword
                })
                    .then(response => {
                        alert("비밀번호 변경이 완료되었습니다.");
                        console.log(response);
                    })
                    .catch(err => {
                        alert("비밀번호 변경 실패");
                        console.log(err);
                    });
            }
        },
        unregister() {
            axios.get(`${SERVER_URL}/api/auth/unregister?email=${this.getEmail()}`)
                .then(response => {
                    console.log(response.data)
                    alert("계정이 성공적으로 삭제되었습니다.");
                })
                .catch(err => console.log(err));
                this.logout();
                this.$router.push({ name: "Main" });
        }
    },
    created() {
        this.getInfo();
    }
}
</script>

<style lang="scss" scoped>
@import "@/assets/user/userpageInfo.scss";
</style>