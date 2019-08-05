import requests
from urllib.parse import urlencode
from pyquery import PyQuery as pq
from requests.exceptions import ConnectionError
import pymongo

client = pymongo.MongoClient('localhost')
db = client['csdn']
base_url = 'https://so.csdn.net/so/search/s.do?'
headers_so = {
    'Cookie': 'TY_SESSION_ID=4b2f5026-3899-4019-8411-80327061b468; JSESSIONID=75FD81B7536C8BFC86A8E240497C6C95; uuid_tt_dd=10_10359167700-1563616677306-384693; dc_session_id=10_1563616677306.578526; acw_tc=65c86a0c15636166992371834ebd1dd5877c2f2e2087d2acea9c815e05fd15; UN=Zannier; Hm_ct_6bcd52f51e9b3dce32bec4a3997715ac=6525*1*10_10359167700-1563616677306-384693!5744*1*Zannier; __yadk_uid=WHcwJchwWbqeDuqC3RoicgOcyHshPEO0; UserName=Zannier; UserInfo=9e2a76a5111247f18a52acad7c6de1e1; UserToken=9e2a76a5111247f18a52acad7c6de1e1; UserNick=Zannier; AU=E33; BT=1563758114286; p_uid=U000000; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1563792710,1563803619,1563803657,1563844794; dc_tos=pv2mdf; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1563844804',
    'Host': 'so.csdn.net',
    'Upgrade-Insecure-Requests' : '1',
    'User-Agent' : 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36'
}
headers_blog = {
    'Cookie': 'uuid_tt_dd=10_10359167700-1563616677306-384693; dc_session_id=10_1563616677306.578526; UN=Zannier; Hm_ct_6bcd52f51e9b3dce32bec4a3997715ac=6525*1*10_10359167700-1563616677306-384693!5744*1*Zannier; acw_tc=7b39758715636217360748239ea3ff107caead1dc5384dd89fadc4d73f77ac; Hm_ct_e5ef47b9f471504959267fd614d579cd=5744*1*Zannier!6525*1*10_10359167700-1563616677306-384693; __yadk_uid=zEHTGRe4xLVhhGCF2ooJYIzw0igqcY9B; Hm_lvt_e5ef47b9f471504959267fd614d579cd=1563690478,1563697003,1563698195,1563708614; UserName=Zannier; UserInfo=9e2a76a5111247f18a52acad7c6de1e1; UserToken=9e2a76a5111247f18a52acad7c6de1e1; UserNick=Zannier; AU=E33; BT=1563758114286; p_uid=U000000; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1563792710,1563803619,1563803657,1563844794; dc_tos=pv2mg6; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1563844903',
    'Host': 'blog.csdn.net',
    'Upgrade-Insecure-Requests' : '1',
    'User-Agent' : 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36'

}
proxy =[]
count = 0

def get_proxy():
    return requests.get("http://127.0.0.1:5010/get/").content

def Getindex(keyword,page):
    data = {
        'p' : page,
        'q' : keyword,
        't' : 'blog'
    }
    url = base_url + urlencode(data)
    r = requests.get(url , headers = headers_so)
    if r.status_code == 200:
        return  r.text
    else :
        return r.status_code

def GetURL(html)  :
    if html :
        doc = pq(html)
        items = doc('.main-container .con-l .search-list-con .search-list.J_search .author-time .link a').items()
        for item in items :
            href = item.attr('href')
            htm_l = GetHTML(href)
            ParseHTML(htm_l)


def GetHTML(url):
        r = requests.get(url , headers = headers_blog)
        if r.status_code == 200 :
            return r.text
        else:
            return None

def ParseHTML(html) :
    if html :
        doc = pq(html)
        title = doc('#mainBox > main > div.blog-content-box > div > div > div.article-title-box > h1').text()
        date = doc('#mainBox > main > div.blog-content-box > div > div > div.article-info-box > div.article-bar-top > span.time').text()
        author = doc('#mainBox > main > div.blog-content-box > div > div > div.article-info-box > div.article-bar-top > a').text()
        content = doc('.htmledit_views').text()
        if content == '':
            content = doc('#content_views').text()
        results ={
            'title' : title,
            'date' : date,
            'author' : author,
            'content' : content
        }
        SaveToMongo(results)

def SaveToMongo(data):
    if db['natural-language'].update({'title' : data['title']},{'$set' : data} ,True) :
        print("Saved to mongo",data['title'])
    else :
        print("Saved failed",data['title'])

def main():
    for i in range(1,21) :
        html = Getindex('自然语言处理',i)
        GetURL(html)

if __name__ == '__main__':
    main()