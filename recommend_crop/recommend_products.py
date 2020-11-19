import pandas as pd
import numpy as np
from function import db_con, select_query
from scipy.sparse.linalg import svds


# 사용자 행동 데이터 가져오기
def get_user_behavior_data():
    # DB 연결
    cursor = db_con()

    # 사용자 아이디 인덱스 데이터
    result_user_id = select_query("select u.id as user_id from user u left join user_behavior b on u.id = b.user_id", cursor)
    if(len(result_user_id) == 0):
        return 0

    user_id_index = set()
    for uid in result_user_id:
        user_id_index.add(uid['user_id'])
    print(list(user_id_index))
    user_id_index = list(user_id_index)

    # 사용자 행동 데이터
    result_user = select_query("select u.id as user_id, product_id, rating, date from user u left join user_behavior b on u.id = b.user_id", cursor)
    result_product = select_query("select * from product", cursor)
    # print(result_user)
    # print(result_product)
    df_ratings = pd.DataFrame(result_user, columns=['user_id', 'product_id', 'rating', 'view_date'])
    df_products = pd.DataFrame(result_product, columns=['id', 'name', 'img', 'price', 'kg'])
    # print(df_ratings)
    # print(df_products)

    df_user_crop_ratings = df_ratings.pivot(
        index='user_id',
        columns='product_id',
        values='rating'
    ).fillna(0)
    # print(df_user_crop_ratings)

    # matrix는 pivot_table 값을 numpy matrix로 만든 것
    matrix = df_user_crop_ratings.to_numpy()

    # user_ratings_mean은 사용자의 평균 평점
    user_ratings_mean = np.mean(matrix, axis=1)
    print(user_ratings_mean)
    # R_user_mean : 사용자-농작물에 대해 사용자 평균 평점을 뺀 것.
    matrix_user_mean = matrix - user_ratings_mean.reshape(-1, 1)
    print(matrix_user_mean)
    # scipy에서 제공해주는 svd.
    # U 행렬, sigma 행렬, V 전치 행렬을 반환.
    U, sigma, Vt = svds(matrix_user_mean, k=matrix_user_mean.shape[1]-1)  # k값 한 번 확인(원래는 12)
    sigma = np.diag(sigma)

    # U, Sigma, Vt의 내적을 수행하면, 다시 원본 행렬로 복원이 된다.
    # 거기에 + 사용자 평균 rating을 적용한다.
    svd_user_predicted_ratings = np.dot(np.dot(U, sigma), Vt) + user_ratings_mean.reshape(-1, 1)
    print(svd_user_predicted_ratings)
    df_svd_preds = pd.DataFrame(svd_user_predicted_ratings, columns=df_user_crop_ratings.columns)
    df_svd_preds.head()

    return df_svd_preds, df_products, df_ratings, user_id_index

# 특정 seller의 상품에 대한 사용자 행동 데이터 가져오기
def get_user_behavior_data_by_seller(seller):
    # DB 연결
    cursor = db_con()

    # 스토어의 상품 중 점수가 매겨진 게 없는 경우 예외처리
    store_rating_num = select_query("select * from user_behavior u join product p on u.product_id = p.id where p.seller = '"+str(seller)+"'", cursor)
    print("================store_rating_num=============");
    if(len(store_rating_num) == 0):
        return 0

    # 사용자 아이디 인덱스 데이터
    result_user_id = select_query(
                                """select u.id as user_id, product_id, rating, date
                                           from user u 
                                           left join (select u.user_id, u.product_id, u.rating, u.date
                                                      from user_behavior u join product p 
                                                      on u.product_id = p.id 
                                                      where p.seller = '""" + str(seller) + """') ub
                                       on u.id = ub.user_id""",
                                  cursor)
    user_id_index = set()
    for uid in result_user_id:
        user_id_index.add(uid['user_id'])
    print(list(user_id_index))
    user_id_index = list(user_id_index)

    # 사용자 행동 데이터
    result_user = select_query(
        """select u.id as user_id, product_id, rating, date
           from user u 
           left join (select u.user_id, u.product_id, u.rating, u.date
                      from user_behavior u join product p 
                      on u.product_id = p.id 
                      where p.seller = '""" + str(seller) + """') ub
           on u.id = ub.user_id""",
        cursor)
    result_product = select_query("select * from product", cursor)
    # print(result_user)
    # print(result_product)
    df_ratings = pd.DataFrame(result_user, columns=['user_id', 'product_id', 'rating', 'view_date'])
    df_products = pd.DataFrame(result_product, columns=['id', 'name', 'img', 'price', 'kg'])
    # print(df_ratings)
    print(df_products)
    print("=====================")

    df_user_crop_ratings = df_ratings.pivot(
        index='user_id',
        columns='product_id',
        values='rating'
    ).fillna(0)
    print(df_user_crop_ratings)

    # matrix는 pivot_table 값을 numpy matrix로 만든 것
    matrix = df_user_crop_ratings.to_numpy()

    # user_ratings_mean은 사용자의 평균 평점
    user_ratings_mean = np.mean(matrix, axis=1)

    # R_user_mean : 사용자-농작물에 대해 사용자 평균 평점을 뺀 것.
    matrix_user_mean = matrix - user_ratings_mean.reshape(-1, 1)

    # scipy에서 제공해주는 svd.
    # U 행렬, sigma 행렬, V 전치 행렬을 반환.
    print("=====shape=====")
    print(matrix_user_mean.shape[1])
    U, sigma, Vt = svds(matrix_user_mean, k=matrix_user_mean.shape[1]-1)  # k값 한 번 확인(원래는 12)
    sigma = np.diag(sigma)

    # U, Sigma, Vt의 내적을 수행하면, 다시 원본 행렬로 복원이 된다.
    # 거기에 + 사용자 평균 rating을 적용한다.
    svd_user_predicted_ratings = np.dot(np.dot(U, sigma), Vt) + user_ratings_mean.reshape(-1, 1)

    df_svd_preds = pd.DataFrame(svd_user_predicted_ratings, columns=df_user_crop_ratings.columns)
    df_svd_preds.head()

    return df_svd_preds, df_products, df_ratings, user_id_index

