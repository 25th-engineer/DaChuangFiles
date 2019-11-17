import urllib3
from bs4 import BeautifulSoup as bs
import certifi
import time

import discord
TOKEN = 'XXXXXXXXXXXXXXXXXXXXXX'
tool = True
bo = 0
bi = []
pee = []
poe = []
client = discord.Client()


url = ("https://www.d20pfsrd.com/feats/combat-feats/")

urllib3.disable_warnings()

http = urllib3.PoolManager()
bool = True
i = 0
link = []
Feats = []
temp = ""
next = False
x = 0



response = http.request('GET', url)
soup = bs(response.data, "lxml")



link = soup.findChildren('tr')


link = soup.findAll('td' or 'a') 
link3 = link
templ = []
FeatNames = []
Categ = []
PreReq = []
Benefits = []
Sources = []

for ss in (soup.find_all('strong')):
    ss.decompose()

templ = soup.findChildren('td')

z = 0
i = -1
while(bool == True):# second attempt at seperating clust of data into their assigned feats(list) - seperates items by child order then seperates feats iterating every 5 children
    i+=1
    
    if(len(templ) == (i+x*5+z+1)):
        bool = False
    if(bool):
        if (templ[x*5+i+z].text == ""):
            z+=1
            i = 0
        if(i == 0):
            FeatNames.append(templ[x*5+i+z].text)
        if(i == 1):
            Categ.append(templ[x*5+i+z].text)
        if(i == 2):
            PreReq.append(templ[x*5+i+z].text)
        if(i == 3):
            Benefits.append(templ[x*5+i+z].text)
        if(i == 4 ):
            Feats.append((FeatNames[x], Categ[x], PreReq[x], Benefits[x]))
        
            x+=1
            i = -1


                
            


def clear(num):
    while(num > 0):
        print (" ")
        num -= 1


x=-1
i = -1

def Search(terms,categ,message):
        templ = []
        templ1 = []
        templ2 = []
        templ3 = []
        templ4 = []
        x=-1
        for pss in FeatNames:
            x+=1

            for tt in terms:
                if str(tt) in pss:
                    if x not in templ1:
                        templ1.append(x)
                    if x not in templ:
                        templ.append(x)
        x =-1
        for pss in PreReq:
            x+=1
            for tt in terms:
                if str(tt) in pss:
                    if x not in templ2:
                        templ2.append(x)
                    if x not in templ:
                        templ.append(x)
        x =-1
        for pss in Benefits:
            x+=1
            for tt in terms:
                if str(tt) in pss:
                    if x not in templ3:
                        templ3.append(x)
                    if x not in templ:
                        templ.append(x)
        x =-1
        for pss in Categ:
            x+=1
            for tt in terms:
                if str(tt) in pss:
                    if x not in templ4:
                        templ4.append(x)
                    if x not in templ:
                        templ.append(x)

        b = 0
        templ.sort()
        if (categ == "Names"):
            return (templ1)
        if (categ == "Prerequisites"):
            return (templ2)
        if (categ == "Benefits"):
            return (templ3)
        if (categ == "Categories"):
            return(templ4)
        if (categ == "All"):
            return (templ)

        for b in templ:
            clear(5)
            print("Feat Index: "+str(b))
            print(" ")
            print("Feat Name: "+FeatNames[b])
            print(" ")
            print("Category/Type: "+Categ[b])
            print(" ")
            print("Prerequisites: "+PreReq[b])
            print(" ")
            print("Benefits: "+Benefits[b])

            



pop = []            
teets = []
templl = []
def INPUT_(rii,categ,message):
    
    clear(30)
    if (rii is None):
        re = int(input("How Many Terms Would You Like to Search?: "))
        clear(2)
        for i in range(0,re):
            if (rii is None):
                ri = (input("Search Term "+str(i+1)+": "))
            print(" ")
            templl.append(ri)
    else:
        templl = rii
    pop = (Search(templl,categ,message))
    if pop is not None:
        for o in pop:
            print(o)
            teets.append(o)
        return (teets)
        pop = []
        clear(10)
    


@client.event
async def on_message(message):
    tool = False
    cate = ""
    b = []
    poe = []
    peo = []
    y = -1
    yy = 0
    if ((message.content.startswith('!s')) or (tool)):
        print("Starting")
        await client.send_message(message.channel,'Category You Wish to Search Under("Names", "Categories", "Prerequisites", "Benefits", or "All"): ')
        cate = await client.wait_for_message(author=message.author)
        if (tool == False):
            tool = True
        if(tool):
            await client.send_message(message.channel,'Enter Term: ')
            print("Waiting on Response")
            msg = await client.wait_for_message(author=message.author)
            
            pee.append(msg.content)
            poe = INPUT_(pee,cate.content,message)
            if( poe is not None):
                poe.sort
                for b in poe:

                    if((y == 4) or (y == 9) or (y == 14) or (y == 19) or (y == 24) or (y == 29) or (y == 34) or (y == 39) or (y == 44)):
                        
                        msg = await client.send_message(message.channel,'To Move to the Next 5 Results, Type "!next"')
                        msg = await client.wait_for_message(author=message.author)
                        if (msg.content.startswith("!next")):
                            embed = discord.Embed(title="Feat Index: ", description= (str(b)), color=0x00ff00)
                            embed.add_field(name= "Feat Name: ", value= (FeatNames[b]), inline=False)
                            embed.add_field(name= "Category/Type: ", value= (Categ[b]), inline=False)
                            embed.add_field(name= "Prerequisites: ", value= (PreReq[b]), inline=False)
                            embed.add_field(name= "Benefit: ", value= (Benefits[b]), inline=False)
                            embed.add_field(name="Search Result: "+str(yy+1)+"/"+(str(len(poe))),value ="--------------------------------------------------------------------------------------------------", inline=False)
                            await client.send_message(message.channel, embed=embed)
                            embed.Empty
                        else:
                            message.content = ""
                    else:
                            embed = discord.Embed(title="Feat Index: ", description= (str(b)), color=0x00ff00)
                            embed.add_field(name= "Feat Name: ", value= (FeatNames[b]), inline=False)
                            embed.add_field(name= "Category/Type: ", value= (Categ[b]), inline=False)
                            embed.add_field(name= "Prerequisites: ", value= (PreReq[b]), inline=False)
                            embed.add_field(name= "Benefit: ", value= (Benefits[b]), inline=False)
                            embed.add_field(name="Search Result: "+str(yy+1)+"/"+(str(len(poe))),value ="--------------------------------------------------------------------------------------------------", inline=False)
                            await client.send_message(message.channel, embed=embed)
                            embed.Empty
                    yy +=1
                    y +=1
            
client.run(TOKEN)

def main() :
    keyword = '计算机视觉'



if __name__ == '__main__':
    main()