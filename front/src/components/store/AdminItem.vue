<template>
    <div class="item-container">
        <div class="item-img-container">
            <img :src="item.img"/>
        </div>
        <div class="item-body-container">
            <div class="store-name"><p>{{item.store_name}}</p></div>
            <div class="product-name"><p>{{item.name}}</p></div>
        </div>
        <div class="item-option-container">
            <div class="price">{{item.price}}원</div>
            <div class="option">({{item.kg}}kg)</div>
        </div>
        <div class="item-button-container">
            <button class="openReview-btn" @click="goUpdateItemPage()">수정</button>
            <button class="delete-btn" @click="deleteItem">삭제</button>
        </div>
    </div>
</template>

<script>
import axios from "axios";
import { mapGetters } from "vuex";
const SERVER_URL = "http://j3a404.p.ssafy.io:8081";
export default {
    props:{
        item:{
            type: Array,
            required: true
        },
        sellerEmail: {type: String},
        // ItemData:{
        //     type: [],
        //     required: true
        // }
    },
    methods:{
        ...mapGetters({
            getUserId: "user/getUserId",
            getEmail: "user/getEmail"
        }),
        deleteItem() {
            console.log(this.item.id);
            if(confirm("해당 상품을 삭제하시겠습니까?") == true) {
                //상품 sub 이미지 삭제
                axios.delete(`${SERVER_URL}/api/product/delete/picture/${this.item.id}`)
                .then(response => {
                    alert("삭제가 완료되었습니다.");
                    console.log(response);
                })
                .catch(err => console.log(err));

                // 상품 기본 정보 삭제
                axios.delete(`${SERVER_URL}/api/product/delete/${this.item.id}`)
                .then(response => {
                    alert("삭제가 완료되었습니다.");
                    console.log(response);
                    this.$emit('delete-event');
                })
                .catch(err => console.log(err));
                
            }
        },
        goUpdateItemPage() {
            this.$router.push(`/store/editproduct/${this.item.id}`);
        }

    }
}
</script>

<style scoped lang="scss">
.item-container{
    width: 100%;
    height: 120px;
    border-bottom: 0.5px solid lightgray;
    .item-img-container{
        float: left;
        padding: 15px 0px 0px 3px;
        width: 19%;
        height: 120px;
        img{
            border: 1px solid rgba(lightgray, 0.5);
            width: 105px;
            height: 90px;
        }
    }
    .item-body-container{
        float: left;
        height: 120px;
        padding: 10px;
        // width: 350px;
        width: 29%;
        display: flex;
        flex-direction: column;
        flex-grow: 1;
        position: relative;
        align-items: flex-start;
        div{
            display: flex;
            flex-direction: row;
            flex-grow: 0;
        }
        .store-name{
           padding-top:30px;
           color: #a7496d;
           font-weight: bolder;
           font-size: 0.8rem;
           padding-bottom: 5px;
        }

    }
    .item-option-container{
        float: left;
        height: 120px;
        width:15%;
        padding: 10px;
        display: flex;
        flex-direction: column;
        align-items: center;
        div{
            display: flex;
            flex-direction: row;
        }
        .price{
            padding-top:30px;
        }
    }
    .item-button-container{
        float: right;
        height: 120px;
        padding: 10px;
        display: flex;
        flex-direction: column;
        button{
            flex-grow: 0;
            width:120px;
            padding:5px 10px;
            margin:5px;
            border:1px solid black;
            border-radius: 10px;
        }
        .openReview-btn{
            color:cornflowerblue;
        }
        .purchase-btn{
            color:cornflowerblue;
        }
        .delete-btn{
            color:red;
        }
    }
}
</style>