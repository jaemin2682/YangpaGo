from konlpy.tag import Okt
import json
import os
import nltk
import numpy as np
from tensorflow.keras import models
from tensorflow.keras import layers
from tensorflow.keras import optimizers
from tensorflow.keras import losses
from tensorflow.keras import metrics
from tensorflow.keras.models import load_model

def read_data(filename):
    with open(filename, 'r', encoding="utf8") as f:
        data = [line.split('\t') for line in f.read().splitlines()]
        # txt 파일의 헤더(id document label)는 제외하기
        data = data[1:]
    return data
def tokenize(doc):
    # norm은 정규화, stem은 근어로 표시하기를 나타냄
    okt = Okt()
    return ['/'.join(t) for t in okt.pos(doc, norm=True, stem=True)]

def term_frequency(doc, selected_words):
    return [doc.count(word) for word in selected_words]

def predict_pos_neg(review):
    model = load_model('reviewres.h5')
    token = tokenize(review)
    tf = term_frequency(token)
    data = np.expand_dims(np.asarray(tf).astype('float32'), axis=0)
    score = float(model.predict(data))
    if(score > 0.5):
        print("[{}]는 {:.2f}% 확률로 긍정 리뷰이지 않을까 추측해봅니다.^^\n".format(review, score * 100))
    else:
        print("[{}]는 {:.2f}% 확률로 부정 리뷰이지 않을까 추측해봅니다.^^;\n".format(review, (1 - score) * 100))


def main():
    # train_data = read_data('ratings_train.txt')
    # test_data = read_data('ratings_test.txt')
    okt = Okt()

    if os.path.isfile('train_docs.json'):
        with open('train_docs.json', encoding="utf8") as f:
            train_docs = json.load(f)
        with open('test_docs.json', encoding="utf8") as f:
            test_docs = json.load(f)
    # else:
    #     train_docs = [(tokenize(row[1]), row[2]) for row in train_data]
    #     test_docs = [(tokenize(row[1]), row[2]) for row in test_data]
    #     # JSON 파일로 저장
    #     with open('train_docs.json', 'w', encoding="utf-8") as make_file:
    #         json.dump(train_docs, make_file, ensure_ascii=False, indent="\t")
    #     with open('test_docs.json', 'w', encoding="utf-8") as make_file:
    #         json.dump(test_docs, make_file, ensure_ascii=False, indent="\t")

            

    tokens = [t for d in train_docs for t in d[0]]
    text = nltk.Text(tokens, name='NMSC')
    selected_words = [f[0] for f in text.vocab().most_common(10000)]

    train_x = [term_frequency(d, selected_words) for d, _ in train_docs]
    test_x = [term_frequency(d, selected_words) for d, _ in test_docs]
    train_y = [c for _, c in train_docs]
    test_y = [c for _, c in test_docs]

    x_train = np.asarray(train_x).astype('float32')
    x_test = np.asarray(test_x).astype('float32')

    y_train = np.asarray(train_y).astype('float32')
    y_test = np.asarray(test_y).astype('float32')

    model = models.Sequential()
    model.add(layers.Dense(64, activation='relu', input_shape=(10000,)))
    model.add(layers.Dense(64, activation='relu'))
    model.add(layers.Dense(1, activation='sigmoid'))

    model.compile(optimizer=optimizers.RMSprop(lr=0.001),
                loss=losses.binary_crossentropy,
                metrics=[metrics.binary_accuracy])

    model.fit(x_train, y_train, epochs=10, batch_size=512)
    results = model.evaluate(x_test, y_test)

    print("결과다!!!!")
    print(results)

    model.save('reviewres.h5')

    predict_pos_neg("맛있어요!!")
    predict_pos_neg("맛없어요")
    predict_pos_neg("진짜 대박 맛집")
    # predict_pos_neg('감자 진짜 맛있어요!')
    # predict_pos_neg('토마토가 다 썩었네요')
    # predict_pos_neg('배송 진짜 느려요')
    # predict_pos_neg('진짜 너무 달아요 맛있어요!')

if __name__ == "__main__":
    main()