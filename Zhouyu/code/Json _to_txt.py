import json

import glob
import os

def readjson():

    for i in range(1,520) :
        url_1  = 'F:\splitTables\csdn_natural_language_onlycontent_dropduplicate2_2_x_index'+'\_'+ str(i)+ '\\'+'*.json'
        path_1 = glob.glob(url_1)[0]
        with open(path_1, 'r',encoding='utf-8') as f:
            temp =json.load(f)

            # 获得annotations中的内容

            #print(temp['content2'])
            url_2 = 'F:\splitData\csdn_natural_language_onlycontent_dropduplicate2_2_x_index\\_'+str(i)+'\\'
            os.makedirs(url_2)
            #url_2 = 'cnblog_computer_version_onlycontent_dropduplicate2_2_x_index'
            path_2 = url_2+str(i) +'.txt'
            #path_2 = 'json_to_txt.txt'
            file_handle = open(path_2,"w",encoding='utf-8')
            file_handle.write(temp['content2'])
            file_handle.close()


if __name__ == '__main__':
    readjson()
