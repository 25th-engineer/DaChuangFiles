package DaChuang


import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

object testHive {
	def main(args: Array[String]): Unit = {
		val conf = new SparkConf()
		conf.setAppName("TestHive").setMaster("local[2]")
		val spark = SparkSession.builder().enableHiveSupport().config(conf).getOrCreate()

		//spark.sql("show databases").collect().foreach(println)
		spark.sql("select * from emp2").collect().foreach(println)//select * from emp2
	}

}
