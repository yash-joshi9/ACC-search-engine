package HtmlParser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;	


public class HtmlParser {

//		html to text parser
		public static void htmlToText(File f, String fileName) throws IOException {
			
//			parsing html to text using jsoup
			Document doc = Jsoup.parse(f, "utf-8");
			
			String docString = doc.text();
			
			String path = "TextFiles\\"+fileName+".txt";

			PrintWriter pw = new PrintWriter(path);
			pw.println(docString);
			
			System.out.println("file created");
			pw.close();
		}
}
