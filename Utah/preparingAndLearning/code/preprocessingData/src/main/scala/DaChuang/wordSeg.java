package DaChuang;


import org.apdplat.word.WordSegmenter;
import org.apdplat.word.dictionary.DictionaryFactory;
import org.apdplat.word.segmentation.Word;
import org.apdplat.word.util.WordConfTools;


import java.io.*;
import java.util.List;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Iterator;


public class wordSeg {

	//private static final String fileName1 = "/home/hadoop001/hadoop/data/splitTables/splitData/cnblog_computer_version_onlycontent_dropduplicate2_2_x_index/1.txt";
	public  static  String foldName1 = "cnblog_machine_learning_onlycontent_dropduplicate2_2_x_index";
	public static String fileName1 = "/home/hadoop001/hadoop/data/splitData/" + foldName1 + "/";
	public  static  final int tot = 891;


	public static StringBuffer readFile( BufferedReader x, StringBuffer y ) {
		//StringBuffer x;
		//读取文件
		BufferedReader br;
		StringBuffer sb;
		br = x;
		sb = y;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName1), "UTF-8")); //这里可以控制编码
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

	public static void main( String[] args )
	{
		//WordConfTools.set("dic.path", "classpath:/home/hadoop001/hadoop/data/dic.txt");
		//DictionaryFactory.reload();//更改词典路径之后，重新加载词典
		int i;
		for( i = 1; i <= tot; i ++ ) {
			String tmp = "_" + String.valueOf(i) + "/" + String.valueOf(i) + ".txt";

			System.out.println("i = " + i);
			System.out.println("String tmp = " + tmp);

			String completePath = fileName1 + tmp;
			System.out.println("completePath = " + completePath);

			fileName1 = completePath;
			System.out.println("fileName = " + fileName1);


			BufferedReader br = null;
			StringBuffer sb = null;
			String title2 = "自然语言处理";
			sb = readFile(br, sb);

			fileName1 = fileName1.replace(tmp, "");
			System.out.println("fileName = " + fileName1);

			List<Word> words1 = WordSegmenter.segWithStopWords(sb.toString());

			try {

				String fold = "_" + String.valueOf(i) + "/";
				String outPath1 = fileName1 + fold;
				System.out.println("first, outPath = " + outPath1);

				FileWriter fw = new FileWriter(outPath1 + String.valueOf(i) + ".out1_1", true);

				outPath1.replaceAll(outPath1, "");
				System.out.println("second, outPath = " + outPath1);

				//FileWriter fw = new FileWriter(outPath1, true);
				BufferedWriter bw = new BufferedWriter(fw);

				System.out.println("******************************");
				Iterator<Word> iterator1 = words1.listIterator();
				int count = 0;
				for (; iterator1.hasNext(); ) {
					Word wd = iterator1.next();
					String temp = (wd + " ");
					System.out.print(temp);
					bw.write(temp);
					count++;
					if (count % 10 == 0) {
						System.out.println();
						bw.write("\n");
					}

				}
				bw.close();
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
