import Vue from "vue";
import VueRouter from "vue-router";
import mainPageRouter from "@/router/modules/main.js";
import settingPageRouter from "@/router/modules/setting.js";
import accountPageRouter from "@/router/modules/account.js";
import articlePageRouter from "@/router/modules/article.js";
import storePageRouter from "@/router/modules/store.js";
import recipePageRouter from "@/router/modules/recipe.js";
import productListPageRouter from "@/router/modules/productlist.js";
import userPageRouter from "@/router/modules/userpage.js";

Vue.use(VueRouter);

const router = new VueRouter({
  mode: "history",
  routes: [
    {
      path: "/",
      redirect: "/main"
    },
    {
      path: "/main",
      redirect: "/main/home",
      name: "Main",
      component: () => import("@/views/Main.vue"),
      children: mainPageRouter,
    },
    {
      path: "/login",
      name: "Login",
      component: () => import("@/views/Login.vue"),
    },
    {
      path: "/join",
      name: "Join",
      component: () => import("@/views/Join.vue"),
    },
    {
      path: "/joinselect",
      component: () => import("@/views/JoinSelect.vue")
    },
    {
      path: "/terms",
      component: () => import("@/views/Terms.vue")
    },
    {
      path: "/setting",
      redirect: "/setting/blog",
      name: "Setting",
      component: () => import("@/views/Setting.vue"),
      children: settingPageRouter,
    },
    {
      path: "/account",
      redirect: "/main/home",
      name: "Account",
      component: () => import("@/views/Main.vue"),
      children: accountPageRouter,
    },
    {
      path: "/article",
      redirect: "/article/edit",
      name: "Article",
      component: () => import("@/views/Article.vue"),
      children: articlePageRouter,
    },
    // {
    //   path: "/store",
    //   redirect : "/store/home",
    //   name: "Store",
    //   component: () => import("@/views/store/Store.vue"),
    //   children : storePageRouter,
    // },
    {
      path: "/store/:email",
      redirect : "/store/:email/home",
      name: "Store",
      component: () => import("@/views/store/Store.vue"),
      children : storePageRouter,
    },
    {
      path: "/storeSetting",
      name: "StoreSetting",
      component: () => import("@/views/store/StoreSetting.vue"),
    },
    {
      path: "/product",
      name: "Product",
      component: () => import("@/views/Product2.vue"),
    },
    {
      path: "/product/:pid",
      name: "Product",
      component: () => import("@/views/Product2.vue"),
    },
    {
      path: "/editproduct/:pid",
      name: "EditProduct",
      component: () => import("@/views/store/ProductEdit.vue"),
    },
    {
      path: "/addproduct",
      name: "AddProduct",
      component: () => import("@/views/store/AddProduct.vue"),
    },
    {
      path: "/productlist",
      redirect: "/productlist/all",
      name: "ProductList",
      component: () => import("@/views/ProductList.vue"),
      children : productListPageRouter,
    },
    {
      path: "/recipe",
      redirect : "/recipe/home",
      name: "Recipe",
      component: () => import("@/views/Recipe.vue"),
      children : recipePageRouter,
    },
    {
      path: "/cart",
      name: "Cart",
      component: () => import("@/views/Cart.vue"),
    },
    {
      path: "/userpage",
      redirect : "/userpage/info",
      name: "UserPage",
      component: () => import("@/views/user/UserPage.vue"),
      children : userPageRouter,
    },
    {
      path: "/logo",
      name: "Logo",
      component: () => import("@/views/Logo.vue"),
    },
    {
      path: "/intro",
      name: "Intro",
      component: () => import("@/views/Intro.vue"),
    },
    {
      path: "/:email",
      name: "Blog",
      component: () => import("@/views/blog/Blog.vue"),
    }
  ],
  // 스크롤 맨 위로
  scrollBehavior() {
    return { x: 0, y: 0 };
  },
});

export default router;
