# pip install PyMySQL
import pymysql;
import urllib.request
from bs4 import BeautifulSoup
from urllib.parse import quote # url 한글 인코딩 Error 해결

def productCrawler(product, pages): # product라는 검색어의 page들 안에 있는 글 링크들을 return
    page_list = []
    for page in pages:
        url = 'https://www.10000recipe.com/recipe/list.html?q=' + product + '&order=reco&page=' + str(page)
        req = urllib.request.Request(url)
        sourcecode = urllib.request.urlopen(url).read()
        soup = BeautifulSoup(sourcecode, "html.parser")
    
        res = soup.find('ul', 'common_sp_list_ul')
        for n in res.find_all('li'):
            cur_url = 'https://www.10000recipe.com' + n.find('div', 'common_sp_thumb').find('a')['href']
            page_list.append(cur_url)
    return page_list

def Crawler(url):
    req = urllib.request.Request(url)
    sourcecode = urllib.request.urlopen(url).read()
    soup = BeautifulSoup(sourcecode, "html.parser")

    recipe_num = url.split('/')[-1]
    recipe_title = ""   #레시피 제목
    recipe_picture = "" #레시피 사진
    recipe_source = {}  #레시피 재료
    recipe_step = []    #레시피 순서

    res = soup.find('div', 'view2_summary')  # 레시피 제목 뽑기
    res = res.find('h3')
    recipe_title = res.get_text()

    res = soup.find('div', 'centeredcrop')   # 레시피 사진 뽑기
    res = res.find('img')["src"]
    recipe_picture = res

    res = soup.find('div', 'ready_ingre3')

    # 재료 찾는 for문 가끔 형식에 맞지 않는 레시피들이 있어 try / except 해준다
    try:
        for n in res.find_all('ul'):
            source = []
            for tmp in n.find_all('li'):
                name = tmp.get_text().split('   ')[0]
                amount = tmp.find('span', 'ingre_unit').get_text()
                if amount: # 재료의 양이 있으면 재료-양 묶어줌
                    recipe_source[name] = amount
                else: # 재료의 양이 표시되지 않은 경우, 'None'을 넣어줌
                    recipe_source[name] = 'None'
    except(AttributeError):
        return
    
    #요리 순서 찾는 for문
    res = soup.find('div', 'view_step')
    i = 0
    for n in res.find_all('div', 'view_step_cont'):
        i = i + 1
        if '\r' in n.get_text():  # 레시피에 간혹 제품 소개가 들어있는 경우가 있음. 이 경우 return
            return
        recipe_step.append(n.get_text().replace('\n', ' '))

    # 형식에 맞지 않는 페이지들은 제외해준다.
    if not recipe_num:
        return
    if not recipe_step:
        return
    if not recipe_title:
        return
    if not recipe_picture:
        return
    if not recipe_source:
        return
    
    recipe_all = [recipe_num, recipe_title, recipe_picture, recipe_source, recipe_step]
    return(recipe_all)

def insert(recipe_data, product):
    if not recipe_data:         
        return
    num = recipe_data[0]                                             # 레시피 번호
    name = recipe_data[1].replace('"', ' ').replace("'", " ")        # 레시피 이름
    picture = recipe_data[2]                                         # 레시피 사진
    ingredient_list = []                                             # 레시피 재료 목록
    amount_list = []                                                 # 레시피 재료 수량 목록
    step_list = []                                                   # 레시피 조리과정 목록

    for data in recipe_data[3]:
        post_data = data.replace('"', ' ').replace("'", " ")
        ingredient_list.append(data)
    for data in recipe_data[3]:
        post_data = data.replace('"', ' ').replace("'", " ")
        amount_list.append(recipe_data[3][data])
    for data in recipe_data[4]:
        post_data = data.replace('"', ' ').replace("'", " ")
        step_list.append(post_data)
    
    recipe = pymysql.connect(    # DB con
        user='root', 
        passwd='root4', 
        host='127.0.0.1', 
        db='onionGo', 
        charset='utf8'
    )

    cursor = recipe.cursor(pymysql.cursors.DictCursor)

    #넣다가 중간에 끊기는걸 방지하기 위해 primary를 미리 검사해줌.
    sql = 'select id from recipe where id = ' + str(num)
    cursor.execute(sql)
    result = cursor.fetchall()
    # print(result[0]['id'])
    if result:
        print("중복된 primary key이므로 Insert 안함")
        return

    #recipe 테이블
    sql = 'insert into recipe values(' + str(num) + ', "' + name + '", "' + picture + '", "' + product + '", 0, 0'  + ')'
    print(sql)
    cursor.execute(sql)
    recipe.commit()
    #ingredient 테이블
    for i in range(0, len(ingredient_list)):
        sql = 'insert into recipe_ingredient values(' + str(num) + ', "' + ingredient_list[i] + '", "' + amount_list[i] + '")'
        cursor.execute(sql)
        recipe.commit()
    #step 테이블
    for i in range(0, len(step_list)):
        sql = 'insert into recipe_step values(' + str(num) + ', "' + str(i + 1) + '", "' + step_list[i] + '")'
        cursor.execute(sql)
        recipe.commit()
    
    # 조회 구문의 경우
    # cursor = recipe.cursor(pymysql.cursors.DictCursor)
    # sql = 'select * from test'
    # cursor.execute(sql)
    # result = cursor.fetchall()
    # print(result)
    
    # 삽입 구문
    # sql = 'insert into test values("jieun", 26)'
    # cursor.execute(sql)
    # recipe.commit()

def select():
    recipe = pymysql.connect(    # DB con
        user='root', 
        passwd='force1', 
        host='127.0.0.1', 
        db='recipe', 
        charset='utf8'
    )
    cursor = recipe.cursor(pymysql.cursors.DictCursor)
    sql = "select * from recipe_ingredient"
    cursor.execute(sql)
    result = cursor.fetchall()
    print(result)


def main():
    product = "당근"
    page_range = range(1, 10)

    page_list = productCrawler(quote(product), page_range)
    for url in page_list:
        recipe = Crawler(url)
        insert(recipe, product)
    # select()


if __name__ == "__main__":
    main()
