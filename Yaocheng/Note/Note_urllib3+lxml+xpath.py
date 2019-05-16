import urllib3
import requests
from lxml import etree

#  忽略警告：InsecureRequestWarning: Unverified HTTPS request is being made. Adding certificate verification is strongly advised.
requests.packages.urllib3.disable_warnings()
# 一个PoolManager实例来生成请求, 由该实例对象处理与线程池的连接以及线程安全的所有细节
http = urllib3.PoolManager()
# 通过request()方法创建一个请求：
r = http.request('GET', 'http://www.baidu.com/')
# 获得html源码,utf-8解码
html_page = r.data
html = etree.HTML(html_page.lower().decode('utf-8'))
#print(html_page.decode('utf-8'))

#print chain
print(html.xpath('//a/@href'))
#print text
print(html.xpath('//a/text()'))
