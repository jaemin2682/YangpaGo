<template>
  <div class="container-base">
    <span class="title">
      <span class="keyword">
      {{keyword === undefined ? `#${tag}` : keyword}}</span>
      {{keyword === undefined ? "태그를 포함하는 게시글" : " 검색 결과"}}
    </span>
    <FlexSearchItem :datas="ItemData"/>
    <infinite-loading
        slot="append"
        @infinite="infiniteHandler"
        force-use-infinite-wrapper=".el-table__body-wrapper"
      >
       </infinite-loading>
  </div>
</template>

<script>
import axios from "axios";
import FlexSearchItem from "@/components/store/FlexSearchItem.vue";
import InfiniteLoading from "vue-infinite-loading";
const SERVER_URL = "http://j3a404.p.ssafy.io:8081";

export default {
  components: {
    FlexSearchItem,
    InfiniteLoading
  },
  data: function() {
        return {
            sellerEmail: this.$route.params.email,
            data: [],
            ItemData: [],
            page: 1,
            keyword: this.$route.query.keyword,
            tag: this.$route.query.tag
        };
  },
  methods:{
       infiniteHandler($state) {
      axios
        .get(`${SERVER_URL}/api/main/search?page=${this.page}&word=${this.keyword}`)
        .then(response => {
          if (response.data.length) {
            this.ItemData = this.ItemData.concat(response.data);
            $state.loaded();
            this.page += 1;
            if (this.RecipeData.length / 9 == 0) {
              $state.complete();
            }
          } else {
            $state.complete();
          }
        })
        .catch(err => console.log(err));
    },
        fetchFirstdata(){
        axios
        .get(`${SERVER_URL}/api/main/search?page=${this.page}&word=${this.keyword}`)
        .then(response => {
          if (response.data.length) {
            console.log(response.data);
            this.ItemData = this.ItemData.concat(response.data);
            this.page += 1;
          } 
        })
        .catch(err => console.log(err));
    },
  },
  created() {
    if (this.keyword !== undefined) {
      this.fetchWordResult();
    } else if (this.tag !== undefined) {
      this.fetchHashResult();
    }
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/_variables.scss";
@import "@/assets/common/Base.scss";
@import "@/assets/store/storeHome.scss";
.keyword {
  color: #A7496D;
}
</style>
