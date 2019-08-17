import jieba.analyse

for i in range(1,438) :

    #读取文件
    url ='F:\splitData\csdn_natural_language_onlycontent_dropduplicate2_2_x_index\_'
    path_1 = url +str(i)+'\\'+str(i)+'.txt'
    f = open(path_1,"r",encoding='utf-8')
    fr = f.read()

    #向jieba库中添加词语//add words to jieba
    jieba.add_word('计算机视觉')
    jieba.add_word('机器学习')
    jieba.add_word('深度学习')



    #分词//split words
    keywords = jieba.lcut(fr)
    content = ' '.join(keywords)

    #写入数据//write data
    path_2 = url + str(i) + '\\'+str(i)+'.out'
    file = open(path_2, "w",encoding='utf-8')
    file.write(content)
    file.close()

#print(content)