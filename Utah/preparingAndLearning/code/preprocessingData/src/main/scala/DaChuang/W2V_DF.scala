package DaChuang

import org.apache.spark.SparkConf
import org.apache.spark.ml.feature.{Word2Vec, Word2VecModel}
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * Created by ray on 17/2/9.
  *
  * Use word2Vec Class to turn word to vector in this object.
  * Format of input data is DataFrame.
  */
object W2V_DF {




	/**
	  * build word2Vec model
	  *
	  * @param df every Row includes some words of a sentence/article
	  */
	def buildModel(df: DataFrame, inputCol: String, outputCol: String = "wv", minCount: Int = 0, vectorSize: Int = 100, seed: Long = 1L): Word2VecModel = {
		val word2Vec = new Word2Vec()
				.setInputCol(inputCol)
				.setOutputCol(outputCol)
				.setVectorSize(vectorSize)
				.setMinCount(minCount)
				.setSeed(seed)
		val model = word2Vec.fit(df)
		model
	}

	/**
	  * build word2Vec model ,then return word and vector that represents word
	  *
	  * @param df every Row includes some words of a sentence/article
	  */
	def getWordsVectors(df: DataFrame, inputCol: String, outputCol: String = "wd", minCount: Int = 0, vectorSize: Int = 100, seed: Long = 1L): DataFrame = {
		val model = buildModel(df, inputCol, outputCol, minCount, vectorSize, seed)
		model.getVectors
	}

	/**
	  * in this method, every sentence/article will be turn to a vector
	  *
	  * @param df every Row includes some words of a sentence/article
	  */
	def makeWordsVector(df: DataFrame, inputCol: String, outputCol: String = "wv", minCount: Int = 0, vectorSize: Int = 100, seed: Long = 1L): DataFrame = {
		val model = buildModel(df, inputCol, outputCol, minCount, vectorSize, seed)
		model.transform(df)
	}


	//**********************************************  test area  ********************************************************

	def main(args: Array[String]): Unit = {

		val conf = new SparkConf()
		conf.setAppName("preprocess").setMaster("local[4]")

		val spark = SparkSession.builder().enableHiveSupport().config(conf).getOrCreate()

		spark.sql("use dachuangppreprocessingdata")
		//spark.sql("select * from cnblog_computer_version_onlycontent").show(false)
		val rdd = spark.sql("select * from cnblog_computer_version_onlycontent_dropduplicate2_2_x")   //cnblog_machine_learning_onlycontent

		val rdd1 = spark.sql("select * from pornographiclist")

		val precessDF = rdd.toDF("word")



		//val path = "24235345"
		import org.apache._
		//val precessDF = rdd.map(_.replaceAll("[\\,\\.]", "").split(" "))("UTF-8").map(Tuple1.apply).toDF("word")

/*
		val sql = spark.sqlContext

		val precessDF = sql.createDataFrame(Seq(
			"基于Web Service的智能API接口让我们不需要了解复杂的机器学习以及数学知识就能轻松开发出智能APP.但是,本文将介绍如何完全自己动手去实现一个智能API接口服务,由于涉及到的东西非常多,本文仅以我比较熟悉的“计算机视觉”为例,包含“图像分类（image classification）”和“目标检测（target detect）”,之后如果有机会,我会介绍“视频轨迹跟踪”相关的东西,大概就是图像处理的升级版.在开始正文之前,先解释几个名词.AI的概念近一两年尤其火热,“机器学习”以及“深度学习”的技术介绍到处都是,这里再简单介绍一下我对它们的理解.",
			"又名AI,概念出现得特别早,上世纪五六十年代就有.人工智能大概可以分为两大类,一类“强人工智能”,你可以理解为完全具备跟人类一样的思维和意识的计算机程序；第二类“弱人工智能”,大概就是指计算机能够完成大部分相对较高级的行为,比如前面提到的理解图片含义,理解语言含义以及理解语音等等.我们日常提到的人工智能通常指第二类,常见的有计算机视觉、语音识别、机器翻译、推荐系统、搜索引擎甚至一些智能美图的APP,这些都可以说使用了人工智能技术,因为它们内部都使用了相关机器学习或者深度学习的算法.",
			"这个概念也出现得很早,大概上世界八九十年代（？）.以前的概念中,计算机必须按照人编写的程序去执行任务,对于程序中没有的逻辑,计算机是不可能去做的.机器学习出现后,计算机具备人类“掌握经验”的能力,在通过大量学习/总结规律之后,计算机能够预测它之前并没有见过的事物.",
			"深度学习的概念近几年才出现,你可以理解为它是机器学习的升级.之所以近几年突然流行,是因为一些传统机器学习算法（比如神经网络）要想取得非常好的性能,神经网络必须足够复杂,同时需要大量的学习数据,这时计算能力遇到了瓶颈.而近几年随着硬件性能普遍提升,再加上互联网时代爆炸式的数据存储,训练出足够复杂的模型已经不再是遥不可及.因此,可以将深度学习理解为更复杂的机器学习方式.",
			"这里稍微说一下跟视频有关的处理.对于视频来讲,它跟图片一样,由一张张图片组成,唯一的区别就是它具备时间的维度.我们不仅要检测每帧中的目标,还要判断前后帧之间各个目标之间的联系.然后利用目标物体的位移差来分析物体行为,对于路上车辆来讲,可以分析“异常停车”、“压线”、“逆行掉头”、“车速”、“流量统计”、“抛洒物”等数据.",
			"我认为作为普通程序员, 如果 要学习AI开发,请用一种Top Down的方式,抛开晦涩难懂的数学理论,先找个适合自己的机器学习框架（比如tensorflow或者基于它的keras）,学会如何准备训练数据集（比如本文中如何去标记图片？）,如何训练自己的模型,然后用训练得到的模型去解决一些小问题（比如本文中的图像目标检测）.等自己对机器学习有一种具体的认识之后,经过一段时间的摸索,会自然而然地引导我们去了解底层的数学原理,这个时候再去搞清楚这些原理是什么."
		).map(_.replaceAll("[\\,\\.]", "").split(" ")).map(Tuple1.apply))
				.toDF("word")
*/

		val model = buildModel(precessDF, "word", vectorSize = 20000)
		model.findSynonyms("如果", 100).foreach(println(_))

		val vectors = getWordsVectors(precessDF, "word", vectorSize = 20000)
		vectors.foreach(println(_))

		val sentence = makeWordsVector(precessDF, "word", vectorSize = 200)
		sentence.foreach(println(_))

		// close spark session
		spark.close()
	}
}
