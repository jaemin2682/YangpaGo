<template>
  <div class="container-base">
    <div class="cart-header-container">
        <div class="cart-title">
        <p><b>장바구니</b></p>
        </div>
    </div>
    <hr/>
    <div class="cart-container">
        <div class="select-contatiner">
            <div class="select-all" @click="checkAll()">전체 선택</div>
            <div class="select-delete" @click="deleteAll()">선택 삭제</div>
        </div>
        <template v-for="(data,idx) in datas">
            <div class="store-container" :key="idx">
                <div class="store-name" >
                    <div class="store-input">
                        <input type="checkbox" v-model="data.isselected" class="store-checkbox checkbox" ref="store-checkbox" value="One" @click="checkStoreItem(data.seller, data.isselected)"/>
                    </div>
                    <div class="store-name-only">{{data.store_name}}</div>
                </div>
                <template v-for="(item, i) in data.items">
                    <CartItem :data=item :key="i" ref="listeditem"/>
                </template>
                <div class="store-price-container">
                    <div class="store-price">
                        <div class="store-name-only"> {{data.store_name}} </div>
                        <div class="store-text">스토어 주문 합계 = </div>
                        <div class="total-price">{{getTotalCountStore(data.seller)}}</div>
                    </div>
                </div>
            </div>
        </template>
        <div class="total-price-container">
            <div class="text"> 결제 예정 금액</div>
            <div class="total-price">{{getTotalCount()}}원</div>
        </div>
        <div class="btn-container">
            <div class="keep-shopping-btn" @click="moveMain()">
                계속 쇼핑하기
            </div>
            <div class="purchase-btn" @click="purchase()">
                구매하기
            </div>
        </div>
    </div>
  </div>
</template>

<script>
import CartItem from "@/components/common/CartItem.vue";
import axios from "axios";
import { mapGetters } from "vuex";
const SERVER_URL = "http://j3a404.p.ssafy.io:8081";
//const SERVER_URL = "http://localhost:8081";

export default {
  name: "Cart",
  data() {
    return {
      datas:[],
    };
  },
  watch: {
      datas: {
          deep: true,
          handler() {
              for(var i = 0; i < this.datas.length; i++){
                  var items = this.datas[i].items;
                  var flag = true;
                  for(var j = 0; j < items.length; j++){
                      flag &= items[j].isselected;
                  }
                  if(flag) this.datas[i].isselected = true;
                  else this.datas[i].isselected = false;
              }
          }
      }
  },
  methods:{
      ...mapGetters({
        getEmail: "user/getEmail",
      }),
      getTotalCountStore(seller){
          let totalprice=0;
          for(var i = 0; i < this.datas.length; i++){
              if(this.datas[i].seller == seller){
                  var items = this.datas[i].items;
                  // 가격 계산
                for(var j = 0; j < items.length; j++){
                    if(items[j].isselected == true){
                        totalprice += items[j].price*items[j].amount;
                    }
                }
              }
          }
          return totalprice;
      },
      getTotalCount(){
          let totalprice=0;
          for(let i=0; i<this.datas.length; i++){
              for(let j=0; j<this.datas[i].items.length; j++){
                  if(this.datas[i].items[j].isselected == true){
                    totalprice += this.datas[i].items[j].price*this.datas[i].items[j].amount;
                  }
              }
          }
          return totalprice;
      },
      checkAll(){
          for(var i = 0; i < this.datas.length; i++){
            this.datas[i].isselected = true;
            var items = this.datas[i].items;
            for(var j = 0; j < items.length; j++){
                items[j].isselected = true;
            }  
          }
      },
      deleteAll(){
          for(var i = 0; i < this.datas.length; i++){
            this.datas[i].isselected = false;
            var items = this.datas[i].items;
            for(var j = 0; j < items.length; j++){
                items[j].isselected = false;
            }  
          }
      },
      checkStoreItem(seller, isselected){
          if(!isselected){
            for(var i = 0; i < this.datas.length; i++){
              if(this.datas[i].seller == seller){
                  var items = this.datas[i].items;
                  //체크
                  for(var j = 0; j < items.length; j++){
                      items[j].isselected = true;
                  }
              }
            }
          } else {
              for(i = 0; i < this.datas.length; i++){
                if(this.datas[i].seller == seller){
                    items = this.datas[i].items;
                    //체크 해제
                    for(j = 0; j < items.length; j++){
                        items[j].isselected = false;
                    }
                }
            }
          }
      },
      moveMain(){
          this.$router.push({ name: "Main" });
      },
      purchase(){
          //구매 리스트 등록
          for(var i = 0; i < this.datas.length; i++){
              for(var j = 0; j < this.datas[i].items.length; j++){
                  var item = this.datas[i].items[j];
                  if(item.isselected == true){
                      // 구매 목록 등록
                      axios
                        .post(
                            `${SERVER_URL}/api/purchase/add`,item
                        )
                        .catch(err => console.log(err));
                  }
              }
          }
          alert("구매 완료")
          this.$router.push({ name: "Main" });
          
      }
  },
  mounted(){
      this.checkAll();
      // 이메일로 장바구니 리스트 조회
      axios
        .get(
        `${SERVER_URL}/api/cart/search/email/${this.getEmail()}`
        )
        .then(response => {
            var data = response.data;
            // var sellers = Object.keys(response.data);
            for (var store_name in data) {
                var cart = {
                    store_name: store_name,
                    items: [],
                    isselected: false
                }
                for(var i = 0; i < data[store_name].length; i++){
                    data[store_name][i]["isselected"] = false;
                    // console.log(data[seller][i]);
                    cart.items.push(data[store_name][i]);
                }
                this.datas.push(cart);
            }
            // console.log(this.datas);
        })
        .catch(err => console.log(err));
  },
  components: {
      CartItem,
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/common/Cart.scss";
</style>

