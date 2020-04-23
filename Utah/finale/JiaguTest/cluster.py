import jiagu

'''
docs = [
        "计算机视觉是使用计算机及相关设备对生物视觉的一种模拟。",
        "它的主要任务就是通过对采集的图片或视频进行处理以获得相应场景的三维信息，就像人类和许多其他类生物每天所做的那样。",
        "计算机视觉是一门关于如何运用照相机和计算机来获取我们所需的，被拍摄对象的数据与信息的学问。",
        "形象地说，就是给计算机安装上眼睛（照相机）和大脑（算法），让计算机能够感知环境。我们中国人的成语'眼见为实'和西方人常说的'One picture is worth ten thousand words'表达了视觉对人类的重要性。",
        "不难想象，具有视觉的机器的应用前景能有多么地宽广。",
        "计算机视觉既是工程领域，也是科学领域中的一个富有挑战性重要研究领域。",
        "计算机视觉是一门综合性的学科，它已经吸引了来自各个学科的研究者参加到对它的研究之中。",
        "其中包括计算机科学和工程、信号处理、物理学、应用数学和统计学，神经生理学和认知科学等。",
    ]
'''
docs = [
         "百度深度学习中文情感分析工具Senta试用及在线测试",
        "情感分析是自然语言处理里面一个热门话题",
        "AI Challenger 2018 文本挖掘类竞赛相关解决方案及代码汇总",
        "深度学习实践：从零开始做电影评论文本情感分析",
        "BERT相关论文、文章和代码资源汇总",
        "将不同长度的句子用BERT预训练模型编码，映射到一个固定长度的向量上",
        "自然语言处理工具包spaCy介绍",
        "现在可以快速测试一下spaCy的相关功能，我们以英文数据为例，spaCy目前主要支持英文和德文"
]    
    
cluster = jiagu.text_cluster(docs)	
print(cluster)