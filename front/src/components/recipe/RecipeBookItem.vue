<template>
  <div ref="recipe-book-item" class="recipe-book-item" @click="openDetailModal()">
    <DetailModal ref="DetailModal" :id="recipedata.id" />
      <div ref="picture-item" class="picture-item">
        <img :src="recipedata.picture"/>
      </div>
      <div class="body-item">
        <h5>{{recipedata.name}}</h5>
      </div>
  </div>
</template>

<script>
import DetailModal from "@/components/recipe/DetailModal.vue";
export default {
    components: {
    DetailModal,
    },
    props : {
        recipedata: [],
    },
    methods:{
    openDetailModal() {
      this.$refs.DetailModal.showModal();
    
    },
  },
}
</script>
<style scoped lang="scss">
 @import "@/assets/recipe/recipeBookItem.scss";
</style>
