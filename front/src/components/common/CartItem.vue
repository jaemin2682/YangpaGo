<template>
    <div class="item-container"> 
        <div class="item-select">
            <div class="item-input">
                <input type="checkbox" v-model="data.isselected" class="item-checkbox checkbox" ref="itemcheckbox" value="One"/>
            </div>
        </div>
        <div class="item-img-container">
            <img :src="data.img"/>
        </div>
        <div class="item-body">
            <div class="item-title">
                <p>{{data.name}}</p>
            </div>
        </div>
        <div class="item-price-container">
            <div class="item-price">
                <div class="price"><div class="price-only">{{data.price}}</div><div class="won">원</div></div>
            </div>
            <div class="item-num-counter-container">
                <div class="item-num-counter">
                    <div class="counter-down-btn" @click="counterDown" :disabled="data.amount<=0" >
                        <img src="@/assets/images/icons/crops/counter.png"/>
                    </div>
                    <div class="item-num">{{data.amount}}</div>
                    <div class="counter-up-btn" @click="counterUp" >
                        <img src="@/assets/images/icons/crops/counter.png"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
  props: {
    data: {
        type: Object,
        required: true,
    }
  },
  methods: {
    counterUp() {
      this.data.amount += 1;
    },
    counterDown(){
     if(this.data.amount - 1 < 0)
        return;
     this.data.amount -= 1;
    },
    selectAll(){
        alert("hi");
        this.$refs.itemcheckbox.checked=true;
    }
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/common/CartItem.scss";
</style>