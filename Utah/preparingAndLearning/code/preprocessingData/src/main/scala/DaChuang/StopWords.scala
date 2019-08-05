package DaChuang

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.regexp_extract

import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.row_number


import org.apache.spark.ml.feature.StopWordsRemover

object StopWords {
	def main(args: Array[String]): Unit = {

		val conf = new SparkConf()
		conf.setAppName("preprocess").setMaster("local[4]")
		val spark = SparkSession.builder().enableHiveSupport().config(conf).getOrCreate()
		spark.sql("use dachuangppreprocessingdata")
		//spark.sql("select * from cnblog_computer_version_onlycontent").show(false)
		val rdd = spark.sql("select * from cnblog_computer_version_onlycontent_dropduplicate")   //cnblog_machine_learning_onlycontent

		val precessDF = rdd.toDF()





	}


}
