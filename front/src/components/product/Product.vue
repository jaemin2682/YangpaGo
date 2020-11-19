<template>
  <div class="container-base">
    <div class="main-container">
      <div class="crop-container">
        <div class="crop-body">
          <img src="@/assets/images/icons/crops/potato.png" style="float:left"/>
          <div>
            <span class="crop-name">눈을 감자마자 생각나는<br> 갓 수확한 맛있는 감자</span>         
            <br>
            <span class="crop-price">10,200원 / (kg)<br><br></span>
            <button class="btn btn-propose">가격 제안하기</button>
          </div>
          <div>
            <!-- <div class="crop-price">
              <img src="@/assets/images/icons/crops/graph.png" style="float:right"/>
            </div> -->
            <span class="crop-cheap">(매우 저렴한 가격입니다!)<br><br></span>
          </div>
          <div>
            <span class="banner-info">배송 정보 : 농장 직배송</span><br>
            <span class="banner-info">농장 정보 : 강원도 삼척시 블라블라<br></span>
          </div>
          <div>
            <span class="banner-select">메뉴 선택 :  <br><br></span>
              <select v-model="selected">
                <option disabled value="">선택해주세요</option>
                <option>감자감자 왕감자 10kg (10,200원)</option>
                <option>감자감자 왕감자 20kg (20,400원)</option>
                <option>감자감자 왕감자 30kg (30,600원)</option>
              </select><br>
            <!-- <span class="banner-select">선택함: {{ selected }}</span><br> -->
            <!-- <span class="banner-select">수량 선택 :
              <button @click="decrement">-</button>
              <h1> 1 {{number}} </h1>
              <button @click="increment">+</button>
            </span> -->
          </div>
          <div>
            <span class="banner-select">예상 결재 금액 : </span>
            <span class="banner-select">10,200원</span><br><br>
          </div>
            <button class="btn btn-cart">장바구니</button>
            <button class="btn btn-buy">구매하기</button>         
          </div>
        </div>        
      </div>

      <!-- 이 상품과 함께 산 산품 -->
      <div class="recommend-container">
          <!-- <h3>이 상품과 함께 산 산품</h3> -->
          <div class="crops">
            <div class="crop">
              <!-- <div class="crop-body">
                <img class="crops-icon" src="@/assets/images/icons/crops/potato.png"/>
              </div>
              <div class="crop-footer">
                갓수확한 감자감자왕감자<br>
                10,200원(kg)
              </div> -->
            </div>
            <div class="crop">
              <!-- <div class="crop-body">
                <img class="crops-icon" src="@/assets/images/icons/crops/potato.png"/>
              </div>
              <div class="crop-footer">
                양파고 단독! 뤠드어니언<br>
                7,300원(kg)
              </div> -->
            <!-- </div> -->
            <!-- <div class="crop">
              <div class="crop-body">
                <img class="crops-icon" src="@/assets/images/icons/crops/potato.png"/>
              </div>
              <div class="crop-footer">
                갓수확한 감자감자왕감자<br>
                10,200원
              </div> -->
            </div>
          <!-- <h3>추천 레시피</h3>
            <div class="crop">
              <div class="crop-body">
                <img class="crops-icon" src="@/assets/images/icons/crops/potato.png"/>
              </div>
              <div class="crop-footer">
                초간단 에어프라이어 감자칩
              </div>
            </div>
            <div class="crop">
              <div class="crop-body">
                <img class="crops-icon" src="@/assets/images/icons/crops/potato.png"/>
              </div>
              <div class="crop-footer">
                서양의 맛 감자뇨끼
              </div>
            </div>
            <div class="crop">
              <div class="crop-body">
                <img class="crops-icon" src="@/assets/images/icons/crops/potato.png"/>
              </div>
              <div class="crop-footer">
                감자듬뿍 카레라이스
              </div>
            </div>             -->
      </div>

      <div class="content">
        <ul class="tabs">
          <li @click="moveTab(1)" class="info current">상품정보</li>
          <li @click="moveTab(2)" class="review">상품리뷰</li>
          <li @click="moveTab(3)" class="seller">판매자정보</li>
        </ul>

