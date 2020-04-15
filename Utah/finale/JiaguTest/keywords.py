import jiagu

text = '''
数据 是 因为 机器 学习 与 各 领域 的 大 
数据 处理 应用 比如 金融 医疗 联系 十分 紧密 这门 
课 内容 涵盖 基础理论 算法 和 应用 平衡 了 理论 
与 实践 既 覆盖 数学 统计 也 包含 启发式 的 
概念 理解 Tom Mitchell 机器学习 课程 链接 http / / 
www . cs . cmu . edu / ~ tom 
/ 10701 _ sp11 / 介绍 门 课 是 学界 
人士 的 最爱 是 入门课程 之中 较 全面 高阶 的 
一门 课 时为 15 周 远超 大多数 机器学习 慕课/nr 其 
覆盖 的 话题 非常 广 按 先后 次序 包括 代数和 
概率论 机器 学习 的 基础 工具 概率 图 模型 AI 
神经网络 主动 学习 增强 学习 课程 内容 和 练习 十分 
简洁 明白 概念 解释 清楚 到位 谷歌 人工智能 入门 https 
/ / cn . udacity . com / course / 
intro to artificial intelligence cs271 介绍 两位 主讲者 Peter Norvig 
和 Sebastian Thrun 一个 是 谷歌 研究 总监 一个 是 
斯坦福 著名 机器学习 教授 均 是 与 吴恩 达 Yann 
Lecun 同 级别 的 顶级 AI 专家 需要 强调 的 
是 该 课程 倾向于 介绍 AI 的 实际 应用 课程 
'''             

keywords = jiagu.keywords(text, 5) # 关键词
print(keywords)