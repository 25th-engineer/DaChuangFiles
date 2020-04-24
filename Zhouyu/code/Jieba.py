#<<<<<<< HEAD
import jieba.analyse

f = open("F:\Data\\toZhouyu\\toZhouyu\csdn_computer_version_onlycontent_dropduplicate2_2_x\\abc.txt","r",encoding='utf-8')   #打开文件
fr = f.read()                                           #读取文件
jieba.add_word('计算机视觉')
jieba.add_word('机器学习')
jieba.add_word('深度学习')

#keywords = jieba.analyse.extract_tags(fr, topK=30,withWeight=True, allowPOS=())
keywords = jieba.lcut(fr)
content = ' '.join(keywords)
file = open("csdn_cv.txt", "w",encoding='utf-8')
file.write(content)
file.close()
print(content)
#=======
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
#>>>>>>> 74ca953c28dca42746f878d6e7379fbeda4d12d9
