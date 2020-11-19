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
                <div class="navbar-item info">
                    <router-link tag="button" to="/userpage/info">
                    <a href="#" v-on:click="makeActive('info')">내 정보</a>
                    </router-link>
                </div>
                <div class="navbar-item order">
                    <router-link tag="button" to="/userpage/order">
                    <a href="#" v-on:click="makeActive('order')">주문조회</a>
                    </router-link>
                </div>
                <div class="navbar-item pick">
                    <router-link tag="button" to="/userpage/pick">
                    <a href="#" v-on:click="makeActive('pick')">찜한상품</a>
                    </router-link>
                </div>
            <div class="navbar-item recent">
                 <router-link tag="button" to="/userpage/recent">
                <a href="#" v-on:click="makeActive('recent')">최근 본 상품</a>
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
        active:'info'
        };
    },
    methods:{
     makeActive: function(item){
        this.active = item;
        }
    }
};
</script>


<style lang="scss" scoped>
@import "@/assets/user/userpageNavbar.scss";
</style>