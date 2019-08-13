import org.ansj.recognition.impl.StopRecognition
import org.ansj.splitWord.analysis.ToAnalysis
import org.apache.spark.{SparkConf, SparkContext}

object wordSegment {
	def main(args: Array[String]): Unit = {
		val conf = new SparkConf().setAppName("wordSegment").setMaster("local[*]")
		val sc = new SparkContext(conf)
		val filter = new StopRecognition()

		filter.insertStopNatures("w") // 过滤掉标点
		filter.insertStopNatures("m") // 过滤掉m词性
		filter.insertStopNatures("null") // 过滤null词性
		filter.insertStopNatures("<br />") // 过滤<br />词性
		filter.insertStopNatures(":")
		filter.insertStopNatures("'")
		filter.insertStopWords("的")
		/*
		*/
		

		//filter.insertStopNatures("w") //过滤掉标点

		val rdd=sc.textFile("file:///home/hadoop001/hadoop/data/cnblog_computer_version_onlycontent_dropduplicate2_2_x/000000_0")
				.map { x =>
					var str = if (x.length > 0)
						ToAnalysis.parse(x).recognition(filter).toStringWithOutNature(" ")
					str.toString
				}.top(100).foreach(println)
	}
}