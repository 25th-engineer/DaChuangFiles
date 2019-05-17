#########################################################################
# File Name: csdn_script.sh
# Author: Xef Utah刁肥宅
# mail: u25th_engineer@163.com
# Created Time: 2019年05月14日 星期二 23时02分13秒
#########################################################################
#!/bin/bash
ps -ef | grep python | cut -c 9-15| xargs kill -s 9
python2 /home/u25th_engineer/document/code/Python/testCsdnVisit.py & python /home/u25th_engineer/document/code/Python/accessCsdn/accessCsdn.py & python /home/u25th_engineer/document/code/Python/Analog-access/main.py & python /home/u25th_engineer/document/code/Python/csdn-spider/csdn_spider.py & echo "Job Done!"
