<template>
  <div class="container-home">
    <div class="title">
    <h2> 주문조회 </h2>
    </div>
    <div class="order-info">
        <div class="container">
            <div class="text"> 주문전체 </div>
            <div class="number">{{datas.orderCountAll}}</div> 
        </div>
        <div class="container review-written">
            <div class="text"> 리뷰작성 </div>
            <div class="number">{{datas.reviewCount}}</div> 
        </div>
        <div class="container">
            <div class="text"> 리뷰미작성 </div>
            <div class="number">{{datas.reviewNotCount}}</div> 
        </div>
    </div>
    <template v-for="(data, idx) in datas.purchaseList">
        <CartItem :item=data :key="idx" @getList="fetchAll"/>
    </template>
  </div>
</template>

<script>
import CartItem from "@/components/user/userPurchaseItem.vue";
import axios from "axios";
import { mapGetters } from "vuex";
const SERVER_URL = "http://j3a404.p.ssafy.io:8081";
export default {
    data() {
        return{
        datas : [],
        }
    },
    components:{
        CartItem,
    },
    methods:{
        ...mapGetters({
            getEmail: "user/getEmail"
        }),
        fetchAll() {
            axios.get(`${SERVER_URL}/api/mypage/orderlist?email=${this.getEmail()}`)
                .then(response => {
                    console.log(response.data);
                    this.datas = response.data;
                })
                .catch(err => console.log(err));
        }
    },
    created() {
        this.fetchAll();
    }
}
</script>

<style lang="scss" scoped>
@import "@/assets/user/userpageOrder.scss";
</style>