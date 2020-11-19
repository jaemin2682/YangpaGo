export default [
    {
      path: "all",
      name: "ProductAll",
      component: () => import("@/views/product/ProductAll.vue")
    },
    {
      path: "over",
      name: "OverProduct",
      component: () => import("@/views/product/OverProduct.vue")
    },
    {
      path: "bgrade",
      name: "BGradeProduct",
      component: () => import("@/views/product/BGradeProduct.vue")
    },
    {
        path: "search",
        name: "SearchProduct",
        component: () => import("@/views/product/SearchProduct.vue")
    }
];
  