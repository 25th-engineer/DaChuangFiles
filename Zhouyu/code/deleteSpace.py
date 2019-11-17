import os
        #初始路径
dirPath = "F:\\20Data\csdn_natural_language_onlycontent_dropduplicate2_2_x_index\_"

        #动态路径
for i in range(1,21) :

        #读取文件
    del_doc1 = dirPath + str(i) + '\\' + str(i) + '.dic'
    if (os.path.exists(del_doc1)) :
        f = open(del_doc1,'r',encoding="utf-8")
        s = f.read().split('\n')

        #删除空行
        while '' in s:
            s.remove('')

        #准备写入新文件
        del_doc2 = dirPath + str(i) + '\\' + str(i) + 'l.dic'
        w = open(del_doc2, 'w', encoding="utf-8")
        for item in range(len(s)):
            if item != len(s)-1 :
                w.writelines(s[item]+'\n')
            else :
                w.writelines(s[item])
        f.close()
        w.close()

        #删除文件
        os.remove(del_doc1)

        #文件重命名
        os.rename(del_doc2,del_doc1)

    else :
        print("文件不存在")