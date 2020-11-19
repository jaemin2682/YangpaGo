from flask import Flask
from flask import request
from recommend import get_similar_recipe, select_recipe
from recommend_products import get_user_behavior_data, recommend_products, get_user_behavior_data_by_seller
from recommend_recipe_based_user import get_recipe_by_user, select_recipe2
from insert import productCrawler, Crawler, insert
from review import predict_pos_neg
from predict_final import main
from urllib.parse import quote # url 한글 인코딩 Error 해결

app = Flask(__name__)

@app.route('/reciperec')
def recipeRecommend():
    no = request.args.get('no', 'no')
    similar_rank = get_similar_recipe(int(no))
    similar_info = select_recipe(similar_rank['num'])
    return similar_info

@app.route('/crawling')
def crawling():
    product = request.args.get('product', 'product')
    page_range = range(1, 3)
    page_list = productCrawler(quote(product), page_range)
    for url in page_list:
        recipe = Crawler(url)
        insert(recipe, product)
    return "success"

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

@app.route('/recipebyuserlog')
def recipeRecommend_by_user():
    email = request.args.get('email', 'email')
    similar_rank = get_recipe_by_user(email)
    similar_info = select_recipe2(similar_rank['num'])
    print(similar_info)
    return similar_info

@app.route('/review')
def review():
    word = request.args.get('word', 'word')
    res = predict_pos_neg(word)
    print(res)
    return str(res)

@app.route('/predict')
def predict():
    return main()

if __name__ == '__main__':
    app.run()
