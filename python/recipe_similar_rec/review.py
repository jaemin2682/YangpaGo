import json
import os
from tensorflow.keras.models import load_model
from konlpy.tag import Okt
import nltk
import numpy as np

def term_frequency(doc):
    if os.path.isfile('train_docs.json'):
        with open('train_docs.json', encoding="utf8") as f:
            train_docs = json.load(f)
        with open('test_docs.json', encoding="utf8") as f:
            test_docs = json.load(f)
    tokens = [t for d in train_docs for t in d[0]]
    text = nltk.Text(tokens, name='NMSC')
    selected_words = [f[0] for f in text.vocab().most_common(10000)]

    return [doc.count(word) for word in selected_words]

def tokenize(doc):
    okt = Okt()
    return ['/'.join(t) for t in okt.pos(doc, norm=True, stem=True)]

def predict_pos_neg(review):
    model = load_model('reviewres.h5')
    token = tokenize(review)
    tf = term_frequency(token)
    data = np.expand_dims(np.asarray(tf).astype('float32'), axis=0)
    score = float(model.predict(data))
    print(review + " 의 긍정 리뷰 점수 : " + str(score*100))
    return score*100

def main():
    predict_pos_neg("맛있어요!!")
    predict_pos_neg("맛없어요")
    predict_pos_neg("진짜 대박 맛집")
    predict_pos_neg('감자 진짜 맛있어요!')
    predict_pos_neg('토마토가 다 썩었네요')
    predict_pos_neg('배송 진짜 느려요')
    predict_pos_neg('진짜 너무 달아요 맛있어요!')

if __name__ == "__main__":
    main()