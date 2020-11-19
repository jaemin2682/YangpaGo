export default [
    {
      path: "info",
      name: "UserPageInfo",
      component: () => import("@/views/user/UserPageInfo.vue")
    },
    {
      path: "order",
      name: "UserPageOrder",
      component: () => import("@/views/user/UserPageOrder.vue")
    },
    {
      path: "pick",
      name: "UserPagePick",
      component: () => import("@/views/user/UserPagePick.vue")
    },
    {
      path: "recent",
      name: "UserPageRecent",
      component: () => import("@/views/user/UserPageRecent.vue")
    }
];
  