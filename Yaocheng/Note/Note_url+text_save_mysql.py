import pymysql
#change python to utf-8 neccessary!
import sys
import imp
import urllib3
import requests
import certifi
import queue
from lxml import etree
imp.reload(sys)

requests.packages.urllib3.disable_warnings()
http = urllib3.PoolManager(
  cert_reqs='CERT_REQUIRED',
  ca_certs=certifi.where())

conn=pymysql.connect(host='127.0.0.1',user='root',password='123',db="to_hdfs",charset="utf8")
cur=conn.cursor()

url="https://baike.baidu.com/item/%E7%BD%91%E7%BB%9C%E7%88%AC%E8%99%AB/5162711?fromtitle=%E7%88%AC%E8%99%AB&fromid=22046949&fr=aladdin/"
sql="insert into baidu(Url,Text) values(%s,%s)"

count = int(0)
#网页数
max_size = 100
que = queue.Queue()
que.put(url)
#空的暂时列表
data=[]
while count<max_size:
    if que.empty():
       break
    
    print(float(count)/float(max_size),'%')

    r = http.request('GET', que.get())
    
    html_page=r.data
    html = etree.HTML(html_page.lower().decode('utf-8'))

    url_data=''
    text_data=''
#url_ = [node]
    url_=html.xpath('//a')
    for i in url_:
#i.xpath('text()')获取节点文本    i.xpath('@href')获取节点链接
        if len(i.xpath('text()'))>0 and len(i.xpath('@href'))>0 and len(i.xpath('@href')[0])>7:
          if i.xpath('@href')[0][0:6]=="/item/":
            que.put("https://baike.baidu.com"+i.xpath('@href')[0])
            url_data=i.xpath('@href')[0]
#text_=[str]
    text_=html.xpath('//div/text()')
    for i in range(len(text_)):
        text_[i]=text_[i].replace(' ','').replace('\n','').replace('\t','').replace('\r','').replace('\xa0|\xa0','')
        if len(text_[i])>=4:
            text_data=text_data+text_[i]
    data.append((url_data,text_data))
    if count%5 == 0:
        cur.executemany(sql,data)
        data.clear()
    count=count+1

print("done!")
cur.executemany(sql,data)
conn.commit()
cur.close()
conn.close()
print('saved!')
