<template>
  <div class="container-home">
    <div class="title">
    <h2> 최근 본 상품 </h2>
    </div>
    <template v-for="(data, idx) in datas">
        <CartItem :item=data :key="idx" :pagetype="recent" @getList="getList"/>
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
            datas: [],
            recent: {"type": "recent"}
        }
    },
    methods: {
        ...mapGetters({
            getEmail: "user/getEmail"
        }),
        getList() {
            axios.get(`${SERVER_URL}/api/mypage/recentlist?email=${this.getEmail()}`)
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