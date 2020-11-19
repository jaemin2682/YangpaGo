from flask import Flask
from flask import request
from recommend_products import get_user_behavior_data, recommend_products, get_user_behavior_data_by_seller
from urllib.parse import quote # url 한글 인코딩 Error 해결

app = Flask(__name__)

@app.route('/productrec')
def productRecommend():
    userid = request.args.get('userid', 'userid')
    num = request.args.get('num', 'num')
    print(userid)
    print(num)
    if(get_user_behavior_data() != 0):
        df_svd_preds, df_products, df_ratings, user_id_index = get_user_behavior_data()
        already_rated, predictions = recommend_products(df_svd_preds, int(userid), df_products, df_ratings, user_id_index, int(num))
        print(predictions)
        return predictions
    else:
        return {}

@app.route('/productrec/store')
def productRecommendByStore():
    userid = request.args.get('userid', 'userid')
    num = request.args.get('num', 'num')
    seller = request.args.get('seller', 'seller')
    print(userid)
    print(num)
    print(seller)
    if(get_user_behavior_data_by_seller(str(seller)) != 0):
        df_svd_preds, df_products, df_ratings, user_id_index = get_user_behavior_data_by_seller(str(seller))
        already_rated, predictions = recommend_products(df_svd_preds, int(userid), df_products, df_ratings, user_id_index, int(num))
        print(type(predictions))
        return predictions
    else:
        return {}

if __name__ == '__main__':
    app.run()