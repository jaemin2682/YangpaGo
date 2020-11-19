<template>
  <div class="modal" v-if="show">
    <div class="background" @click="closeModal"></div>
    <div class="modal-content">
        <div class="product-info-wrapper">
           <img v-bind:src="this.datas.img"/>
           <div class="product-info">
               <div class="product-name"> {{this.datas.name}} </div>
               <div class="product-price"> {{this.datas.created_date}} 주문 </div>
           </div>
        </div>
        <div class="star-point">
            <div class="star-box">
                <span class="star-rating">
                    <span v-bind:style="this.width"></span>
                </span>
            </div>
            <div class="star-rate">{{this.datas.score}} </div>
            <div class="user-info"> | {{this.datas.nickname}} </div>
        </div>
        <div class="review-container">
            <div class="review-text"> {{this.datas.content}} </div>
            <div class="review-img">
                <img v-bind:src="this.datas.path"/>
            </div>
        </div>
        <div class="btn-container">
            <div class="cancel-btn" @click="addMyPickList">
                상품 찜하기
            </div>
            <div class="upload-btn" @click="UploadReview()">
                상품 상세보기
            </div>
        </div>
    </div>
    <button @click="show = false" class="closeModalBtn">창 닫기</button>
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
      datas:{},
      width: "width:100%"
    };
  },
  methods: {
    ...mapGetters({
        getEmail: "user/getEmail",
        getUserId: "user/getUserId"
    }),
    readReview(id) {
      axios.get(`${SERVER_URL}/api/review/search?email=${this.getEmail()}&product_id=${id}`)
      .then(response => {
          this.datas = response.data;
      })
      .catch(err => console.log(err));
    },
    addMyPickList() {
      axios.post(`${SERVER_URL}/api/userlike/add`, {
        user_id: this.getUserId(),
        product_id: this.datas.product_id
      })
        .then(response => {
          alert("찜하기가 완료되었습니다.");
          console.log(response);
        })
        .catch(err => {
          alert("이미 찜한 상품입니다.");
          console.log(err);
        });
    },
    showModal(command) {
      // Add the sent command
      this.readReview(command);
      this.command = command;
      this.show = true;
      this.width = "width:" + this.datas.score*20 + "%";
      // setTimeout(function() {
      //   console.log("fire!");
      //   this.width = "width:" + this.datas.score*20 + "%";
        
      // }, 1000);
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
        this.review_rate=num;
    },
    UploadImg(){
        this.$refs.ReviewImg.click();
    },
    onChangeReviewImg(e){
      const file = e.target.files[0];
      var formData = new FormData();
      formData.append("upload_file", file);
      this.ReviewFormData = formData;
      //console.log("DoctorFormData : ", this.DoctorFormData);
      this.ReviewImgsrc = URL.createObjectURL(file);
    },
    CancelReview(){
        this.closeModal();
    }
  },
  components: {
  },
  created() {
  }
};
</script>


<style scoped lang="scss">
 @import "@/assets/user/reviewReadModal.scss";
 .background {
  position: fixed;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.4);
}
</style>