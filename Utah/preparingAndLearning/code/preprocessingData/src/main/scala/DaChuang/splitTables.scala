package DaChuang

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object splitTables {
	def main(args: Array[String]): Unit = {


		val conf = new SparkConf()
		conf.setAppName("TestHive").setMaster("local[2]")
		val spark = SparkSession.builder().enableHiveSupport().config(conf).getOrCreate()

		val tableName = "csdn_natural_language_onlycontent_dropduplicate2_2_x_index"
		val path = "file:///home/hadoop001/hadoop/data/splitTables/" + tableName + "/"
		spark.sql("use dachuangppreprocessingdata")
		//spark.sql("select * from cnblog_computer_version_onlycontent").show(false)

		//var i = 0;
		val tot = 437
		for(i <- 1 to tot) {
			val rdd = spark.sql(s"select content2 from " + tableName + s" where index = $i ")
			//rdd.repartition(1).write.format("txt").save(path + s"_$i")
			rdd.coalesce(1).write.format("json").save(path + s"_$i")
			//rdd.show()
		}
	}
}
