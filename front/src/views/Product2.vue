<template>
  <div class="container-base">
     <StoreHeader :storeInfo="storeinfo"/>
     <hr/>
     <div class="container-body">
         <div class="container-product-top">
             <div class="image-container">
                 <div class="main-img-container">
                     <img :src="productinfo.img"/>
                 </div>
                 <div class="sub-img-container">
                     <!--flex 서브이미지 만들어야됨 -->
                     <FlexSubImageBox :datas="productinfo.product_img" />
                 </div>
             </div>
             <div class="product-info-container">
                 <div class="product-name">
                     {{productinfo.name}}
                 </div>
                 <div class="product-price">
                     {{productinfo.price}} / (kg)
                 </div>
                 <div class="product-info">
                     <div class="product-delivery-container">
                        <div class="subtitle"> 배송정보 </div>
                        <div class="product-delivery-text"> {{productinfo.delevery_method}}</div>
                    </div>
                    <div class="product-origin-container">
                        <div class="subtitle"> 농장정보 </div>
                        <div class="product-origin-text">{{productinfo.origin}}</div>
                    </div>
                 </div>
             </div>
         </div>
         <div class="container-product-bottom">
             <div class="best-review-container">
                 <div class="best-review-img">
                    <img src="@/assets/images/icons/onion.jpg"/>
                 </div>
                 <div class="best-review-body">
                     <div class="subtitle">베스트 리뷰 </div>
                     <div class="star-point">
                        <div class="star-box">
                            <span class="star-rating">
                                <span v-bind:style="computedBestStarWidth"></span>
                            </span>
                        </div>
                        <div class="star-rate"> {{bestReview.score}} </div>
                    </div>
                     <div class="best-review-content">
                         {{bestReview.content}}
                     </div>
                 </div>
             </div>
             <div class="product-order-container">
                 <div class="product-num-container">
                    <div class="subtitle">수량 선택</div>
                    <div class="item-num-counter">
                        <div class="counter-down-btn" @click="counterDown" :disabled="this.amount<=0" >
                            <img src="@/assets/images/icons/crops/counter.png"/>
                        </div>
                        <div class="item-num">{{amount}}</div>
                        <div class="counter-up-btn" @click="counterUp" >
                            <img src="@/assets/images/icons/crops/counter.png"/>
                        </div>
                    </div>
                    <div class="item-num-total">
                        총 {{this.amount}} 개
                    </div>
                 </div>
                 <div class="product-order-price">
                     <div class="subtitle">예상 결제금액</div>
                     <div class="total-order-price">{{this.amount * this.productinfo.price}}</div>
                 </div>
                 <div class="product-order-btn-container">
                     <div class="pick-btn" @click="pickClick">
                         찜하기
                     </div>
                     <div class="cart-btn" @click="cartClick">
                         장바구니
                     </div>
                 </div>
             </div>
         </div>
         <div class="product-and-recipe">
             <div class="relate-product-container">
                <div class="title"> 
                    <div class="title-normal">이 상품과</div>
                    <div class="title-emp">함께 본 상품</div> 
                </div>
                <FlexProductItem
                :datas="recommenditems" />
             </div>
         </div>
         <div class="store-info-container">
             <div class="store-info-title">
                 <div class="store-name">{{storeinfo.store_name}}</div>
                 <div class="store-info">
                     <div class="sub-text">스토어 평점 {{storeinfo.store_score}} |</div>
                     <div class="sub-text"> 긍정리뷰 {{storeinfo.review_score}}% </div>
                 </div>
             </div>
             <div class="store-product">
                 <div class="store-product-subtitle"> 스토어 인기상품 </div>
                 <FlexProductItem
                :datas="popularitems" />
             </div>
         </div>
         <div class="tap-container" ref="tab">
             <div class="tap-info current" @click="moveTab(1)"> 상품정보 </div>
             <div class="tap-review " @click="moveTab(2)">  상품리뷰 </div>
             <div class="tap-seller " @click="moveTab(3)"> 판매자정보 </div>
         </div>
         <div class="tab-contents">
             <div class="content-info" ref="tab1">
                 <table class="info-table">
                     <tr>
                         <th>상품상태</th>
                         <td>{{productinfo.status}}</td>
                     </tr>
                     <tr>
                         <th>배송방법</th>
                         <td>{{productinfo.delevery_method}}</td>
                     </tr>
                     <tr>
                         <th>배송가능지역</th>
                         <td>{{productinfo.delevery_location}}</td>
                     </tr>
                     <tr>
                         <th>원산지</th>
                         <td>{{productinfo.origin}}</td>
                     </tr>
                     <tr>
                         <th>농수산물의 원산지 표기에 관한 법률에 따른 원산지</th>
                         <td>{{productinfo.law_origin}}</td>
                     </tr>
                     <tr>
                         <th>보관/취급 방법</th>
                         <td>{{productinfo.keep_method}}</td>
                     </tr>
                 </table>
                 <div class="info-content">
                     <div class="test">알지??!??!?!?!?!??!?!본문 내용</div>
                 </div>
             </div> <!--info content-->
             <div class="content-review" ref="tab2">
                 <div class="title"> 상품 리뷰 </div>
                 <div class="review-info-container">
                     <div class="review-avgscore">
                         <div class="text"> 구매자 평점 </div>
                         <div class="avgscore"> {{review_total_score}} </div>
                         <div class="star-point">
                            <div class="star-box">
                                <span class="star-rating">
                                    <span style="width:100%"></span>
                                </span>
                            </div>
                        </div>
                        <div class="review_count"> {{reviewList.length}}건 </div>
                     </div>
                     <div class="review-graph-container">
                         <table class="review-graph">
                             <tr>
                                 <td> 5점 </td>
                                 <td>
                                     <div class="graph-grey">
                                         <div :style="{width: (this.review_5/this.review_total*100)+'%'}"></div>
                                     </div>
                                 </td>
                                 <td> {{this.review_5}}개 </td>
                             </tr>
                             <tr>
                                 <td> 4점 </td>
                                 <td>
                                     <div class="graph-grey">
                                         <div :style="{width: (this.review_4/this.review_total*100)+'%'}"></div>
                                     </div>
                                 </td>
                                 <td> {{this.review_4}}개 </td>
                             </tr>
                             <tr>
                                 <td> 3점 </td>
                                 <td>
                                     <div class="graph-grey">
                                         <div :style="{width: (this.review_3/this.review_total*100)+'%'}"></div>
                                     </div>
                                 </td>
                                 <td> {{this.review_3}}개 </td>
                             </tr>
                             <tr>
                                 <td> 2점 </td>
                                 <td>
                                     <div class="graph-grey">
                                          <div :style="{width: (this.review_2/this.review_total*100)+'%'}"></div>
                                     </div>
                                 </td>
                                 <td> {{this.review_2}}개 </td>
                             </tr>
                             <tr>
                                 <td> 1점 </td>
                                 <td>
                                     <div class="graph-grey">
                                         <div :style="{width: (this.review_1/this.review_total*100)+'%'}"></div>
                                     </div>
                                 </td>
                                 <td> {{this.review_1}}개 </td>
                             </tr>
                         </table>
                     </div>
                     <div class="review-face-table">
                         <table class="face-info">
                            <tr>
                            <td>
                                <img src="@/assets/images/icons/crops/smle.png"/>
                            </td>
                            <td>
                                <img src="@/assets/images/icons/crops/soso.png"/>
                            </td>
                            <td>
                                <img src="@/assets/images/icons/crops/sad.png"/>
                            </td>
                            </tr>
                            <tr>
                            <td> 긍정리뷰 80%~100% </td>
                            <td> 긍정리뷰 50%~79% </td>
                            <td> 긍정리뷰 0%~49% </td>
                            </tr>
                         </table>
                     </div>
                 </div>
                 <div class="review-container">
                    <template v-for="(review, idx) in reviewList">
                        <ProductReviewItem :key="idx" :item="review"/>
                    </template>
                 </div>
             </div>
             <div class="content-seller" ref="tab3">
                 <div class="title"> 판매자 정보 </div>
                 <table class="seller-table">
                     <tr>
                         <th>판매자</th>
                         <td>{{storeinfo.nickname}}</td>
                     </tr>
                     <tr>
                         <th>상호명 / 대표자</th>
                         <td>{{storeinfo.store_name}} / {{storeinfo.rep_name}}</td>
                     </tr>
                     <tr>
                         <th>사업자 구분</th>
                         <td>{{storeinfo.type}}</td>
                     </tr>
                     <tr>
                         <th>사업자등록번호</th>
                         <td>{{storeinfo.number}}</td>
                     </tr>
                     <tr>
                         <th>전화번호</th>
                         <td>{{storeinfo.tell}}</td>
                     </tr>
                     <tr>
                         <th>이메일</th>
                         <td>{{storeinfo.email}}</td>
                     </tr>
                    <tr>
                         <th>영업소재지</th>
                         <td>{{storeinfo.address}}</td>
                     </tr>
                    <tr>
                         <th>농장주소</th>
                         <td>{{storeinfo.origin}}</td>
                     </tr>
                 </table>
                 <div class="seller-rate-table-container">
                    <table class="seller-rate-table">
                        <tr>
                        <td>
                            <img src="@/assets/images/icons/star.png"/>
                        </td>
                        <td>
                            <img src="@/assets/images/icons/smile2.png"/>
                        </td>
                        </tr>
                        <tr>
                        <td> 스토어평점 {{storeinfo.store_score}} </td>
                        <td> 긍정리뷰 {{storeinfo.review_percent}}% </td>
                        </tr>
                    </table>
                 </div>
                <div class="guideline">
                        <p> 스토어 평점 </p>
                        <p> * 판매된 모든 물품들의 총 평점을 평균값으로 나눈 점수</p>
                        <br/>
                        <p> 긍정 리뷰 </p>
                        <p> * 고객이 판매자를 평가한 판매자 리뷰를 분석하여 긍정적인 반응의 글로 작성된 글의 비율</p>
                </div>
             </div>
         </div>
     </div>
  </div>
