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