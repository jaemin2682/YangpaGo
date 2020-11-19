export default [
    {
      path: "home",
      name: "RecipeHome",
      component: () => import("@/views/recipe/RecipeHome.vue")
    },
    {
      path: "recommend",
      name: "RecipeRecommend",
      component: () => import("@/views/recipe/RecipeRecommend.vue")
    },
    {
      path: "popular",
      name: "RecipePopular",
      component: () => import("@/views/recipe/RecipePopular.vue")
    },
    {
      path: "all",
      name: "RecipeAll",
      component: () => import("@/views/recipe/RecipeAll.vue")
    },
    {
      path: "search",
      name: "RecipeSearch",
      component: () => import("@/views/recipe/RecipeSearch.vue")
    },
    {
      path: "mybook",
      name: "MyRecipeBook",
      component: () => import("@/views/recipe/MyRecipeBook.vue")
    },
];
  