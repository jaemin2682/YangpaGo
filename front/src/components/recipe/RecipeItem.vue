<template>
  <div ref="recipe-item" class="recipe-item">
      <DetailModal ref="DetailModal" :id="item.id"/>
      <div ref="picture-item" class="picture-item" @click="openDetailModal()">
        <img :src="item.picture">
      </div>
      <div class="body-item">
        <p>{{item.name}}</p>
      </div>
  </div>
</template>

<script>
import DetailModal from "@/components/recipe/DetailModal.vue";
export default {
   components: {
    DetailModal,
  },
  methods:{
    openDetailModal() {
      this.$refs.DetailModal.showModal();
    },
  },
  props: {
    item: [],
  }
}
</script>

<style scoped lang="scss">
 @import "@/assets/recipe/recipeItem.scss";
</style>