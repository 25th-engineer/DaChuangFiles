import os, sys

#原始地址
dirPath = "F:\\20Data\cnblog_computer_version_onlycontent_dropduplicate2_2_x_index\_"

#动态地址
for i in range(1,21) :
    del_doc1 = dirPath + str(i) + '\\' + str(i)+ '.out'
    del_doc2 = dirPath + str(i) + '\\' + str(i)+ '.txt'

    #判断文件是否存在
    if(os.path.exists(del_doc1)) and (os.path.exists(del_doc2)):
        os.remove(del_doc1)
        os.remove(del_doc2)
    else:
        print ("要删除的文件不存在！")