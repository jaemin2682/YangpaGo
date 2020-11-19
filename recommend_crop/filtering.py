from scipy.sparse.linalg import svds

import pandas as pd
import numpy as np
import warnings
warnings.filterwarnings("ignore")

df_ratings  = pd.read_csv('./data/crop_ratings.csv')
df_products  = pd.read_csv('./data/crops.csv', engine='python')
print(df_ratings)

df_user_crop_ratings = df_ratings.pivot(
    index='userId',
    columns='cropId',
    values='rating'
).fillna(0)
# print(df_user_crop_ratings.head())

# matrix는 pivot_table 값을 numpy matrix로 만든 것
# matrix = df_user_crop_ratings.as_matrix() -> error
matrix = df_user_crop_ratings.to_numpy()

# user_ratings_mean은 사용자의 평균 평점
user_ratings_mean = np.mean(matrix, axis = 1)

# R_user_mean : 사용자-농작물에 대해 사용자 평균 평점을 뺀 것.
matrix_user_mean = matrix - user_ratings_mean.reshape(-1, 1)

# print(matrix)
# print(matrix.shape)
# print(user_ratings_mean.shape)
# print(matrix_user_mean.shape)
# print(pd.DataFrame(matrix_user_mean, columns = df_user_crop_ratings.columns).head())

# scipy에서 제공해주는 svd.
# U 행렬, sigma 행렬, V 전치 행렬을 반환.

U, sigma, Vt = svds(matrix_user_mean, k = 12)
# print(U.shape)
# print(sigma.shape)
# print(Vt.shape)

sigma = np.diag(sigma)
# print(sigma.shape)
# print(sigma[0])
# print(sigma[1])


# U, Sigma, Vt의 내적을 수행하면, 다시 원본 행렬로 복원이 된다.
# 거기에 + 사용자 평균 rating을 적용한다.
svd_user_predicted_ratings = np.dot(np.dot(U, sigma), Vt) + user_ratings_mean.reshape(-1, 1)

df_svd_preds = pd.DataFrame(svd_user_predicted_ratings, columns = df_user_crop_ratings.columns)
df_svd_preds.head()
# print(df_svd_preds.shape)


def recommend_movies(df_svd_preds, user_id, ori_crops_df, ori_ratings_df, num_recommendations=5):
    # 현재는 index로 적용이 되어있으므로 user_id - 1을 해야함.
    user_row_number = user_id - 1

    # 최종적으로 만든 pred_df에서 사용자 index에 따라 영화 데이터 정렬 -> 영화 평점이 높은 순으로 정렬 됌
    sorted_user_predictions = df_svd_preds.iloc[user_row_number].sort_values(ascending=False)

    # 원본 평점 데이터에서 user id에 해당하는 데이터를 뽑아낸다.
    user_data = ori_ratings_df[ori_ratings_df.userId == user_id]

    # 위에서 뽑은 user_data와 원본 영화 데이터를 합친다.
    user_history = user_data.merge(ori_crops_df, on='cropId').sort_values(['rating'], ascending=False)

    # 원본 영화 데이터에서 사용자가 본 영화 데이터를 제외한 데이터를 추출
    recommendations = ori_crops_df[~ori_crops_df['cropId'].isin(user_history['cropId'])]
    # 사용자의 영화 평점이 높은 순으로 정렬된 데이터와 위 recommendations을 합친다.
    recommendations = recommendations.merge(pd.DataFrame(sorted_user_predictions).reset_index(), on='cropId')
    # 컬럼 이름 바꾸고 정렬해서 return
    recommendations = recommendations.rename(columns={user_row_number: 'Predictions'}).sort_values('Predictions',                                                                                       ascending=False).iloc[:num_recommendations, :]

    return user_history, recommendations

already_rated, predictions = recommend_movies(df_svd_preds, 1, df_products, df_ratings, 5)
print("======사용자가 기존에 점수 매긴 농작물======")
print(already_rated.head(10))
print("\n")
print("===============추천 농작물================")
print(predictions)







