<template>
  <div class="modal" v-if="show">
    <div class="background" @click="closeModal"></div>
    <div class="modal-content">
        <div class="page1-container">
            <div v-if="this.pageNum1===0" class="cover">
                <p>MY<br>COOKBOOK</p>
            </div>
            <div v-else>
               <FlexRecipeBook
              :datas="paginatedData1"/>
            </div>
        </div>
        <div class="line"/>
        <div class="page2-container" ref="page2Container">
            <FlexRecipeBook
            :datas="paginatedData2"/>
        </div>
    </div>
    <div @click="show = false" class="closeModalBtn">
      <img class="closeImg" src="@/assets/images/icons/close.png"/>
    </div>
    <button :disabled="pageNum1 == 0" @click="prevPage" class="prev-btn">
                <img class="prev-btn-img" src="@/assets/images/icons/next2.png">
    </button>
    <button :disabled="pageNum1 >= pageCount-1" @click="nextPage" class="next-btn">
               <img class="next-btn-img" src="@/assets/images/icons/next2.png">
    </button>
  </div>
</template>

<script>
import FlexRecipeBook from "@/components/recipe/FlexRecipeBook.vue";
export default {
  data() {
    return {
      command: null,
      show: false,
      tab: 1,
      pageNum1: 0,
      pageNum2: 1,
    };
  },
  props:{
      listArray:{
          required: true
      },
      pageSize:{
          type:Number,
          requered:false,
          default:9,
      }
  },
  methods: {
    showModal(command) {
      // Add the sent command
      console.dir(command);
      this.command = command;
      this.show = true;
    },
    closeModal() {
      this.show = false;
      this.imageSrc = [];
      this.tab = 1;
    },
    nextPage(){
        this.pageNum1 += 2;
        this.pageNum2 += 2;
        this.$refs.page2Container.booklet();
    },
    prevPage(){
        this.pageNum1 -= 2;
        this.pageNum2 -= 2;
    }
  },
  created(){
    console.log("여기"+this.listArray[0]);
  },
  computed: {
    pageCount () {
      let listLeng = this.listArray.length,
          listSize = this.pageSize,
          page = Math.floor(listLeng / listSize);
      if (listLeng % listSize > 0) page += 1;
      
      /*
      아니면 page = Math.floor((listLeng - 1) / listSize) + 1;
      이런식으로 if 문 없이 고칠 수도 있다!
      */
      return page;
    },
    paginatedData1() {
      const start = (this.pageNum1-1) * this.pageSize,
            end = start + this.pageSize;
      return this.listArray.slice(start, end);
    },
    paginatedData2() {
      const start = (this.pageNum2-1) * this.pageSize,
            end = start + this.pageSize;
      console.log("여기"+this.listArray[0]);
      return this.listArray.slice(start, end);
    },
  },
  components: {
    FlexRecipeBook,
  }
};
</script>

<style scoped lang="scss">
 @import "@/assets/recipe/recipeBook.scss";
 .background {
  position: fixed;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.4);
}
.closeImg{
  width: 100px;
  height: 100px;
}


</style>