<!-- 상품 정보 -->
        <div class="tab-content info-content current">
          <table class="info" border="5" bordercolor="black">
            <tr>
              <td class="td1">상품상태</td>
              <td class="td2">새상품</td>
            </tr>
            <tr>
              <td class="td1">배송방법</td>
              <td class="td2">택배</td>
            </tr>
            <tr>
              <td class="td1">배송가능지역</td>
              <td class="td2">전국</td>
            </tr>
            <tr>
              <td class="td1">원산지</td>
              <td class="td2">국내산</td>
            </tr>
            <tr>
              <td class="td1">제조년월일 / 유통기한</td>
              <td class="td2">항시수확</td>
            </tr>
            <tr>
              <td class="td1">농수산물의 원산지 표시에 관한 법률에 따른 원산지</td>
              <td class="td2">국내산</td>
            </tr>
            <tr>
              <td class="td1">보관/취급 방법</td>
              <td class="td2">직사광선을 피하고 서늘한곳에 보관</td>
            </tr>            
            <!-- <vue-good-table
              :rows="rows"
              :columns="columns"/> -->
          </table>
          <div class="content-image">
            <img class="crops-icon" src="@/assets/images/icons/crops/potato.png"/>
          </div>
        </div>

        <!-- 상품리뷰 -->
        <div class="tab-content review-content">
          <div class="video-type px-4 mb-2 bg-white rounded-pill shadow-sm">
            <div class="d-inline-flex justify-content-center align-items-center mx-2 p-2">
              <span class="text-primary font-weight-bold">평점 선택</span>
            </div>
            <div class="d-inline-flex m-2 p-2">
              <button
                class="btn btn-sm"
                :class="{
                  'btn-success': !!grade.status,
                  'btn-outline-primary': !grade.status,
                }"
                v-for="grade in GradeType"
                :key="grade.name"
                @click="toggleFilter(grade)"
              >
                <span class="mx-2">{{ grade.name }}</span>
              </button>
            </div>
          </div>
          <div class="edit-skill px-4 mb-2 bg-white rounded-pill shadow-sm">
            <div
              class="d-inline-flex justify-content-center align-items-center mx-2 p-2"
            >
              <span class="text-primary font-weight-bold">리뷰 구분</span>
            </div>
            <div class="d-inline-flex m-2 p-2">
              <button
                class="btn btn-sm"
                :class="{
                  'btn-success': !!positive.status,
                  'btn-outline-primary': !positive.status,
                }"
                v-for="positive in ReviewType"
                :key="positive.name"
                @click="toggleFilter(positive)"
              >
                <span class="mx-2">{{ positive.name }}</span>
              </button>
            </div>
          </div>          
        </div>
        
        <!-- 판매자정보 -->
        <div class="tab-content seller-content">
          <!-- <h3>판매자 정보</h3> -->
          <table class="seller" border="5" bordercolor="black">
            <tr>
              <td class="td1">판매자</td>
              <td class="td2">SellerName01</td>
            </tr>
            <tr>
              <td class="td1">상호명/대표자</td>
              <td class="td2">StoreName/Name</td>
            </tr>
            <tr>
              <td class="td1">사업자 구분</td>
              <td class="td2">개인 사업</td>
            </tr>
            <tr>
              <td class="td1">사업자등록번호</td>
              <td class="td2">0123456789</td>
            </tr>
            <tr>
              <td class="td1">통신판매업신고</td>
              <td class="td2">010-1234-5678</td>
            </tr>
            <tr>
              <td class="td1">전화번호</td>
              <td class="td2">없음</td>
            </tr>
            <tr>
              <td class="td1">이메일</td>
              <td class="td2">email@domain.com</td>
            </tr>
            <tr>
              <td class="td1">영업소재지</td>
              <td class="td2">~~~~~~~~</td>
            </tr>
            <tr>
              <td class="td1">농장주소</td>
              <td class="td2">경기도 양양시 어쩌구</td>
            </tr>
          </table>   
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
          <div class="seller body">
          </div>              
        </div>
      </div>
      <div class="content-body"></div>    

      <div class="select-container">
        
      </div>
    </div>
  </div>
</template>

