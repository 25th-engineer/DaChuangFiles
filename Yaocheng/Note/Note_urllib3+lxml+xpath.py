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
r = http.request('GET', 'https://blog.csdn.net/qq_33817630/article/details/83347814/')
# 获得html源码,utf-8解码
html_page = r.data
#LXML获取元素
html = etree.HTML(html_page.lower().decode('utf-8'))

#源码
print(html_page.decode('utf-8'))
#获取链接
print(html.xpath('//a/@href'))
#获取链接文本
print(html.xpath('//a/text()'))
