<template>
  <div class="modal" v-if="show">
    <div class="background" @click="closeModal"></div>
    <div class="modal-content">
        <div class="modal-title">
            구매 후기 작성
        </div>
        <div class="product-info-wrapper">
           <img v-bind:src="this.item.img"/>
           <div class="product-info">
               <div class="store-name"> {{this.item.store_name}} </div>
               <div class="product-name"> {{this.item.name}} </div>
               <div class="order-date"> 2020.10.01 주문 </div>
           </div>
        </div>
        <div class="star-point">
            <div class="star-subtitle"> 상품은 만족 하셨나요? </div>
            <div class="star-box">
                <span class="star star_left" @click="starClick(0.5)"></span>
                <span class="star star_right" @click="starClick(1.0)"></span>

                <span class="star star_left"  @click="starClick(1.5)"></span>
                <span class="star star_right"  @click="starClick(2.0)"></span>

                <span class="star star_left" @click="starClick(2.5)"></span>
                <span class="star star_right" @click="starClick(3.0)"></span>

                <span class="star star_left" @click="starClick(3.5)"></span>
                <span class="star star_right" @click="starClick(4.0)"></span>

                <span class="star star_left" @click="starClick(4.5)"></span>
                <span class="star star_right" @click="starClick(5.0)"></span>
            </div>
            <div class="star-text">선택하세요</div>
        </div>
        <div class="review-container">
            <textarea v-model="content" placeholder="리뷰를 입력하세요"></textarea>
            <div @click="UploadImg" class="photo-text"> 리뷰 사진을 첨부하세요 </div>
            <input ref="ReviewImg" type="file" hidden @change="onChangeReviewImg"/>
            <img :src="ReviewImgsrc" />
        </div>
        <div class="btn-container">
            <div class="cancel-btn" @click="CancelReview()">
                취소하기
            </div>
            <div class="upload-btn" @click="writeReview()">
                등록하기
            </div>
        </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import { mapGetters } from "vuex";
const SERVER_URL = "http://j3a404.p.ssafy.io:8081";
export default {
  data() {
    return {
      command: null,
      show: false,
      tab: 1,
      review_rate:0,
      imgsrc:null,
      ReviewImgsrc:null,
      ReviewFormData:null,
      content:"",
      path:"",
      product_id:0,
      score:0,
      item: {}
    };
  },
  methods: {
    ...mapGetters({
        getEmail: "user/getEmail"
    }),
    showModal(command) {
      // Add the sent command
      this.item = command;
      console.log(this.item);
      this.command = command;
      this.show = true;
    },
    writeReview() {
      axios.post(`${SERVER_URL}/api/review/add`, {
        email: this.getEmail(),
        content: this.content,
        path: this.path,
        product_id: this.item.id,
        score: this.score
      })
        .then(response => {
          alert("리뷰 작성이 완료되었습니다.");
          console.log(response);
        })
        .catch(err => {
          alert("리뷰 작성 실패");
          console.log(err);
        });
      this.closeModal();
    },
    closeModal() {
      this.show = false;
      this.imageSrc = [];
      this.tab = 1;
    },
    starClick(num){
        var idx = num*2-1;
        var stars = document.querySelectorAll(".star");
        for(var i=0; i<stars.length; i++){
            stars[i].classList.remove("on");
        }
        for(var j=0; j<=idx; j++){
            stars[j].classList.add("on");
        }
        this.score=num;
    },
    UploadImg(){
        this.$refs.ReviewImg.click();
    },
    onChangeReviewImg(e){
      const file = e.target.files[0];
      var formData = new FormData();
      formData.append("filename", file);
      this.ReviewFormData = formData;
      //console.log("DoctorFormData : ", this.DoctorFormData);
      this.ReviewImgsrc = URL.createObjectURL(file);
      axios
      .put(`${SERVER_URL}/api/product/upload`,this.ReviewFormData)
      .then(response => {
        console.log(response.data);
        this.path = response.data;
      })
      .catch(err => console.log(err));
    },
    CancelReview(){
        this.closeModal();
    }
  },
  components: {
  }
};
</script>


<style scoped lang="scss">
 @import "@/assets/user/reviewModal.scss";
  .background {
  position: fixed;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.4);
}
</style>