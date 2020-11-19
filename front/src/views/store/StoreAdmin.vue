<template>
    <div class="main-container">
        <!--스토어 홈 페이지-->
        <div class="home">
            <div class="title">
                <h1>스토어 관리</h1>
            </div>
            <div class="store-guideline">
                <p> 스토어 관리 </p>
                <p> * 내 스토어와 등록된 물품을 관리 할 수 있습니다.</p>
            </div>
            <div class="product-upload-container">
                <router-link to="/addproduct">
                <div class="product-upload-btn">
                    새 상품 등록하기
                </div>
                </router-link>
            </div>
            <div class="item-list">
                <!-- 아이템 리스트 표 -->
                <table class="title">
                    <tr>
                        <th class="img-title">사진</th>
                        <th class="name-title">상품명</th>
                        <th class="price-title">가격</th>
                        <th class="button-title">기능</th>
                    </tr>
                </table>
                <template v-for="(data, idx) in ItemData">
                    <AdminItem :item=data :key="idx" v-on:delete-event="deleteReceive" :sellerEmail="sellerEmail"/>
                </template>
                <infinite-loading
                    slot="append"
                    @infinite="infiniteHandler"
                    force-use-infinite-wrapper=".el-table__body-wrapper"
                >
                </infinite-loading>
            </div>
        </div>
    </div>
</template>

<script>
import AdminItem from "@/components/store/AdminItem.vue";
import InfiniteLoading from "vue-infinite-loading";
const SERVER_URL = "http://j3a404.p.ssafy.io:8081";
import axios from "axios";
export default {
    components:{
      AdminItem,
      InfiniteLoading
    },
    data: function() {
        return {
            ItemData: [],
            sellerEmail: this.$route.params.email,
            page: 1
        };
    },
    mounted(){
        // this.sellerEmail = this.getUserInfo().email;
        this.fetchFirstData();
    },
    methods: {
        infiniteHandler($state) {
            axios
                .get(
                `${SERVER_URL}/api/store/all?email=${this.sellerEmail}&page=${
                    this.page
                }`
                )
                .then(response => {
                if (response.data.length) {
                    this.ItemData = this.ItemData.concat(response.data);
                    $state.loaded();
                    this.page += 1;
                    if (this.RecipeData.length / 9 == 0) {
                    $state.complete();
                    }
                } else {
                    $state.complete();
                }
                })
                .catch(err => console.log(err));
            },
        fetchFirstData() {
            axios
                .get(
                `${SERVER_URL}/api/store/all?email=${this.sellerEmail}&page=${
                    this.page
                }`
                )
                .then(response => {
                if (response.data.length) {
                    console.log(response.data);
                    this.ItemData = this.ItemData.concat(response.data);
                    this.page += 1;
                } 
                })
                .catch(err => console.log(err));
            },
        deleteReceive(){
            axios
                .get(
                `${SERVER_URL}/api/store/all?email=${this.sellerEmail}&page=${
                    1
                }`
                )
                .then(response => {
                if (response.data.length) {
                    console.log(response.data);
                    this.ItemData = [];
                    this.ItemData = response.data;
                    this.page += 1;
                } 
                })
                .catch(err => console.log(err));
        }
    }
}
</script>
<style lang="scss" scoped>
@import "@/assets/store/storeAdmin.scss";
.item-list {
    margin-top: 60px;
    padding-left: 180px;
    .title {
        border-top: 1px solid lightgrey;
        border-bottom: 1px solid lightgrey;
        width: 690px;
        height: 40px;
        font-size: 18px;
        .img-title {
            width: 118px;
            padding-left: 9px;
        }
        .name-title {
            width: 230px;
            padding-left: 9px;
        }
        .price-title {
            width: 185px;
        }
        .button-title{
            padding-left: 48px;
        }
    }
}
</style>