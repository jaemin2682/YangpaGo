<template>
  <!--스토어 정보 페이지-->
    <div class="info">
        <div class="title">
        <h1>스토어 정보</h1>
        </div>
        <div class="seller-info">
          <p>판매자 정보</p>
          <table class="seller-info-table">
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
        </div>
        <div class="store-info">
          <table>
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
        <div class="store-guideline">
          <div class="guideline">
            <p> 스토어 평점 </p>
            <p> * 판매된 모든 물품들의 총 평점을 평균값으로 나눈 점수</p>
            <br/>
            <p> 긍정 리뷰 </p>
            <p> * 고객이 판매자를 평가한 판매자 리뷰를 분석하여 긍정적인 반응의 글로 작성된 글의 비율</p>
            <br/>
          </div>
        </div>
        <div class="review-info">
          <p class="review-graph-title"> 판매자리뷰 </p>
          <div class="review-graph-container">
                         <table class="review-graph">
                             <tr>
                                 <td> 5점 </td>
                                 <td>
                                     <div class="graph-grey">
                                         <div :style="{width: (storeinfo.review_5/this.total*100)+'%'}"></div>
                                     </div>
                                 </td>
                                 <td> {{storeinfo.review_5}}개 </td>
                             </tr>
                             <tr>
                                 <td> 4점 </td>
                                 <td>
                                     <div class="graph-grey">
                                         <div :style="{width: (storeinfo.review_4/this.total*100)+'%'}"></div>
                                     </div>
                                 </td>
                                 <td> {{storeinfo.review_4}}개 </td>
                             </tr>
                             <tr>
                                 <td> 3점 </td>
                                 <td>
                                     <div class="graph-grey">
                                         <div :style="{width: (storeinfo.review_3/this.total*100)+'%'}"></div>
                                     </div>
                                 </td>
                                 <td> {{storeinfo.review_3}}개 </td>
                             </tr>
                             <tr>
                                 <td> 2점 </td>
                                 <td>
                                     <div class="graph-grey">
                                         <div :style="{width: (storeinfo.review_2/this.total*100)+'%'}"></div>
                                     </div>
                                 </td>
                                 <td> {{storeinfo.review_2}}개 </td>
                             </tr>
                             <tr>
                                 <td> 1점 </td>
                                 <td>
                                     <div class="graph-grey">
                                         <div :style="{width: (storeinfo.review_1/this.total*100)+'%'}"></div>
                                     </div>
                                 </td>
                                 <td> {{storeinfo.review_1}}개 </td>
                             </tr>
                         </table>
                     </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";
import { mapGetters } from "vuex";
const SERVER_URL = "http://j3a404.p.ssafy.io:8081";
export default {
  data(){
    return{
      storeinfo:[],
      sellerEmail: this.$route.params.email,
      total: null
    }
  },
  methods:{
    ...mapGetters({
        getEmail: "user/getEmail",
    }),
    fetchStoreInfoData(){
      axios
          .get(
                `${SERVER_URL}/api/store/storeInfo?email=${this.sellerEmail}`
                )
                .then(response => {
                console.log(response.data);
                this.storeinfo = response.data;
                // 리뷰 퍼센트
                this.total = this.storeinfo.review_5
                            +this.storeinfo.review_4
                            +this.storeinfo.review_3
                            +this.storeinfo.review_2
                            +this.storeinfo.review_1;
                
                })
          .catch(err => console.log(err));
    },
  },
  mounted() {
      this.fetchStoreInfoData();
  }
}
</script>
<style lang="scss" scoped>
@import "@/assets/store/storeInfo.scss";
.review-graph-title {
  margin-top: 20px;
  font-size: 17px;
}
.review-graph-container{
  .review-graph{
      tr{
          td{
              padding : 10px 10px 10px 0px;
              .graph-grey{
                  width: 350px;
                  height: 10px;
                  background-color: #e0e0e0;
                  display:inline-block; 
              }
              .graph-grey div{
                  width:40px;
                  display:inline-block;
                  height: 7px;
                  background-color:#a7496d;
              }
          }
      }
  }
}
</style>