package DaChuang

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.regexp_extract

import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.row_number

import org.apache.spark.ml.Transformer
import org.apache.spark.ml.param.shared.{HasInputCol, HasOutputCol}
import org.apache.spark.ml.param.{ParamMap, BooleanParam, Param}
import org.apache.spark.ml.util.Identifiable

object removeDuplicateContent {
	def main(args: Array[String]): Unit = {

		val conf = new SparkConf()
		conf.setAppName("preprocess").setMaster("local[4]")
		val spark = SparkSession.builder().enableHiveSupport().config(conf).getOrCreate()
		spark.sql("use dachuangppreprocessingdata")
		//spark.sql("select * from cnblog_computer_version_onlycontent").show(false)
		val rdd = spark.sql("select * from cnblog_computer_version_onlycontent_dropduplicate")   //cnblog_machine_learning_onlycontent

		val precessDF = rdd.toDF()
		val filterPrecessDF = precessDF.filter(row => !(row.mkString("").isEmpty && row.length>0))

		//filterPrecessDF.show(false)


		//remove duplicate the content
		val preDropDuplicateDF = filterPrecessDF.select("content").distinct()
		val dropDuplicateDF = preDropDuplicateDF.dropDuplicates("content")
		dropDuplicateDF.write.saveAsTable("csdn_natural_language_onlycontent_dropDuplicate")


	}


}
