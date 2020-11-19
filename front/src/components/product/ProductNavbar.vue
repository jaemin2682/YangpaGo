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

            <!--탭바-->
            <nav class="navbar-body" v-bind:class="active" v-on:click.prevent>
                <div class="navbar-item all">
                    <router-link tag="button" to="/productlist/all">
                    <a href="#" v-on:click="makeActive('all')">전체 상품 리스트</a>
                    </router-link>
                </div>
                <div class="navbar-item over">
                    <router-link tag="button" to="/productlist/over">
                    <a href="#" v-on:click="makeActive('over')">과잉 생산 농산물</a>
                    </router-link>
                </div>
                <div class="navbar-item bgrade">
                    <router-link tag="button" to="/productlist/bgrade">
                    <a href="#" v-on:click="makeActive('bgrade')">B급 농산물</a>
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
        active:'all',
        searchWord: ""
        };
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
        search: function(){
            this.$router.push(`/productlist/search?word=${this.searchWord}`);
        }
        },
        mounted() {
            this.setInitialActive();
        }
};
</script>


<style lang="scss" scoped>
@import "@/assets/product/productNavbar.scss";
</style>