package DaChuang

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.regexp_extract
import org.apache.spark.sql.functions.regexp_replace

import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.row_number

object processRegex {
	def main(args: Array[String]): Unit = {

		val conf = new SparkConf()
		conf.setAppName("preprocess").setMaster("local[4]")
		val spark = SparkSession.builder().enableHiveSupport().config(conf).getOrCreate()
		spark.sql("use dachuangppreprocessingdata")
		//spark.sql("select * from cnblog_computer_version_onlycontent").show(false)

		val tableName = "cnblog_computer_version_onlycontent_dropduplicate2_2"

		val rdd = spark.sql("select * from " + tableName  )

		val precessDF = rdd.toDF()
		val filterPrecessDF = precessDF.filter(row => !(row.mkString("").isEmpty && row.length>0))



		//filterPrecessDF.show(false)


		val pattern = "图\\d{1,9}" + " " +"[\u4e00-\u9fa5].*"
		val pattern1 = "图\\d{1,9}" + " " + "*"
		val pattern2 = "图\\d{1,9}" + " ." + "*"
		val pattern3 = "\\n{1,9}"
						// "图\\d{1,3}" + " " + "*"
						//"图\\d{1,3}" + " " +"[\u4e00-\u9fa5].*"
						//"图\\d{1,3}" + " ." + "*"

		//val data = filterPrecessDF.select(regexp_extract(filterPrecessDF("content2_2"), pattern2, 0).alias("content2"))
		val filterData = filterPrecessDF.select(regexp_replace(filterPrecessDF("content2_2"), pattern, "").alias("content2")) //replace again

		val data1 = filterData.select(regexp_replace(filterData("content2"), pattern1, "").alias("content2"))

		val data2 = data1.select(regexp_replace(data1("content2"), pattern2, "").alias("content2"))


		val data3 = data2.select(regexp_replace(data2("content2"), pattern3, "").alias("content2"))
		data3.write.saveAsTable(tableName + "_X")

		data3.write.format("json").save("file:///home/hadoop001/hadoop/data/regexOutput" + tableName + "_X")
		//filterData.write.saveAsTable(tableName + "_X")
		/**/




/*
		val pattern1 = "\\n{2,9}" //matching the empty line
						//^(\s*)\n
						//^[\\s&&[^\\n]*\\n$]*
						//^(\s*)\n
		val data = filterPrecessDF.select(regexp_extract(filterPrecessDF("content2"), pattern1, 0).alias("content2_2"))
		//data.show(1000,false)
		val filterData = filterPrecessDF.select(regexp_replace(filterPrecessDF("content2"), pattern1, "\n").alias("content2_2"))
		data.write.format("json").save("file:///home/hadoop001/hadoop/data/regexOutput" + tableName + "_666")
		filterData.write.saveAsTable( tableName + "_2")
		//filterData.write.saveAsTable("cnblog_computer_version_onlycontent_dropduplicate2_2_X")
*/


		//table1  --> duplicate
		//table2  --> remove pattern
		//table2_1  --> remove pattern2
		//table2_2  --> remove empty lines

		//filterData.show(100,false)

		spark.close()
	}


}
