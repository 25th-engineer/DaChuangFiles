package DaChuang

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.row_number

object preprocess {
	def main(args: Array[String]): Unit = {

		val conf = new SparkConf()
		conf.setAppName("preprocess").setMaster("local[4]")
		val spark = SparkSession.builder().enableHiveSupport().config(conf).getOrCreate()
		spark.sql("use dachuangppreprocessingdata")
		//spark.sql("select * from cnblog_computer_version_onlycontent").show(false)
		val rdd = spark.sql("select * from cnblog_computer_version_onlycontent")
		//rdd.show(false)

		/*
		//added the index column
		val w = Window.orderBy("content")
		val result = rdd.withColumn("index", row_number().over(w))
		result.write.saveAsTable("csdn_natural_language_onlycontent1")
		*/

		/*
		import spark.implicits._

		val precessDF1 = rdd.as[need]
		val test = rdd.filter(_.length>0).collect()
		println( "hahah: " + test.mkString)
		precessDF1.map( line => line.content ).filter(row => !(row.mkString("").isEmpty && row.length>0)).show(false)
		*/

		val precessDF = rdd.toDF()
		val filterPrecessDF = precessDF.filter(row => !(row.mkString("").isEmpty && row.length>0))
		filterPrecessDF.show(false)




	}
	case class need(content: String)

	def stringLength(phone: String): Boolean = {
		if (phone.length == 0 || phone.isEmpty == true){
			false
		} else {
			true
		}
	}
}
