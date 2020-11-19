<template>
    <div class="item-container">
        <div class="item-img-container">
            <img v-bind:src="item.img"/>
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
            <!-- <button class="purchase-btn">구매하기</button> -->
            <button @click="del" class="delete-btn">삭제</button>
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
        pagetype: {type: String}
    },
    methods:{
        ...mapGetters({
            getUserId: "user/getUserId",
            getEmail: "user/getEmail"
        }),
        del() {
            console.log(this.item.id);
            if(this.pagetype.type == "pick") {
                if(confirm("해당 상품을 찜한 상품 목록에서 삭제하시겠습니까?") == true) {
                    axios.delete(`${SERVER_URL}/api/userlike/delete/${this.getUserId()}/${this.item.id}`)
                    .then(response => {
                        alert("삭제가 완료되었습니다.");
                        console.log(response);
                        this.$emit('getList');
                        this.$router.push({ name: "UserPagePick" });
                    })
                    .catch(err => console.log(err));
                }
            }
            else if(this.pagetype.type == "recent") {
                if(confirm("해당 상품을 최근 본 상품 목록에서 삭제하시겠습니까?") == true) {
                    axios.delete(`${SERVER_URL}/api/view/delete?email=${this.getEmail()}&product_id=${this.item.id}`)
                    .then(response => {
                        alert("삭제가 완료되었습니다.");
                        console.log(response);
                        this.$emit('getList');
                        this.$router.push({ name: "UserPageRecent" });
                    })
                    .catch(err => console.log(err));
                }
            }
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
        padding: 10px;
        width: 15%;
        height: 120px;
        img{
            max-width: 100px;
            max-height: 100px;
        }
    }
    .item-body-container{
        float: left;
        height: 120px;
        padding: 10px;
        // width: 350px;
        width: 30%;
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
        padding-top: 35px;
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