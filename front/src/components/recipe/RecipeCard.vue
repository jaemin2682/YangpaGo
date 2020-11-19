<template>
  <div ref="recipe-card" class="recipe-card" @click="openDetailModal()">
    <DetailModal ref="DetailModal" :id="item.id"/>
    <div class="line"/>
    <div class="body-item">
        <p>{{item.name}}</p>
    </div>
    <div ref="picture-recipe" class="picture-recipe">
        <img :src="item.picture"/>
    </div> 
  </div>
</template>

<script>
import DetailModal from "@/components/recipe/DetailModal.vue";
export default {
  components: {
    DetailModal,
  },
  props: {
    item: []
  },
  methods:{
    openDetailModal() { 
      this.$refs.DetailModal.showModal();
      this.$parent.$parent.closeModal();
    },
  },
}
</script>
<style scoped lang="scss">
 @import "@/assets/recipe/recipeCard.scss";
</style>
