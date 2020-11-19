<template>
    <div class="item-container">
        <div class="item-img-container">
            <img v-bind:src="item.img"/>
        </div>
        <div class="item-body-container">
            <div class="store-name"><p>{{item.storeName}}</p></div>
            <div class="product-name"><p>{{item.productName}}</p></div>
        </div>
        <div class="item-option-container">
            <div class="price">{{item.defaultPrice}}원</div>
            <div class="option">(/{{item.defaultOption}})</div>
        </div>
        <div class="item-button-container">
            <button class="purchase-btn">구매하기</button>
            <button class="delete-btn">삭제</button>
        </div>
    </div>
</template>

<script>
export default {
    props:{
      item:{
          type: Array,
          required: true
        },
    }
}
</script>

<style scoped lang="scss">
 @import "@/assets/user/userpageItem.scss";
</style>