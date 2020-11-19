<template>
    <div class="main-container">
        <!--스토어 홈 페이지-->
        <div class="home">
            <div class="title">
                <h1>스토어 홈</h1>
            </div>
            <div class="home-price">
                <p> 시세보다 저렴한 상품 </p>
                <div class="home-price-item">
                    <StoreItem2 class="flexitems" :datas="datas.cheapList.slice(0,2)" />
                    <div class="more">
                        <img src="@/assets/images/icons/next.svg" @click="$router.push('price')"/>
                    </div>
                </div>
            </div>
            <div class="home-review">
                    <p> 베스트 리뷰 </p>
                    <div class="home-review-item">
                        <StoreReview class="flexitems" :datas="datas.reviewList" />
                    </div>
                </div>
                <div class="home-popular">
                    <p>스토어 인기 상품</p>
                    <div class="recommend">
                        <StoreItem
                        :datas="datas.popularList.slice(0, 6)"
                        />
                    </div>
                </div>
        </div>
    </div>
</template>

<script>
import StoreItem2 from "@/components/store/FlexStoreItem2.vue";
import StoreReview from "@/components/store/FlexStoreReview.vue";
import StoreItem from "@/components/store/FlexStoreItem.vue";
import axios from "axios";
import { mapGetters } from "vuex";
const SERVER_URL = "http://j3a404.p.ssafy.io:8081";
export default {
    components:{
      StoreItem2,
      StoreItem,
      StoreReview
    },
    data() {
        return{
            datas : {},
            sellerEmail: this.$route.params.email
        }
    },
    methods: {
        ...mapGetters({
            getEmail: "user/getEmail"
        }),
        fetchAll() {
            axios.get(`${SERVER_URL}/api/store/storeHome?email=${this.sellerEmail}`)
                .then(response => {
                    console.log(response.data);
                    this.datas = response.data;
                })
                .catch(err => console.log(err));
        }
    },
    mounted() {
        this.fetchAll();
    }
}
</script>
<style lang="scss" scoped>
@import "@/assets/store/storeHome.scss";
@import "@/assets/recipe/recipeHome.scss";
</style>