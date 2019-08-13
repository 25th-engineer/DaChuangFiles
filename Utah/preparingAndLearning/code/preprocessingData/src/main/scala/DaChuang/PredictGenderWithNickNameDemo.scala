package DaChuang


import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.classification.LogisticRegressionModel
import org.apache.spark.ml.feature.{CountVectorizer, CountVectorizerModel, StopWordsRemover}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

object PredictGenderWithNickNameDemo extends Serializable {
	val logger: Logger = Logger.getLogger(this.getClass)

	case class OrigData(userId: String, gender: Double, ageBucket: Int, nickName: String, nickWordList: List[String],
	                    appList: List[String], playList: List[Long])

	def main(args: Array[String]): Unit = {
		Logger.getLogger("org").setLevel(Level.WARN)

		val conf: SparkConf = new SparkConf().setAppName("Predict Gender With LR").setMaster("local")
		val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()
		val sc: SparkContext = spark.sparkContext

		val filePath = "data/data_with_gender.dat"
		val stopwordPath: String = "dictionaries/stopwords.txt"

		//数据DataFrame
		import spark.implicits._
		val data = sc.textFile(filePath).flatMap{line =>
			val tokens: Array[String] = line.split("\\|")

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




	}

}
