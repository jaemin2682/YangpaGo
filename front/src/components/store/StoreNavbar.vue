<template>
    <main>
        <div class="navbar-container">
            <div class="store-search">
          <form class="container-main-search" @submit.prevent="search">
            <input
              type="search"
              class="input-main-search"
              v-model="searchWord"
              @keyup.enter="search"
            />
            <button type="submit" class="btn-main-search desktop">
              검색
            </button>
          </form>
        </div>
        <nav class="navbar-body" v-bind:class="active" v-on:click.prevent>
          <div class="navbar-item home">
            <router-link tag="button" to="home">
            <a href="#" v-on:click="makeActive('home')">스토어 홈</a>
            </router-link>
          </div>
          <div class="navbar-item info"> 
              <router-link tag="button" to="info">
              <a href="#" v-on:click="makeActive('info')">스토어 정보</a>
              </router-link>
          </div>
          <div class="navbar-item popular">
              <router-link tag="button" to="popular">
              <a href="#" v-on:click="makeActive('popular')">인기 상품</a>
              </router-link>
          </div>
          <div class="navbar-item recommend">
              <router-link tag="button" to="recommend">
              <a href="#" v-on:click="makeActive('recommend')">추천상품</a>
              </router-link>
          </div>
          <div class="navbar-item price">
              <router-link tag="button" to="price">
              <a href="#" v-on:click="makeActive('price')">시세보다 저렴한 상품</a>
              </router-link>
          </div>
          <div class="navbar-item all">
              <router-link tag="button" to="all">
              <a href="#" v-on:click="makeActive('all')">전체 상품 목록</a>
              </router-link>
          </div>
          <div class="navbar-item admin" v-if="isISeller == 0">
              <router-link tag="button" to="admin">
              <a href="#" v-on:click="makeActive('admin')">스토어 관리</a>
              </router-link>
          </div>
        </nav>
        </div>
    </main>
</template>

<script>
export default {
    data:function() {
        return {
        active:'home',
        };
    },
    props: {
      sellerEmail:{
          type:String,
          requered:false,
          default:"",
      },
      isISeller:{
        type:Boolean,
        requered:false,
        default:false,
      },
    },
    methods:{
     makeActive: function(item){
        this.active = item;
     },
     setInitialActive: function(){
            var url = document.location.href.split('/');
            var item = url[url.length -1];
            this.active = item;
      },
    },
    mounted() {
        this.setInitialActive();
    }
};
</script>


<style lang="scss" scoped>
@import "@/assets/store/storeNavbar.scss";
</style>