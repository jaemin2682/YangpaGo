<template>
    <div class="item-container">
        <ReviewModal ref="ReviewModal" />
        <ReviewReadModal ref="ReviewReadModal" />
        <div class="item-img-container">
            <img v-bind:src="item.img"/>
        </div>
        <div class="item-body-container">
            <div class="store-name"><p>{{item.store_name}}</p></div>
            <div class="product-name"><p>{{item.name}}</p></div>
        </div>
        <div class="item-option-container">
            <div class="price">{{item.price}}원</div>
            <div class="option">({{item.kg}}kg)</div>
            <div> 수량 : {{item.amount}}</div>
        </div>
        <div class="item-button-container">
            <button v-if="!item.isReview" class="purchase-btn" @click="openReviewModal()">리뷰 쓰기</button>
            <button v-else class="openReview-btn" @click="openReviewReadModal()">리뷰 보기</button>
            <button class="delete-btn" @click="unregister">삭제</button>
        </div>
    </div>
</template>

<script>
import ReviewModal from "@/components/user/ReviewModal.vue";
import ReviewReadModal from "@/components/user/ReviewReadModal.vue";
import axios from "axios";
import { mapGetters } from "vuex";
const SERVER_URL = "http://j3a404.p.ssafy.io:8081";
export default {
    props:{
      item:{
          type: Array,
          required: true
        },
    },
    components: {
    ReviewModal,
    ReviewReadModal,
   },
   methods:{
    ...mapGetters({
        getEmail: "user/getEmail"
    }),
    openReviewModal() {
      this.$refs.ReviewModal.showModal(this.item);
    },
    openReviewReadModal(){
        this.$refs.ReviewReadModal.showModal(this.item.id);
    },
    closeReadModal() {
        alert("close");
        this.$refs.ReviewReadModal.closeModal();
    },
    unregister() {
        console.log(this.item.id);
        if(confirm("해당 상품을 주문조회 목록에서 삭제하시겠습니까?") == true) {
            axios.delete(`${SERVER_URL}/api/purchase/delete/${this.getEmail()}/${this.item.id}`)
            .then(response => {
                alert("삭제가 완료되었습니다.")
                this.$emit('getList');
                console.log(response);
            })
            .catch(err => console.log(err));
        }
    }
  }
}
</script>

<style scoped lang="scss">
 @import "@/assets/user/userpageItem.scss";
</style>