<template>
    <div class="container-base">
        <div class="add-product-header">
            <img class="back-btn" src="@/assets/images/left_arrow_btn.svg"/>
            <button class="complete-btn" @click="editProduct">작성 완료</button>
        </div>
        <div class="add-product-body">
            <div class="container-add-header">상품 기본 정보 입력</div>
            <div class="container-add-header">상품 설명</div>
            <div class="container-add-body">
                <div class="container-add">
                    <div class="pictures-container">
                        <div class="pictures-container-title">상품 사진</div>
                        <div class="main-picture">
                            <div class="main-img-title">대표 사진</div>                       
                            <form v-if="main_image.isUpload == false" class="main-img-upload-label">
                                <label  for="main-img-upload">+</label>
                                <input v-if="!main_image.isUpload" type="file" id="main-img-upload" @change="previewMainImage" multiple style="display:none"/>
                            </form>
                            <form v-else class="main-img">
                                <img :src="main_image.previewUrl">
                                <button class="main-delete-btn" @click="deleteMainImg">X</button>
                            </form>
                        </div>
                        <div class="sub-pictures">
                            <template v-for="(item) in sub_images">
                                <div class="sub-picture" :key="item.id">
                                    <form v-if="item.isUpload == false" class="sub-img-upload-label">
                                        <label :for="'sub-img-upload-'+item.id">+</label>
                                        <input type="file" :id="'sub-img-upload-'+item.id" @change="previewSubImage(item.id)" multiple style="display:none"/>   
                                    </form>
                                    <form v-else class="main-img">
                                        <img :src="item.previewUrl">
                                        <button class="sub-delete-btn" @click="deleteSubImg(item.id)">X</button>
                                    </form>
                                </div>
                            </template>
                        </div>
                    </div>
                    <div class="info-container">
                        <div class="info-title">상품 제목</div>
                        <input class="info-input" type="text" v-model="productinfo.name"/>
                    </div>
                    <div class="info-container">
                        <div class="info-title">상품 가격</div>
                        <input class="info-short-input" type="text" v-model="productinfo.price"/>
                        <div class="text-info">원</div>
                        <div class="text-info-medium">구매 단위</div>
                        <input class="info-short-input" type="text" v-model="productinfo.kg"/>
                        <div class="text-info">kg</div>
                    </div>
                    <div class="info-container">
                        <div class="info-title">상품 상태</div>
                        <div class="status-radio">
                            <label class="box-radio-input" >
                                <input type="radio" name="cp_item" value="특" checked="checked" v-model="productinfo.status"><span>특</span>
                            </label>
                            <label class="box-radio-input">
                                <input type="radio" name="cp_item" value="상" v-model="productinfo.status"><span>상</span>
                            </label>
                            <label class="box-radio-input">
                                <input type="radio" name="cp_item" value="보통" v-model="productinfo.status"><span>보통</span>
                            </label>
                            <label class="box-radio-input">
                                <input type="radio" name="cp_item" value="등급 외" v-model="productinfo.status"><span>등급 외</span>
                            </label>
                        </div>
                    </div>
                    <div class="info-container">
                        <div class="info-title">배송정보</div>
                        <div class="text-info">배송비</div>
                        <input class="info-short-input" type="text" v-model="productinfo.delevery_price"/>
                        <div class="text-info">원</div>
                        <div class="text-info-long">배송가능지역</div>
                        <input class="info-short-input" type="text" v-model="productinfo.delevery_location"/>
                    </div>
                    <div class="info-container">
                        <div class="info-title">원산지</div>
                        <input class="info-short-input" type="text" v-model="productinfo.origin"/>
                    </div>
                    <div class="info-container">
                        <div class="info-title">농수산물의 원산지 표시에<br> 관한 법률에 따른 원산지</div>
                        <input class="info-short-input" type="text" v-model="productinfo.law_origin"/>
                    </div>
                    <div class="info-container">
                        <div class="info-title">보관/취급 방법</div>
                        <input class="info-price-input" type="text"  v-model="productinfo.keep_method"/>
                    </div>
                    <div class="info-container">
                        <div class="info-title">제조년월일/유통기한</div>
                        <input class="info-short-input" type="text" v-model="productinfo.life_time"/>
                    </div>
                </div>
                <div class="container-add">
                    <div class="container-article-edit">
                        <editor ref="editor"></editor>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Editor from "@/components/user/article/Editor.vue";
