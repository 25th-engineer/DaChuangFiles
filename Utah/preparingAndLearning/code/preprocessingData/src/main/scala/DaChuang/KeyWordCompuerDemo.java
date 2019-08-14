package DaChuang;

import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.ansj.app.extracting.Extracting;
import org.ansj.recognition.impl.StopRecognition;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.apdplat.word.segmentation.Word;


import java.io.*;
import java.lang.System;

import java.util.Collection;
import java.util.List;

public class KeyWordCompuerDemo {

	//private static final String fileName = "/home/hadoop001/hadoop/data/splitData/cnblog_computer_version_onlycontent_dropduplicate2_2_x_index/";

	public  static  String foldName = "cnblog_machine_learning_onlycontent_dropduplicate2_2_x_index";
	public static String fileName = "/home/hadoop001/hadoop/data/splitData/" + foldName + "/";

	public static String contenX = null;

	public String getContenX() {

		return contenX;
	}

	public static StringBuffer readFile( BufferedReader x, StringBuffer y ) {
		//StringBuffer x;
		//读取文件
		BufferedReader br = null;
		StringBuffer sb = null;
		br = x;
		sb = y;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8")); //这里可以控制编码
			sb = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb;
	}

	public static void main(String[] args) {
		//String filePath = "/home/hadoop001/hadoop/data/splitData/cnblog_computer_version_onlycontent_dropduplicate2_2_x_index";
		int i;
		for( i = 1; i <= 772; i ++ ) {
			String tmp = "_" + String.valueOf(i) + "/" + String.valueOf(i) + ".txt";

			System.out.println("i = " + i);
			System.out.println("String tmp = " + tmp);

			String completePath = fileName + tmp;
			System.out.println("completePath = " + completePath);

			fileName = completePath;
			System.out.println("fileName = " + fileName);


			BufferedReader br = null;
			StringBuffer sb = null;
			String title2 = "自然语言处理";
			sb = readFile(br, sb);

			fileName = fileName.replace(tmp, "");
			System.out.println("fileName = " + fileName);

			String content2 = new String(sb); //StringBuffer ==> String
			contenX = content2;


/*
		KeyWordComputer kwc = new KeyWordComputer(100);
		String title = "维基解密否认斯诺登接受委内瑞拉庇护";
		String content = "有俄罗斯国会议员，9号在社交网站推特表示，美国中情局前雇员斯诺登，已经接受委内瑞拉的庇护，不过推文在发布几分钟后随即删除。俄罗斯当局拒绝发表评论，而一直协助斯诺登的维基解密否认他将投靠委内瑞拉。　　俄罗斯国会国际事务委员会主席普什科夫，在个人推特率先披露斯诺登已接受委内瑞拉的庇护建议，令外界以为斯诺登的动向终于有新进展。　　不过推文在几分钟内旋即被删除，普什科夫澄清他是看到俄罗斯国营电视台的新闻才这样说，而电视台已经作出否认，称普什科夫是误解了新闻内容。　　委内瑞拉驻莫斯科大使馆、俄罗斯总统府发言人、以及外交部都拒绝发表评论。而维基解密就否认斯诺登已正式接受委内瑞拉的庇护，说会在适当时间公布有关决定。　　斯诺登相信目前还在莫斯科谢列梅捷沃机场，已滞留两个多星期。他早前向约20个国家提交庇护申请，委内瑞拉、尼加拉瓜和玻利维亚，先后表示答应，不过斯诺登还没作出决定。　　而另一场外交风波，玻利维亚总统莫拉莱斯的专机上星期被欧洲多国以怀疑斯诺登在机上为由拒绝过境事件，涉事国家之一的西班牙突然转口风，外长马加略]号表示愿意就任何误解致歉，但强调当时当局没有关闭领空或不许专机降落。";
		Collection<Keyword> result = kwc.computeArticleTfidf(title2, content2);
		//System.out.println(result);

		System.out.println("******************************");


		StopRecognition filter = new StopRecognition();
		filter.insertStopNatures("w"); // 过滤掉标点

		filter.insertStopNatures("w"); // 过滤掉标点
		filter.insertStopNatures("m");// 过滤掉m词性
		filter.insertStopNatures("null"); // 过滤null词性
		filter.insertStopNatures("<br />"); // 过滤<br />词性
		filter.insertStopNatures(":");
		filter.insertStopNatures(",");
		filter.insertStopNatures("'");
		//    filter.insertStopWords("的");



		System.out.println( NlpAnalysis.parse(sb.toString()).recognition(filter) );
		(ToAnalysis.parse(sb.toString() ) ).toString().split("\n");//toString("\n");

	}
*/


			try {
				String fold = "_" + String.valueOf(i) + "/";
				String outPath = fileName + fold;
				System.out.println("first, outPath = " + outPath);

				FileWriter fw = new FileWriter(outPath + String.valueOf(i) + ".out2", true);

				outPath.replaceAll(outPath, "");
				System.out.println("second, outPath = " + outPath);

				BufferedWriter bw = new BufferedWriter(fw);


				KeyWordComputer kwc = new KeyWordComputer(100);
				String title = "维基解密否认斯诺登接受委内瑞拉庇护";
				String content = "有俄罗斯国会议员，9号在社交网站推特表示，美国中情局前雇员斯诺登，已经接受委内瑞拉的庇护，不过推文在发布几分钟后随即删除。俄罗斯当局拒绝发表评论，而一直协助斯诺登的维基解密否认他将投靠委内瑞拉。　　俄罗斯国会国际事务委员会主席普什科夫，在个人推特率先披露斯诺登已接受委内瑞拉的庇护建议，令外界以为斯诺登的动向终于有新进展。　　不过推文在几分钟内旋即被删除，普什科夫澄清他是看到俄罗斯国营电视台的新闻才这样说，而电视台已经作出否认，称普什科夫是误解了新闻内容。　　委内瑞拉驻莫斯科大使馆、俄罗斯总统府发言人、以及外交部都拒绝发表评论。而维基解密就否认斯诺登已正式接受委内瑞拉的庇护，说会在适当时间公布有关决定。　　斯诺登相信目前还在莫斯科谢列梅捷沃机场，已滞留两个多星期。他早前向约20个国家提交庇护申请，委内瑞拉、尼加拉瓜和玻利维亚，先后表示答应，不过斯诺登还没作出决定。　　而另一场外交风波，玻利维亚总统莫拉莱斯的专机上星期被欧洲多国以怀疑斯诺登在机上为由拒绝过境事件，涉事国家之一的西班牙突然转口风，外长马加略]号表示愿意就任何误解致歉，但强调当时当局没有关闭领空或不许专机降落。";
				Collection<Keyword> result = kwc.computeArticleTfidf(title2, content2);
				//System.out.println(result);

				System.out.println("******************************");


				StopRecognition filter = new StopRecognition();

				filter.insertStopNatures("w"); // 过滤掉标点
				filter.insertStopNatures("m");// 过滤掉m词性
				filter.insertStopNatures("null"); // 过滤null词性
				filter.insertStopNatures("<br />"); // 过滤<br />词性
				filter.insertStopNatures(":");
				filter.insertStopNatures("'");
				//filter.insertStopWords("的");


				System.out.println(NlpAnalysis.parse(sb.toString()).recognition(filter));
				//List<ToAnalysis> words1 = (ToAnalysis.parse(sb.toString())).recognition(filter);//toString("\n");
				bw.write((NlpAnalysis.parse(sb.toString())).toString());
				//bw.close();
				fw.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
