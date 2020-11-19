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
                <div class="navbar-item home">
                    <router-link tag="button" to="/recipe/home">
                    <a href="#" v-on:click="makeActive('home')">레시피 홈</a>
                    </router-link>
                </div>
                <div class="navbar-item info">
                    <router-link tag="button" to="/recipe/recommend">
                    <a href="#" v-on:click="makeActive('info')">추천레시피</a>
                    </router-link>
                </div>
                <div class="navbar-item popular">
                    <router-link tag="button" to="/recipe/popular">
                    <a href="#" v-on:click="makeActive('popular')">인기레시피</a>
                    </router-link>
                </div>
            <div class="navbar-item recommend">
                 <router-link tag="button" to="/recipe/all">
                <a href="#" v-on:click="makeActive('recommend')">레시피 전체 리스트</a>
                </router-link>
            </div>
            <div class="navbar-item price">
                <router-link tag="button" to="/recipe/mybook">
                <a href="#" v-on:click="makeActive('price')">나의 레시피 북</a>
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
        searchWord: "",
        };
    },
    methods:{
        makeActive: function(item){
            this.active = item;
        },
        search() {
            const keyword = this.searchWord;
            this.keywordSearch(keyword);
        },
        keywordSearch(keyword) {
            this.$router.push({
                name: "RecipeSearch",
                query: { keyword },
            });
            if (this.$route.name === "RecipeSearch") {
                this.$router.go();
            }
        },
    }
};
</script>


<style lang="scss" scoped>
@import "@/assets/recipe/recipeNavbar.scss";
</style>