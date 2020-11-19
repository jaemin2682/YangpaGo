export default [
    {
        path: "home",
        name: "StoreHome",
        component: () => import("@/views/store/StoreHome.vue"),
    },
    {
        path: "info",
        name: "StoreInfo",
        component: () => import("@/views/store/StoreInfo.vue"),
    }, 
    {
        path: "popular",
        name: "StorePopular",
        component: () => import("@/views/store/StorePopular.vue"),
    }, 
    {
        path: "recommend",
        name: "StoreRecommend",
        component: () => import("@/views/store/StoreRecommend.vue"),
    }, 
    {
        path: "price",
        name: "StorePrice",
        component: () => import("@/views/store/StorePrice.vue"),
    },
    {
        path: "all",
        name: "StoreAll",
        component: () => import("@/views/store/StoreAll.vue"),
    },
    {
        path: "admin",
        name: "StoreAdmin",
        component: () => import("@/views/store/StoreAdmin.vue"),
    }, 
];