import { mapGetters } from "vuex";
import axios from "axios";
import router from '@/router/index.js'
const SERVER_URL = "http://j3a404.p.ssafy.io:8081";
export default {
  name: "EditProduct",
  components: {
      Editor
  },
  data() {
    return {
        pid: this.$route.params.pid,
        imgnum: 8,
        main_image: {isUpload: false, previewUrl:"", isUpdated:false,},
        sub_images: [{id: 0, isUpload: false, previewUrl:"", isUpdated:false,},
                    {id: 1, isUpload: false, previewUrl:"", isUpdated:false,},
                    {id: 2, isUpload: false, previewUrl:"", isUpdated:false,},
                    {id: 3, isUpload: false, previewUrl:"", isUpdated:false,},
                    {id: 4, isUpload: false, previewUrl:"", isUpdated:false,},
                    {id: 5, isUpload: false, previewUrl:"", isUpdated:false,},
                    {id: 6, isUpload: false, previewUrl:"", isUpdated:false,},
                    {id: 7, isUpload: false, previewUrl:"", isUpdated:false,}],
        main_image_formData: null,
        sub_images_formData:[],
        productinfo: {
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
    };
  },
  mounted(){
    this.fetchProductData();
  },
  methods: {
      ...mapGetters({
      getUserInfo: "user/getUserInfo"
      }),
      previewMainImage(event) {
          this.main_image.isUpload = true;
          this.main_image.isUpdated = false;    //새로 올린거니까 업데이트 필요
          this.main_image.previewUrl = URL.createObjectURL(event.target.files[0]);
          console.log(this.main_image.previewUrl);
          const file = event.target.files[0];
          var formData = new FormData();
          formData.append("filename", file);
          this.main_image_formData = formData;
      },
      previewSubImage(id) {
          this.sub_images[id].isUpload = true;
          this.sub_images[id].isUpdated = false; //새로 올린거니까 업데이트 필요
          this.sub_images[id].previewUrl = URL.createObjectURL(event.target.files[0]);
          console.log(this.sub_images[id].previewUrl);
          const file = event.target.files[0];
          var formData = new FormData();
          formData.append("filename", file);
          this.sub_images_formData[id] = formData;
      },
      deleteMainImg() {
          this.main_image.isUpload = false;
          this.main_image.isUpdated = false;
          this.main_image.previewUrl = "";
          this.main_image_formData = null;
          this.productinfo.img = null;
      },
      deleteSubImg(id){
          for(var i=0; i<this.productinfo.product_img.length; i++){
            if(this.productinfo.product_img[i] == this.sub_images[id].previewUrl)
                this.productinfo.product_img[i].remove(i);
          }
          this.sub_images[id].isUpload = false;
          this.main_image.isUpdated = false;
          this.sub_images[id].previewUrl = "";
          this.sub_images_formData[id] = null;
          
      },
      async editProduct(){
          //나머지 배열 데이터 추가
          var main_img_result = new Object();
          if(this.main_image.isUpload){
              if(!this.main_image.isUpdated){
                try{
                    const MainImageResult = await axios
                    .put(
                            `${SERVER_URL}/api/product/upload?`,this.main_image_formData
                            );
                    if(MainImageResult){
                        this.productinfo.img = MainImageResult.data;
                        main_img_result = MainImageResult.data;
                    }
                }catch(error){
                    console.log(error);
                } 
              }
              else{ //저번에 올린 글 그대로일때
                console.log("Original image survived");
              }
          }

          var sub_img_result = new Array();
          for(var i=0; i<8; i++){
              if(this.sub_images[i].isUpload && !this.sub_images[i].isUpdated){  //업데이트가 되지 않은것만 축
                  try{
                      const subImageResult = await axios
                        .put(
                                `${SERVER_URL}/api/product/upload?`,this.sub_images_formData[i]
                                );
                        if(subImageResult){
                            console.log(subImageResult.data);
                            this.productinfo.product_img.push(subImageResult.data);
                            sub_img_result.push(subImageResult.data);
                        }
                  }catch(error){
                  console.log(error);
                 }
              }
          }

          //판매자 이메일 추가
          this.productinfo.seller = this.getUserInfo().email;

          //내용 가져오기
          this.productinfo.content = this.$refs.editor.$refs.editorContent.editor.view.dom.innerHTML; 
          
          console.log("상품 정보");
          console.log(main_img_result);
          console.log(this.productinfo.product_img);
          //productinfo보내기
           axios
                .put(
                        `${SERVER_URL}/api/product/update`,{
                                id:this.productinfo.id,
                                img:this.productinfo.img,
                                content:this.productinfo.content,
                                delevery_location:this.productinfo.delevery_location,
                                delevery_method:this.productinfo.delevery_method,
                                delevery_price: this.productinfo.delevery_price,
                                name:this.productinfo.name,
                                price:this.productinfo.price,
                                kg: this.productinfo.kg,
                                status:this.productinfo.status,
                                origin:this.productinfo.origin,
                                law_origin:this.productinfo.law_origin,
                                keep_method:this.productinfo.keep_method,
                                product_img:this.productinfo.product_img,
                                seller:this.productinfo.seller,
                                life_time: this.productinfo.life_time,
                        }
                        )
                        .then(response => {
                        console.log(response.data);
                        router.push({path : `/store/${this.getUserInfo().email}`})
                        })
                .catch(err => console.log(err));

            
      },
      fetchProductData(){
           axios
                .get(
                `${SERVER_URL}/api/product/search/pid/${this.pid}`
                )
                .then(response => {
                    this.productinfo.img = response.data.img;
                    this.productinfo.content = response.data.content;
                    this.productinfo.delevery_location = response.data.delevery_location;
                    this.productinfo.delevery_method = response.data.delevery_method;
                    this.productinfo.delevery_price = response.data.delevery_price;
                    this.productinfo.name = response.data.name;
                    this.productinfo.price = response.data.price;
                    this.productinfo.kg = response.data.kg;
                    this.productinfo.status = response.data.status;
                    this.productinfo.origin = response.data.origin;
                    this.productinfo.law_origin = response.data.law_origin;
                    this.productinfo.keep_method = response.data.keep_method;
                    
                    if(response.data.product_img != null){
                        this.productinfo.product_img = response.data.product_img;
                    }
                    this.productinfo.seller = response.data.seller;
                    this.productinfo.life_time = response.data.life_time;
                    this.productinfo.id = this.pid;
                    console.log(response.data);

                    //서브이미지 불러오기
                    axios
                    .get(
                    `${SERVER_URL}/api/product/search/picture/${this.pid}`
                    ).then(response => {
                        if(response.data != null){
                            for(var j=0; j<response.data.length; j++){
                                this.productinfo.product_img.push(response.data[j].path);
                                this.sub_images[j].isUpload = true;
                                this.sub_images[j].isUpdated = true; //이게 true면 사진을 올리면 안됨!!
                                this.sub_images[j].previewUrl = response.data[j].path;
                            }
                        }
                    }).catch(err => console.log(err));

                    //글 불러오기
                    this.$refs.editor.editor.setContent(response.data.content);

                    //사진 불러오기
                    if(response.data.img != null && response.data.img != ""){
                        this.main_image.isUpload = true;
                        this.main_image.previewUrl = response.data.img;
                        this.main_image.isUpdated = true;   //이게 true면 사진을 올리면 안됨!!
                    }

                })
                .catch(err => console.log(err));
      }
  }
  
};
</script>

<style lang="scss" scoped>
@import "@/assets/common/AddProduct.scss";
</style>

