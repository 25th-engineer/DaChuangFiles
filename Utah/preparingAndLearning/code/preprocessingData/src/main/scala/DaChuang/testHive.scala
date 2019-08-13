package DaChuang


import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.row_number

object testHive {
	def main(args: Array[String]): Unit = {
		val conf = new SparkConf()
		conf.setAppName("TestHive").setMaster("local[2]")
		val spark = SparkSession.builder().enableHiveSupport().config(conf).getOrCreate()


		spark.sql("use dachuangppreprocessingdata")
		val tableName = "csdn_computer_version_onlycontent_dropduplicate2_2_x"
		//spark.sql("select * from cnblog_computer_version_onlycontent").show(false)
		val rdd = spark.sql("select * from " + tableName)

		//spark.sql("show databases").collect().foreach(println)

		//rdd.write.saveAsTable(tableName)


		val w = Window.orderBy("content2")
		val result = rdd.withColumn("index", row_number().over(w))
		result.write.saveAsTable(tableName + "_index")
/**/
		rdd.write.format("json").save("file:///home/hadoop001/hadoop/data/regexOutput" + tableName + "_fuck")



		//rdd.show( 500, false)
	}

}
