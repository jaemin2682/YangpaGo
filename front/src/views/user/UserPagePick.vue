<template>
  <div class="container-home">
    <div class="title">
    <h2> 찜한상품 </h2>
    </div>
    <template v-for="(data, idx) in datas">
        <CartItem :item=data :key="idx" :pagetype="pick" @getList="getList"/>
    </template>
  </div>
</template>

<script>
import CartItem from "@/components/user/userPageItem.vue";
import axios from "axios";
import { mapGetters } from "vuex";
const SERVER_URL = "http://j3a404.p.ssafy.io:8081";
export default {
    data() {
        return{
            datas : [],
            pick: {"type": "pick"}
        }
    },
    methods: {
        ...mapGetters({
            getEmail: "user/getEmail",
            getUserId: "user/getUserId"
        }),
        getList() {
            axios.get(`${SERVER_URL}/api/mypage/goodlist?email=${this.getEmail()}`)
                .then(response => {
                    console.log(response.data);
                    this.datas = response.data;
                })
                .catch(err => console.log(err));
        }
    },
    created() {
        this.getList();
    },
    components:{
        CartItem,
    }
}
</script>

<style lang="scss" scoped>
@import "@/assets/user/userpagePick.scss";
</style>