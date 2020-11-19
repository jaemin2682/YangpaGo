!<template>
  <div class="background">
      <div class="title">
          <div class="text">
          빅데이터를 이용한<br>
          농산물 직거래 플랫폼<br>
          양파고
          </div>
      </div>
      <div class="subtitle">
          <div class="text">
          <div>과잉 생산물을 재료로 하는 레시피 추천</div>
          <div>연관된 관심레시피 추천</div>
          <div>생산량 및 가격예측을 통한 시세분석</div>
          <div>리뷰분석을 통한 정확한 리뷰점수</div>
          </div>
      </div>
      <div class="btn-container">
          <div class="start" @click="moveLogin">시작하기</div>
      </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
    };
  },
  methods:{
      moveLogin(){
          this.$router.push({ name: "Login"}); 
      }
  }
};
</script>

<style scoped lang="scss">
 .background{
    background: url("background.png") no-repeat;
    background-size: cover;
    .title{
        width:100%;
        font-size: 2.1rem;
        font-weight: 1000;
        font-stretch: extra-expanded;
        color:#a7496d;
        .text{
            text-align: left;
            padding-top: 130px;
            padding-left: 200px;
        }
    }
    .subtitle{
        width:100%;
        font-size: 1.2rem;
        font-weight: bolder;
        font-stretch: extra-expanded;
        color:#a7496d;
        .text{
            text-align: left;
            padding-top: 100px;
            padding-left: 200px;
            div{
                margin: 10px 0px;
            }
        }
    }
    .btn-container{
        width:100%;
        height: 400px;
        font-size: 1.4rem;
        font-weight: bolder;
        font-stretch: extra-expanded;
        color:#a7496d;
        padding-top: 100px;
        padding-left: 200px;
        .start{
            text-align: center;
            padding : 20px 30px;
            width: 300px;
            border: 1px solid #a7496d;
            border-radius: 10px;
        }
    }
 }

</style>
