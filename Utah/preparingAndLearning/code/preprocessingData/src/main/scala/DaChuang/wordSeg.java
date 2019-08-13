package DaChuang;


import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;


import java.io.*;
import java.util.List;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Iterator;


public class wordSeg {

	private static final String fileName1 = "/home/hadoop001/hadoop/data/csdn_computer_version_onlycontent_dropduplicate2_2_x/000000_0";
	public static StringBuffer readFile( BufferedReader x, StringBuffer y ) {
		//StringBuffer x;
		//读取文件
		BufferedReader br = null;
		StringBuffer sb = null;
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

		String filePath = "/home/hadoop001/hadoop/data/csdn_computer_version_onlycontent_dropduplicate2_2_x/000000_0_word_Java.out";

		BufferedReader br = null;
		StringBuffer sb = null;
		sb = readFile(br, sb);

		List<Word> words1 = WordSegmenter.segWithStopWords( sb.toString() );

		try {
			FileWriter fw = new FileWriter(filePath, true);
			BufferedWriter bw = new BufferedWriter(fw);

			System.out.println("******************************");
			Iterator<Word> iterator1 = words1.listIterator();
			int count = 0;
			for(;iterator1.hasNext();) {
				Word wd = iterator1.next();
				String temp = ( wd + " " );
				System.out.print(temp);
				bw.write(temp);
				count ++;
				if( count % 10 == 0 ) {
					System.out.println();
					bw.write("\n");
				}

			}
			bw.close();
			fw.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
