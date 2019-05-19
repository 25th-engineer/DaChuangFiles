import urllib3
import requests
import certifi
from lxml import etree

#  忽略警告：InsecureRequestWarning: Unverified HTTPS request is being made. Adding certificate verification is strongly advised.
requests.packages.urllib3.disable_warnings()
# 一个PoolManager实例来生成请求, 由该实例对象处理与线程池的连接以及线程安全的所有细节
#证书形式
http = urllib3.PoolManager(
  cert_reqs='CERT_REQUIRED',
  ca_certs=certifi.where())
# 通过request()方法创建一个请求：
r = http.request('GET', 'https://baike.baidu.com/item/%E7%BD%91%E7%BB%9C%E7%88%AC%E8%99%AB/5162711?fromtitle=%E7%88%AC%E8%99%AB&fromid=22046949&fr=aladdin/')
# 获得html源码,utf-8解码
html_page = r.data
#LXML获取元素
html = etree.HTML(html_page.lower().decode('utf-8'))

#源码
#print(html_page.decode('utf-8'))
#获取链接
#print(html.xpath('//a/@href'))
#获取链接文本
#print(html.xpath('//a/text()'))
#获取META
#print(html.xpath('//meta/@name'))
#获取content
#print(html.xpath('//meta/@text'))

f_url=open('/home/yadalio/Hadoop/data/url.txt','a')
f_text=open('/home/yadalio/Hadoop/data/text.txt','a')

url=html.xpath('//a')
for i in url:
    if len(i.xpath('text()'))==1 and len(i.xpath('@href'))==1:
        if len(i.xpath('text()')[0].replace('\t','').replace('\n','').replace(' ','').replace('\xa0',''))>=1:
            f_url.write(i.xpath('text()')[0]+'\t'+i.xpath('@href')[0]+'\n')
f_url.close()

text_=html.xpath('//div/text()')
for i in range(len(text_)):
    text_[i]=text_[i].replace(' ','').replace('\n','').replace('\t','').replace('\r','').replace('\xa0|\xa0','')
    if len(text_[i])>=4:
        f_text.write(text_[i]+'\n')
f_text.close()
