package DaChuang

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.regexp_extract

import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.row_number

object processRegex {
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


		val pattern = "图\\d.[\\u4e00-\\u9fa5].*"
		//val pattern = "^(\\s*)\\n" //matching the empty line

		/*
		public static Column regexp_replace(Column e,
                    String pattern,
                    String replacement)
		* */

		val data = filterPrecessDF.select(regexp_extract(filterPrecessDF("content"), pattern, 0).alias("content1"))

		//data.show(1000,false)



		data.write.format("json").save("file:///home/hadoop001/hadoop/data/regexOutput_cnblog_computer_version_onlycontent_dropduplicate1")

	}


}
