package DaChuang

import java.io.InputStream
import java.util

import org.ansj.domain.Result
import org.ansj.recognition.impl.StopRecognition
import org.ansj.splitWord.analysis.ToAnalysis
import org.ansj.util.MyStaticValue
import org.apache.spark.{SparkConf, SparkContext}
import org.nlpcn.commons.lang.tire.domain.{Forest, Value}
import org.nlpcn.commons.lang.tire.library.Library

class ChineseSegmentation extends Serializable {

	@transient private val sparkConf: SparkConf = new SparkConf().setAppName("chinese segment")
	@transient private val sparkContext: SparkContext = SparkContext.getOrCreate(sparkConf)

	private val stopLibRecog = new StopLibraryRecognit
	private val stopLib: util.ArrayList[String] = stopLibRecog.stopLibraryFromHDFS(sparkContext)
	private val selfStopRecognition: StopRecognition = stopLibRecog.stopRecognitionFilter(stopLib)

	private val dicUserLibrary = new DicUserLibrar
	@transient private val aListDicLibrary: util.ArrayList[Value] = dicUserLibrary.getUserLibraryList(sparkContext)
	@transient private val dirLibraryForest: Forest = Library.makeForest(aListDicLibrary)

	/**中文分词和模式识别*/
	def cNSeg(comment : String) : String = {

		val result: Result = ToAnalysis.parse(comment,dirLibraryForest).recognition(selfStopRecognition)
		result.toStringWithOutNature(" ")
	}
}

/**停用词典识别：
  * 格式： 词语  停用词类型[可以为空]  使用制表符Tab进行分割
  * 如：
  * #
  * v nature
  * .*了 regex
  *
  * */

class StopLibraryRecognit extends Serializable {

	def stopRecognitionFilter(arrayList: util.ArrayList[String]): StopRecognition ={

		MyStaticValue.isQuantifierRecognition = true //数字和量词合并

		val stopRecognition = new StopRecognition

		//识别评论中的介词（p）、叹词（e）、连词（c）、代词（r）、助词（u）、字符串（x）、拟声词（o）
		stopRecognition.insertStopNatures("p", "e", "c", "r", "u", "x", "o")

		stopRecognition.insertStopNatures("w")  //剔除标点符号

		//剔除以中文数字开头的，以一个字或者两个字为删除单位，超过三个的都不删除
		stopRecognition.insertStopRegexes("^一.{0,2}","^二.{0,2}","^三.{0,2}","^四.{0,2}","^五.{0,2}",
			"^六.{0,2}","^七.{0,2}","^八.{0,2}","^九.{0,2}","^十.{0,2}")

		stopRecognition.insertStopNatures("null") //剔除空

		stopRecognition.insertStopRegexes(".{0,1}")  //剔除只有一个汉字的

		stopRecognition.insertStopRegexes("^[a-zA-Z]{1,}")  //把分词只为英文字母的剔除掉

		stopRecognition.insertStopWords(arrayList)  //添加停用词

		stopRecognition.insertStopRegexes("^[0-9]+") //把分词只为数字的剔除

		stopRecognition.insertStopRegexes("[^a-zA-Z0-9\u4e00-\\u9fa5]+")  //把不是汉字、英文、数字的剔除

		stopRecognition
	}


	def stopLibraryFromHDFS(sparkContext: SparkContext): util.ArrayList[String] ={
		/** 获取stop.dic文件中的数据 方法二：
		  * 在集群上运行的话，需要把stop的数据放在hdfs上，这样集群中所有的节点都能访问到停用词典的数据 */
		val stopLib: Array[String] = sparkContext.textFile("hdfs://zysdmaster000:8020/data/library/stop.dic").collect()
		val arrayList: util.ArrayList[String] = new util.ArrayList[String]()
		for (i<- 0 until stopLib.length)arrayList.add(stopLib(i))

		arrayList

	}

}



/**用户自定义词典：
  * 格式：词语 词性  词频
  * 词语、词性和词频用制表符分开（Tab）
  *
  * */
class DicUserLibrar extends Serializable {

	def getUserLibraryList(sparkContext: SparkContext): util.ArrayList[Value] = {
		/** 获取userLibrary.dic文件中的数据 方法二：
		  * 在集群上运行的话，需要把userLibrary的数据放在hdfs上，这样集群中所有的节点都能访问到user library的数据 */
		val va: Array[String] = sparkContext.textFile("hdfs://zysdmaster000:8020/data/library/userLibrary.dic").collect()
		val arrayList: util.ArrayList[Value] = new util.ArrayList[Value]()
		for (i <- 0 until va.length)arrayList.add(new Value(va(i)))
		arrayList
	}

	def getUserLibraryForest: Forest = {

		/** 获取userLibrary.dic文件中的数据，此方法不适用在集群上运行 方法一：
		  * 如果在本地运行的话，可以把userLibrary.dic文件方法src目录（sources格式）下，使用映射方法获取，
		  * “/library/userLibrary.dic”和“/userLibrary.dic”两种方式均能获取userLibrary.dic中的数据 */
		val stream: InputStream = DicUserLibrar.this.getClass.getResourceAsStream("/library/userLibrary.dic")
		println(stream)
		/** userLibrary.dic文件放在src目录（sources格式）下 */
		val forestLibrary: Forest = Library.makeForest(DicUserLibrar.this.getClass.getResourceAsStream("/userLibrary.dic"))
		forestLibrary
	}
}