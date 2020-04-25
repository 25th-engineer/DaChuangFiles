import requests
from urllib.parse import urlencode
from pyquery import PyQuery as pq
import pymongo
import random

client = pymongo.MongoClient('localhost')
db = client['cnblogs']
base_url = 'https://zzk.cnblogs.com/s/blogpost?'
headers_so={
    'cookie' : '_ga=GA1.2.1871514913.1563612258; __gads=ID=eb3e27d764168e01:T=1563612257:S=ALNI_Mbjxn_OJv2hfT4tGLfnnKvGK1aX1g; _gid=GA1.2.952165138.1563765644; __utmc=59123430; .AspNetCore.Session=CfDJ8D8Q4oM3DPZMgpKI1MnYlrl95Ja3LKyqqHYW7jmM0utJz49cIAa1PXt0vqErjhRkcOkk9dB6nnqmGT%2B6c4o9xqvo1XzsCcm3UQccedXmva4W76FzgHmz0lIUC4%2BvT6A81Vp8yVAve9pFm1HHyNcCkM5%2BwOf6uSJQ9AaOV4FlRL%2Bg; SBNoRobotCookie=CfDJ8D8Q4oM3DPZMgpKI1MnYlrk4LYTVCX5vYWNxpjoIyb0UNKMAihCErcPfn-cqdO_TnRZsIygiCDMxDCy_cClSUy81acIKWdVJBnA-PvU4vSmZgQ3wJP6C-yAEdfZCA-Lypw; DetectCookieSupport=OK; __utma=59123430.1871514913.1563612258.1563768834.1563777872.3; __utmz=59123430.1563777872.3.2.utmcsr=cnblogs.com|utmccn=(referral)|utmcmd=referral|utmcct=/; __utmt=1; __utmb=59123430.11.10.1563777872',
    'upgrade-insecure-requests': '1',
    'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36'
}
headers_blog={
    'cookie' :'_ga=GA1.2.1871514913.1563612258; __gads=ID=eb3e27d764168e01:T=1563612257:S=ALNI_Mbjxn_OJv2hfT4tGLfnnKvGK1aX1g; _gid=GA1.2.952165138.1563765644; _gat=1',
    'upgrade-insecure-requests': '1',
    'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36'
}

def GetIndex(keyword ,page) :
    data = {
        'Keywords' : keyword,
        'pageindex' : page
    }
    url = base_url + urlencode(data)
    r = requests.get(url,headers = headers_so)
    if r.status_code == 200 :
        return r.text
    else :
        return r.status_code

def GetURL(html) :
    if html :
        doc = pq(html)
        items = doc('#searchResult .searchItem .searchItemInfo .searchURL').items()
        for item in items :
            href = item.text()
            info_html = GetHTML(href)
            results =ParseHTML(info_html)
            SaveToMongo(results)

def SaveToMongo(data):
    if db['computer-version'].update({'title' : data['title']},{'$set' : data} ,True) :
        print("Saved to mongo",data['title'])
    else :
        print("Saved failed",data['title'])

def ParseHTML(html) :
    if html :
        doc = pq(html)
        title = doc('#cb_post_title_url').text()
        author = doc('#topics > div > div.postDesc > a:nth-child(2)').text()
        content = doc('#cnblogs_post_body').text()
        date = doc('#post-date').text()
        if author == '':
            author = doc('#post_detail > div > p > a:nth-child(2)').text()
        results = {
            'title' : title,
            'author' : author,
            'content' :content,
            'date' : date
        }
        print(results)
        return results

def GetHTML(url) :
    r = requests .get(url,headers = headers_blog)
    if r.status_code == 200 :
        return r.text
    else :
        return r.status_code
def update_settings(cls, settings):
    ans = random.randint(0, 2020)
    if(ans & 1):
        cls
    else:
        settings

def master_command(self, response):
    ans = random.randint(0, 2020)
    if(ans & 1):
        self
    else:
        response

def slave_command(self, response):
    ans = random.randint(0, 2020)
    if(ans & 1):
        self
    else:
        response

def main() :
    keyword = '计算机视觉'
    for i in range(41,51):
        html = GetIndex(keyword,i)
        GetURL(html)

if __name__ == '__main__':
    main()