</template>
<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
<script type="text/javascript">
import StoreHeader from "@/components/store/StoreHeader.vue";
import MainLineChart from './MainLineChart';
import FlexSubImageBox from "@/components/product/FlexSubImageBox.vue";
import FlexProductItem from "@/components/product/FlexProductItem.vue";
import ProductReviewItem from "@/components/product/ProductReviewItem.vue";
import axios from "axios";
import { mapGetters } from "vuex";
const SERVER_URL = "http://j3a404.p.ssafy.io:8081";
export default {
  name: "Product",
  components: {
    StoreHeader,
    MainLineChart,
    FlexSubImageBox,
    FlexProductItem,
    ProductReviewItem
  },
  data() {
      return{
        pid: this.$route.params.pid,
        productinfo:{
            id:"",
            img:"",
            content:"",
            delevery_location:"",
            delevery_method:"",
            delevery_price: "",
            name:"",
            price:"",
            kg: null,
            status:"",
            origin:"",
            law_origin:"",
            keep_method:"",
            product_img:[],
            seller:"",
            life_time: "",
        },
        storeinfo:{},
        amount:0,
        current_tab:1,
        scrolled:false,
        tapTop:0,
        tab:null,
        tap1Top:0,
        tap2Top:0,
        tap3Top:0,
        recommenditems:[],
        popularitems:[],
        bestReview:{},
        reviewList:[],
        review_total : 0,
        review_5 : 0,
        review_4 : 0,
        review_3 :0,
        review_2 : 0,
        review_1 : 0,
        review_total_score:0,
      };
  },
  methods:{
    ...mapGetters({
      getUserInfo: "user/getUserInfo"
      }),
    counterUp() {
      this.amount += 1;
    },
    counterDown(){
     this.amount -= 1;
    },
    moveTab(num) {
      if (num == 1) {
        document.querySelector(".tap-info").classList.add("current");
        document.querySelector(".tap-review").classList.remove("current");
        document.querySelector(".tap-seller").classList.remove("current");
      } else if (num == 2) {
        document.querySelector(".tap-info").classList.remove("current");
        document.querySelector(".tap-review").classList.add("current");
        document.querySelector(".tap-seller").classList.remove("current");
      }else if(num == 3){
          document.querySelector(".tap-info").classList.remove("current");
        document.querySelector(".tap-review").classList.remove("current");
        document.querySelector(".tap-seller").classList.add("current");
      }
      this.current = num;
    },
    detextWindowScrollY(){
        this.scrolled = window.scrollY> this.tapTop ? true : false
        const tab = this.tab
        this.scrolled ? this.tab.classList.add('scrolled') : this.tab.classList.remove('scrolled');
    },
    detextWindowScrollY2(){
        if(this.scrolled){
            if(window.scrollY <= this.tap2Top){
                this.moveTab(1);
            }
            else if(window.scrollY>this.tap2Top && window.scrollY<= this.tap3Top){
                this.moveTab(2);
            }
            else{
                this.moveTab(3);
            }
        }
    },
    async fetchProductInfo(){
        try{
            const productResult = await axios.get(
          `${SERVER_URL}/api/product/search/pid/${this.pid}`
            );
            if(productResult){
                this.productinfo.img = productResult.data.img;
                this.productinfo.content = productResult.data.content;
                this.productinfo.delevery_location = productResult.data.delevery_location;
                this.productinfo.delevery_method = productResult.data.delevery_method;
                this.productinfo.delevery_price = productResult.data.delevery_price;
                this.productinfo.name = productResult.data.name;
                this.productinfo.price = productResult.data.price;
                this.productinfo.kg = productResult.data.kg;
                this.productinfo.status = productResult.data.status;
                this.productinfo.origin = productResult.data.origin;
                this.productinfo.law_origin = productResult.data.law_origin;
                this.productinfo.keep_method = productResult.data.keep_method;
                    
                this.productinfo.seller = productResult.data.seller;
                this.productinfo.life_time = productResult.data.life_time;
                this.productinfo.id = this.pid;
           
                //본문은 innerHtml로 붙여넣을 것
                var content_area = document.querySelector('.info-content');
                if(this.productinfo.content != null)
                    content_area.innerHTML = this.productinfo.content;

                var sellerEmail = productResult.data.seller;
                //Store정보
                try{
                    const storeResult = await axios.get(
                    `${SERVER_URL}/api/store/storeInfo?email=${sellerEmail}`
                    );
                    if(storeResult.status == 200){
                        this.storeinfo = storeResult.data;
                        console.log("스토어정보");
                        console.log(storeResult.data);

                        //또가져온다 -> 스토어의 인기상품
                            const storePopularResult= await axios.get(
                            `${SERVER_URL}/api/store/popular?email=${sellerEmail}&page=1`
                            );
                            
                            if(storePopularResult){
                            this.popularitems.push(storePopularResult.data[0]);
                            this.popularitems.push(storePopularResult.data[1]);
                            this.popularitems.push(storePopularResult.data[2]);
                            }
                    }
                }catch(error){
                  console.log(error);
                }

            }
        }catch(error){
                  console.log(error);
        }
    },
    fetchSubImage(){
        //서브이미지 불러오기
        axios
        .get(
             `${SERVER_URL}/api/product/search/picture/${this.pid}`
            ).then(response => {
                 if(response.data != null){
                    for(var j=0; j<response.data.length; j++){
                        this.productinfo.product_img.push(response.data[j].path);
                        }
                        console.log("서브 이미지");
                        console.log(this.productinfo.product_img[0]);
                    }
        }).catch(err => console.log(err));
    },

    fetchStoreInfo(){
          axios
                .get(
                `${SERVER_URL}/api/store/storeInfo?email=${productinfo.seller}`
                )
                .then(response => {
                   
                    this.storeinfo = response.data;
                    console.log(response.data);
                })
                .catch(err => console.log(err));
    },
    fetchRecommendItems(){
        axios
                .get(
                `${SERVER_URL}/api/product/recommend?num=5&userId=${this.getUserInfo().id}`
                )
                .then(response => {
                    this.recommenditems = response.data;
                    console.log(response.data);
                })
                .catch(err => console.log(err));
    },
    pickClick(){
         axios
                .post(
                      `${SERVER_URL}/api/userlike/add?`,{
                          "img":this.productinfo.img,
                          "product_id": this.pid,
                          "user_id" : this.getUserInfo().id,
                        })
                        .then(response => {
                        console.log(response.data);
                        alert("찜하기 완료")
                        })
                .catch(err => console.log(err));
    },
    cartClick(){
        axios
                .post(
                      `${SERVER_URL}/api/cart/add?`,{
                          "amount":this.amount,
                          "email":this.getUserInfo().email,
                          "img":this.productinfo.img,
                          "name":this.productinfo.name,
                          "price":this.productinfo.price,
                          "product_id": this.pid,
                          "seller" : this.productinfo.seller,
                          "store_name" : this.storeinfo.store_name,
                          "user_id" : this.getUserInfo().id,
                        })
                        .then(response => {
                        console.log(response.data);
                        alert("장바구니에 추가되었습니다.")
                        })
                .catch(err => console.log(err));
    },
    fetchReviewInfo(){
        axios
                .get(
                `${SERVER_URL}/api/product/review?product_id=${this.pid}`
                )
                .then(response => {
                    if(response.data.bestReview == null) this.bestReview = [];
                    else this.bestReview = response.data.bestReview;
                    
                    if(response.data.reviewList == null) this.reviewList = [];
                    else this.reviewList = response.data.reviewList;
                    for(var i=0; i<this.reviewList.length; i++){
                        if(this.reviewList[i].score == 1 || this.reviewList[i].score == 1.5)
                            this.review_1 += 1;
                        else if(this.reviewList[i].score == 2 || this.reviewList[i].score == 2.5)
                            this.review_2 += 1;
                        else if(this.reviewList[i].score == 3 || this.reviewList[i].score == 3.5)
                            this.review_3 += 1;
                        else if(this.reviewList[i].score == 4 || this.reviewList[i].score == 4.5)
                            this.review_4 += 1;
                        else 
                            this.review_5 += 1;
                    }

                  
                    this.review_total = this.review_1 + this.review_2 + this.review_3  + this.review_4 + this.review_5;
                    this.review_total_score = (this.review_1*1 + this.review_2*2 +this.review_3*3  + this.review_4*4 + this.review_5*5)/this.review_total;
                    console.log("최종");
                   
                })
                .catch(err => console.log(err));
    },
    sendView(){
        axios
                .post(
                `${SERVER_URL}/api/view/add`,{
                    "email" : this.getUserInfo().email,
                    "img" : this.productinfo.img,
                    "name" : this.productinfo.name,
                    "product_id" : this.pid
                })
                .then(response => {
                    console.log(response.data);
                })
                .catch(err => console.log(err));
    },
  },
  computed:{
    computedBestStarWidth(){
      var starwidth = "width:"+(parseFloat(this.bestReview.score/5.0))*100 + "%";
      return starwidth;
    }
  },
  created() {
    this.fetchProductInfo();
    this.fetchReviewInfo();
    this.sendView();
    this.fetchSubImage();
  },
  mounted(){
      //스크롤 탭 구현
      this.tab = this.$refs.tab
      this.tapTop = this.tab.offsetTop;
      this.tap1Top = this.$refs.tab1.offsetTop
      this.tap2Top = this.$refs.tab2.offsetTop
      this.tap3Top = this.$refs.tab3.offsetTop
      window.addEventListener('scroll', this.detextWindowScrollY)
      window.addEventListener('scroll', this.detextWindowScrollY2)

      this.fetchRecommendItems();
  },
  beforeDestroy(){
      window.removeEventListener('scroll', this.detextWindowScrollY)
      window.removeEventListener('scroll', this.detextWindowScrollY2)
  },
  
};
</script>

<style scoped lang="scss">
 @import "@/assets/sass/product.scss";
</style>