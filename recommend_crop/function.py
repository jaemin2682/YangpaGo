import pymysql;

def db_con():
    recipe = pymysql.connect(    # DB con
        user='root', 
        passwd='root4', 
        host='j3a404.p.ssafy.io',
        db='onionGo', 
        charset='utf8'
    )
    cursor = recipe.cursor(pymysql.cursors.DictCursor)
    return cursor

def select_query(sql, cursor):
    cursor.execute(sql)
    return cursor.fetchall()
