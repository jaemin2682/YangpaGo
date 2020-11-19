import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import linear_kernel
from function import db_con, select_query

# 해당 유저가 본 레시피들의 모든 재료를 총합함.
def user_view_recipe(email):
    # DB 연결
    cursor = db_con()
    # 해당 아이디가 조회한 recipe_id들의 재료를 모두 수집
    result = select_query("select group_concat(content separator ' ') content from recipe_ingredient where recipe_id in (select distinct recipe_id from recipe_view where email = '" + email + "')", cursor)
    # select group_concat(content separator ' ') content from recipe_ingredient where recipe_id in (select distinct recipe_id from recipe_view where email = 'average10@naver.com')
    return result

def user_no_view_recipe(email):
    # DB 연결
    cursor = db_con()
    # 사용자가 보지 않은 레시피에 대해서 recipe id - 재료 String 쌍으로 묶어줌
    result = select_query("select recipe_id num, group_concat(content separator ' ') content from recipe_ingredient where recipe_id not in (select distinct recipe_id from recipe_view where email = '" + email + "') group by recipe_id", cursor)
    # select recipe_id num, group_concat(content separator ' ') content from recipe_ingredient where recipe_id not in (select distinct recipe_id from recipe_view where email = 'average10@naver.com') group by recipe_id
    return result

# 사용자가 봤던 레시피들로 가장 유사한 레시피 번호들을 return하는 함수
def get_recipe_by_user(email):
    user_view = user_view_recipe(email)
    user_no_view = user_no_view_recipe(email)
    user_view_dict = {'num':0, 'content': user_view[0]['content']}
    
    user_no_view.append(user_view_dict)

    # DataFrame화
    df = pd.DataFrame(user_no_view, columns=['num', 'content'])

    #tf-idf 생성
    tfidf = TfidfVectorizer()
    tfidf_matrix = tfidf.fit_transform(df.content)
    # print(tfidf_matrix.shape) # (데이터 개수, 유니크 단어 개수)

    #코사인 유사도 계산
    cosine_sim = linear_kernel(tfidf_matrix, tfidf_matrix)
    # print(cosine_sim)        # 데이터 개수 * 데이터 개수 매트릭스 생성

    #인덱스 테이블 만들기
    indices = pd.Series(df.index, index = df.num).drop_duplicates()
    # print(indices) #이제 해당 recipe number로 코사인 유사도의 인덱스를 추출할 수 있음.

    #0번 인덱스로부터 인덱스 가져오기
    idx = indices[0]

    #모든 레시피에 대해서 해당 레시피와의 유사도를 구하기
    sim_scores = list(enumerate(cosine_sim[idx]))

    #유사도에 따라 레시피를 정렬
    sim_scores = sorted(sim_scores, key=lambda x:x[1], reverse=True)

    #가장 유사한 x개의 레시피를 받아옴. 일단 5개
    sim_scores = sim_scores[1:51]

    #가장 유사한 x개의 레시피의 인덱스를 받아옴
    recipe_indices = [i[0] for i in sim_scores]

    #기존에 읽어들인 데이터에서 해당 인덱스의 값들을 가져온다. 그리고 스코어 열을 추가하여 코사인 유사도를 확인할 수 있게 함
    result_df = df.iloc[recipe_indices].copy()
    result_df['score'] = [i[1] for i in sim_scores]

    #읽어들인 데이터에서 재료 부분은 제거, number와 스코어만 보이게 함
    del result_df['content']

    #가장 유사한 2개의 레시피 number를 리턴
    return result_df


# 레시피 번호로 레시피의 모든 정보를 가져온다. [[번호, 제목, [재료], [순서]], [번호, 제목, [재료], [순서]], ...]
def select_recipe2(recipe_numbers):
    cursor = db_con()
    recipe_info = []
    for num in recipe_numbers:
        result = select_query("select id, name, picture from recipe where id = " + str(num), cursor)
        cur_info = [result[0]['id'], result[0]['name'], result[0]['picture']]   # 레시피 번호, 이름, 사진        
        recipe_info.append(cur_info)
    # flask로 넘겨주려면 list형태론 불가능.
    recipe_dict = {}
    recipe_dict["info"] = recipe_info
    return recipe_dict

def main():
    similar_rank = get_recipe_by_user('average10@naver.com')
    similar_info = select_recipe2(similar_rank['num'])
    print(similar_info)

if __name__ == "__main__":
    main()
