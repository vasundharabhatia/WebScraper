import urllib.request
import re
import string
import io

#list of urls to scrape data
urls=["http://colleges.usnews.rankingsandreviews.com/best-colleges/rankings/national-universities/data/page+1","http://colleges.usnews.rankingsandreviews.com/best-colleges/rankings/national-universities/data/page+2","http://colleges.usnews.rankingsandreviews.com/best-colleges/rankings/national-universities/data/page+3","http://colleges.usnews.rankingsandreviews.com/best-colleges/rankings/national-universities/data/page+4","http://colleges.usnews.rankingsandreviews.com/best-colleges/rankings/national-universities/data/page+5","http://colleges.usnews.rankingsandreviews.com/best-colleges/rankings/national-universities/data/page+6","http://colleges.usnews.rankingsandreviews.com/best-colleges/rankings/national-universities/data/page+7","http://colleges.usnews.rankingsandreviews.com/best-colleges/rankings/national-universities/data/page+8","http://colleges.usnews.rankingsandreviews.com/best-colleges/rankings/national-universities/data/page+9","http://colleges.usnews.rankingsandreviews.com/best-colleges/rankings/national-universities/data/page+10","http://colleges.usnews.rankingsandreviews.com/best-colleges/rankings/national-universities/data/page+11"]
#list of universities and tuitions
universities=[]
tuitions=[]
i=0
#regex pattern to get college name and tuition fees from html of each usnews webpage urls
regexuniv='<a class="school-name" href="/best-colleges/.+?">(.+?)</a>'
regextuition='search_tuition_display  ">\s*(.*?)\s*<span'
patterntuition=re.compile(regextuition)
patternuniv=re.compile(regexuniv)
while i<len(urls):
    htmlfile=urllib.request.urlopen(urls[i])
    htmltext=htmlfile.read().decode('iso-8859-1')
    #remove unwanted string from university name
    htmltext2=htmltext.replace('&mdash;â\x80\x8b',' ')
    #add each university and tuition fess to corresponding list
    universities=universities+re.findall(patternuniv,htmltext2)
    tuitions=tuitions+re.findall(patterntuition,htmltext) 
    i+=1

j=0
dict={}
while j<len(universities):
    #create dictionary of universty name: tuition fee
    dict[universities[j]]=tuitions[j]
    j+=1
#write output to output.txt
f = io.open('/Users/vasundharabhatia/Desktop/output.txt', 'w',encoding='utf8')
f.write(str(dict))

print ("done")