<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
<script type="text/javascript">
// import 'vue-good-table/dist/vue-good-table.css'
// import {VueGoodTable} from 'vue-good-table'
export default {
  components: {
  },
  methods: { //탭으로 변경
    moveTab(num){
      if(num==1){ //상품 정보
        document.querySelector(".review").classList.remove("current");
        document.querySelector(".seller").classList.remove("current");
        document.querySelector(".info").classList.add("current");
        document.querySelector(".review-content").classList.remove("current");
        document.querySelector(".seller-content").classList.remove("current");
        document.querySelector(".info-content").classList.add("current");
      } else if(num==2){ //상품 리뷰
        document.querySelector(".seller").classList.remove("current");
        document.querySelector(".info").classList.remove("current");
        document.querySelector(".review").classList.add("current");        
        document.querySelector(".seller-content").classList.remove("current");
        document.querySelector(".info-content").classList.remove("current");
        document.querySelector(".review-content").classList.add("current");        
      } else if(num==3){ //판매자 정보
        document.querySelector(".review").classList.remove("current");
        document.querySelector(".info").classList.remove("current");
        document.querySelector(".seller").classList.add("current");
        document.querySelector(".review-content").classList.remove("current");
        document.querySelector(".info-content").classList.remove("current");
        document.querySelector(".seller-content").classList.add("current");        
      }
    }
  },
  toggleFilter(val) {
    if (val.status) {
      this.clearFilter(val.value);
      val.status = false;
    } else {
      this.selectedFilters.push(val.value);
      val.status = true;
    }
  },  
data(){
    return {
      GradeType: [
        {name: '전체', value: 'all', status:false},
        {name: '5점', value: 'five', status:false},
        {name: '4점', value: 'four', status:false},
        {name: '3점', value: 'three', status:false},
        {name: '2점', value: 'two', status:false},
        {name: '1점', value: 'one', status:false},
      ],
      ReviewType: [
        {name: '전체', value: 'all', status:false},
        {name: '긍정 리뷰', value: 'positive', status:false},
        {name: '부정 리뷰', value: 'negative', status:false},
      ],
      rows: [
        {
          label: '상품상태',
          field: 'state',
        },
        {
          label: '배송방법',
          field: 'deliverWay',
        },
        {
          label: '배송가능지역',
          field: 'area',
        },
        {
          label: '원산지',
          field: 'origin',
        }, 
        {
          label: '제조년월일 / 유통기한',
          field: 'date',
        },  
        {
          label: '농수산물의 원산지 표시에 관한 법률에 따른 원산지',
          field: 'origin_law',
        }, 
        {
          label: '보관/취급 방법',
          field: 'storageWay',
        },                                  
      ],
      columns: [
        { state:"새상품", deliverWay: "택배", area: "전국", origin: "국내산", date: "항시수확", 
        origin_law: "국내산", storageWay: "직사광선을 피하고 서늘한곳에 보관" },
      ],
    };
  },
  computed: {
    selectedGrade() {
      let res = new Array();
      for (const item of this.GradeType) {
        if (item.status == true) {
          res.push(item.value)
        }
      }
      return res;
    },
    selectedPositive() {
      let res = new Array();
      for (const item of this.ReviewType) {
        if (item.status == true) {
          res.push(item.value)
        }
      }
      return res;
    },    
  },
  toggleFilter(val) {
    if (val.status) {
      this.clearFilter(val.value);
      val.status = false;
    } else {
      this.selectedFilters.push(val.value);
      val.status = true;
    }
  },
};
</script>

<style scoped lang="scss">
@import "@/assets/_variables.scss";
@import "@/assets/common/Base.scss";
@import "@/assets/common/Product.scss";



  ul.tabs {
    background-color: rgb(0, 0, 0, 0) !important;
    padding: 8px 5px;
    margin-bottom: 16px;
    width: 1300px;
    height: 80px;
    margin-left: 238px;
    float: left;
  }

  ul.tabs li {
    width: 308px;
    height: 80px;
    background: none;
    color: #222;
    display: inline-block;
    padding: 10px 15px;
    cursor: pointer;
    border: 1px solid #cccccc;
    font-size: 1.5em;
    text-align: center;
  }

  ul.tabs li.current {
    background: #99004c;
    color: white;
  }

  .tab-content {
    display: none;
    background: #ededed;
    padding: 15px;
  }

  .tab-content.current {
    display: inherit;
  }

  table {
    width: 1300px;
    margin-left: 228px;
    text-align: left;
    border: 10px black;
  }
  
  table tr td.td1{
    background-color:#cccccc;
    width: 300px;
    text-align: center;
    padding: 2%;
    border: 5px white;
  }

  table tr td.td2{
    background-color:#ededed;
    text-align: left;
    padding: 2%;
    border: 5px white;    
  }


</style>