def recommend_products(df_svd_preds, user_id, ori_crops_df, ori_ratings_df, user_id_index, num_recommendations=5):
    # 현재는 index로 적용이 되어있으므로 user_id - 1을 해야함.
    # user 가 없을 경우 예외처리
    if(user_id not in user_id_index):
        return {}, {}
    else :
        user_row_number = user_id_index.index(user_id)
    print(user_row_number)
    print("=====================")
    # 최종적으로 만든 pred_df에서 사용자 index에 따라 영화 데이터 정렬 -> 영화 평점이 높은 순으로 정렬 됌
    sorted_user_predictions = df_svd_preds.iloc[user_row_number].sort_values(ascending=False)

    # 원본 평점 데이터에서 user id에 해당하는 데이터를 뽑아낸다.
    user_data = ori_ratings_df[ori_ratings_df.user_id == user_id]

    # 위에서 뽑은 user_data와 원본 영화 데이터를 합친다.
    user_history = user_data.merge(ori_crops_df, left_on='product_id', right_on='id').sort_values(['rating'], ascending=False)
    # print(user_history)
    # print(ori_crops_df)
    # 원본 영화 데이터에서 사용자가 본 영화 데이터를 제외한 데이터를 추출
    recommendations = ori_crops_df[~ori_crops_df['id'].isin(user_history['id'])]
    # print(sorted_user_predictions)
    # 사용자의 영화 평점이 높은 순으로 정렬된 데이터와 위 recommendations을 합친다.
    recommendations = recommendations.merge(pd.DataFrame(sorted_user_predictions).reset_index(), left_on='id', right_on='product_id')
    # 컬럼 이름 바꾸고 정렬해서 return
    recommendations = recommendations.rename(columns={user_row_number: 'Predictions'}).sort_values('Predictions',
                                                                                                   ascending=False).iloc[:num_recommendations, :]
    # print(recommendations[['id', 'name', 'img']].values.tolist())
    # print(recommendations)

    # flask로 넘겨주려면 list형태론 불가능.
    product_dict = {}
    product_dict["info"] = recommendations[['id', 'name', 'img', 'price', 'kg']].values.tolist()
    print(product_dict)
    return user_history, product_dict


def main():
    # print("========================================메인 상품 추천=========================================================")
    # df_svd_preds, df_products, df_ratings, user_id_index = get_user_behavior_data()
    # already_rated, predictions = recommend_products(df_svd_preds, 10, df_products, df_ratings, user_id_index, 100)
    # print("======사용자가 기존에 점수 매긴 농작물======")
    # print(already_rated.head(10))
    # print("\n")
    # print("===============추천 농작물================")
    # print(predictions)
    # print("=============================================================================================================")

    print("========================================스토어 상품 추천=========================================================")
    if(get_user_behavior_data_by_seller("average10@") is not 0):
        df_svd_preds, df_products, df_ratings, user_id_index = get_user_behavior_data_by_seller("average10@")
        predictions = recommend_products(df_svd_preds, 10, df_products, df_ratings, user_id_index, 100)
        print("======사용자가 기존에 점수 매긴 농작물======")
        # print(already_rated.head())
        print("\n")
        print("===============추천 농작물================")
        print(predictions)
        print("=============================================================================================================")
    else :
        print("예외처리");


if __name__ == "__main__":
    main()
