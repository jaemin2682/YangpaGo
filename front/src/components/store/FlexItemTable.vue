<template>
  <div class="container-item">
      <div class="items-table">
        <thead>
        <tr>
            <th class="img-title">사진</th>
            <th class="name-title">상품명</th>
            <th class="price-title">가격</th>
            <th class="button-title">기능</th>
        </tr>
        </thead>
        <tbody>
            <tr>
                <td>
                    <img src="http://j3a404.p.ssafy.io/images/postRep/20201005095846_potato.jpg"/> 
                </td>
                <td>감자</td>
                <td>
                    <div>
                        10200원
                    </div>
                    <div>
                        kg
                    </div>
                </td>
                <td>
                    <button>수정</button>
                    <button>삭제</button>
                </td>
            </tr>
        </tbody>
      </div>
    <template v-for="(item,idx) in datas">
      <StoreItem :key="idx" :item="item"/>
    </template>
  </div>
</template>

<script>
import StoreItem from "@/components/store/StoreItem.vue";

export default {
  components: { StoreItem },
  props: {
    datas: [],
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/store/FlexItemTable.scss";
</style>
