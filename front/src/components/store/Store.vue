<template>
  <main>
  <div class="store-container">


    <div class="main-container">
      <!--스토어 홈 페이지-->
      <div class="home" v-if="this.active=='home'">
        <div class="title">
          <h1>스토어 홈</h1>
        </div>
        <div class="home-price">
          <p> 시세보다 저렴한 상품 </p>
           <div class="home-price-item">
              <StoreItem2 class="flexitems" :datas=2 />
              <div class="more">
                <img src="@/assets/images/icons/next.svg"/>
              </div>
            </div>
        </div>
        <div class="home-review">
          <p> 베스트 리뷰 </p>
          <div class="home-review-item">
            <StoreReview class="flexitems" :datas=2 />
            <div class="more">
                <img src="@/assets/images/icons/next.svg"/>
            </div>
          </div>
        </div>
        <div class="home-popular">
          <p> 스토어 인기 상품 </p>
          <StoreItem 
         :datas=6
          />
        </div>
      </div>
      <!--스토어 정보 페이지-->
      <div class="info" v-if="this.active=='info'">
        <div class="title">
        <h1>스토어 정보</h1>
        </div>
        <div class="seller-info">
          <p>판매자 정보</p>
          <table class="seller-info-table">
            <tr>
              <th>판매자</th>
              <td>SellerName01</td>
            </tr>
            <tr>
              <th>상호명 / 대표자</th>
              <td>StoreName / Name</td>
            </tr>
            <tr>
              <th>사업자 구분</th>
              <td>개인사업</td>
            </tr>
            <tr>
              <th>사업자등록번호</th>
              <td>01234567890</td>
            </tr>
            <tr>
              <th>통신판매업신고</th>
              <td>010-1111-5678</td>
            </tr>
            <tr>
              <th>전화번호</th>
              <td>없음</td>
            </tr>
            <tr>
              <th>이메일</th>
              <td>email@domain.com</td>
            </tr>
            <tr>
              <th>영업소재지</th>
              <td></td>
            </tr>
            <tr>
              <th>농장주소</th>
              <td>강원도 양양시 어쩌구</td>
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
              <td>
                <img src="@/assets/images/icons/comment.png"/>
              </td>
            </tr>
            <tr>
              <td> 스토어평점 4.3 </td>
              <td> 긍정리뷰 88% </td>
              <td> 응답률 100% </td>
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
            <p> 응답률 </p>
            <p> * 판매자가 구매자의 문의에 응답한 비율 </p>
          </div>
        </div>
        <div class="review-info">
          <p> 판매자리뷰 </p>

        </div>
      </div>
      <!--스토어 인기상품 페이지-->
      <div class="popular" v-if="this.active=='popular'">
        <div class="title">
        <h1>인기 상품</h1>
        </div>
        <div class="store-guideline">
          <div class="guideline">
            <p> 인기 상품 </p>
            <p> * 해당 스토어에서 주문량이 많은 상품입니다.</p>
          </div>
        </div>
        <StoreItem 
         :datas=6
        />
      </div>
      <!--스토어 추천상품 페이지-->
      <div class="recommend" v-if="this.active=='recommend'">
        <div class="title">
        <h1>추천상품</h1>
        </div>
        <div class="store-guideline">
          <div class="guideline">
            <p> 추천 상품 </p>
            <p> * 당신의 행동 패턴을 분석하여 필요하거나 취향에 맞는 상품을 추천합니다.</p>
          </div>
        </div>
        <StoreItem 
         :datas=6
        />
      </div>
      <!--스토어 시세 비교 페이지-->
      <div class="price" v-if="this.active=='price'">
        <div class="title">
          <h1>시세보다 저렴한 상품</h1>
        </div>
        <div class="store-guideline">
          <div class="guideline">
            <p> 시세보다 저렴한 상품 </p>
            <p> * 분기별 시세를 분석한 결과를 기반으로 시세보다 저렴한 상품입니다.</p>
          </div>
        </div>
        <div class="price-graph-container">
          <p class="price-graph-title"> 시세 분석 그래프 </p>
          <PriceGraph/>
        </div>
         <StoreItem 
         :datas=6
        />
      </div>
    </div>
  </div>
  </main>
</template>

<script>
import StoreItem from "@/components/store/FlexStoreItem.vue";
import StoreItem2 from "@/components/store/FlexStoreItem2.vue";
import PriceGraph from "@/components/store/PriceGraph.vue";
import StoreReview from "@/components/store/FlexStoreReview.vue";
export default {
    data:function() {
        return {
        active:'home'
        };
    },
    methods:{
     makeActive: function(item){
        this.active = item;
        }
    },
    components:{
      StoreItem,
      PriceGraph,
      StoreItem2,
      StoreReview,
    }
};
</script>

<style scoped lang="scss">
 @import "@/assets/sass/storeMain.scss";
</style>
