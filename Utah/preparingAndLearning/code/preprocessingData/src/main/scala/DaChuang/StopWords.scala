package DaChuang

import DaChuang.PredictGenderWithNickNameDemo.OrigData
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.regexp_extract
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.row_number
import org.apache.spark.ml.feature.{CountVectorizer, CountVectorizerModel, StopWordsRemover, Tokenizer}
import org.apache.spark.mllib.feature.{Word2Vec, Word2VecModel}
import org.apache.spark.ml.Transformer
import org.apache.spark.ml.param.shared.{HasInputCol, HasOutputCol}
import org.apache.spark.ml.param.{BooleanParam, Param, ParamMap}
import org.apache.spark.ml.util.Identifiable

object StopWords {
	def main(args: Array[String]): Unit = {

		val conf = new SparkConf()
		conf.setAppName("preprocess").setMaster("local[4]")
		val spark = SparkSession.builder().enableHiveSupport().config(conf).getOrCreate()
		spark.sql("use dachuangppreprocessingdata")
		//spark.sql("select * from cnblog_computer_version_onlycontent").show(false)
		val rdd = spark.sql("select * from cnblog_computer_version_onlycontent_dropduplicate2_2_x")   //cnblog_machine_learning_onlycontent

		val rdd1 = spark.sql("select * from pornographiclist")

		val precessDF = rdd.toDF()





		//val data = RemoveStopwords(precessDF, spark)
		//data.show()

		/*
		pornographiclist
		reactionarylist
		sensitivelist
		societylist
		terroristiclist
		 */
		//StopWordsRemover(precessDF, rdd1)

		val stopwordPath: String = "file:///home/hadoop001/hadoop/data/funNLP-master/data/sensitive_Words_List/pornographic.txt"

		//数据DataFrame
		import spark.implicits._





		val data = precessDF.flatMap{line =>
			val tokens: Array[String] = line.toString().split("\\|")

			if (tokens.length > 8) {
				val userId: String = tokens(0)
				val gender: Double = tokens(1).toDouble
				val ageBucket: Int = tokens(2).toInt
				val nickName: String = tokens(3)
				val nickWordList: List[String] = nickName.split("").toList
				val appList: List[String] = tokens(4).split(";").toList
				val playList: List[Long] = tokens(5).split(";").filter(_.nonEmpty).map(_.toLong).toList

				Some(OrigData(userId, gender, ageBucket, nickName, nickWordList, appList, playList))
			} else None
		}.toDF()

/*
		//去除停用词
		val stopwords: Array[String] = spark.sparkContext.textFile(stopwordPath).collect()
		val remover = new StopWordsRemover()
				.setInputCol("content2")
				.setOutputCol("content2")
				.setStopWords(stopwords)
		val removedData: DataFrame = remover.transform(data)

		removedData.show()

		//向量化(使用词频为单元)
		val vectorizer = new CountVectorizer()
				.setInputCol("filterNickList")
				.setOutputCol("features")
				.setVocabSize(20000)
		val cvModel: CountVectorizerModel = vectorizer.fit(removedData)
		val vectorizeData: DataFrame = cvModel.transform(removedData)
*/


	}

	def RemoveStopwords (dfin : DataFrame, sc : SparkSession) : DataFrame = {

		val stopwords =	sc.sparkContext.textFile("file:///home/hadoop001/hadoop/data/funNLP-master/data/sensitive_Words_List/pornographic.txt")

		val tokenizer = new Tokenizer().setInputCol("content2".toArray.toString).setOutputCol("content3")
		val wordsData = tokenizer.transform(dfin)

		// remove stop words
		val remover = new StopWordsRemover().setInputCol("content2".toArray.toString).setOutputCol("content3")
		val dfNoStop = remover.transform(wordsData)
		dfNoStop
	}


}
