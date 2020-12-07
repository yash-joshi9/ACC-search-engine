package WebCrawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class WebCrawlerMain {
	
	int MAX_DEPTH = 5;
	
	private static HashSet<String> links;

    public WebCrawlerMain() {
        links = new HashSet<String>();
    }
    
    public static String htmlToText(String URL) throws IOException {
		  Document document = Jsoup.connect(URL).get();
		  String text = document.body().text();
		  return text;
    }
    
    
    public Elements getLinksOnPage(String URL) throws IOException {
    	  Document document = Jsoup.connect(URL).get();
          Elements linksOnPage = document.select("a[href]");
          
          return linksOnPage;
    }
    
    
    public static String savePages(String URL) throws IOException {
	   String path = "src//TextFiles//"+System.nanoTime()+".txt";
//       
       PrintWriter pw = new PrintWriter(path);
       pw.println(htmlToText(URL));                	
       
       pw.close();
       
       return htmlToText(URL);
    }
    
    public HashSet<String> getPageLinks(String URL, int depth) {
        if ((!links.contains(URL) && (depth < MAX_DEPTH))) {
            System.out.println(">> Depth: " + depth + " [" + URL + "]");
            try {
                links.add(URL);

              
                Elements linksOnPage = getLinksOnPage(URL);

                depth++;
                
                for (Element page : linksOnPage) {
                    getPageLinks(page.attr("abs:href"), depth);
                }
                
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
        
        return links;
    }
    
	
	public static void main(String[] args) {
		
		 new WebCrawlerMain().getPageLinks("https://www.w3.org/", 3);
		 System.out.println(links);
    }
}
