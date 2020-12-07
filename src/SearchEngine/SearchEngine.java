package SearchEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import WebCrawler.Trie;
import WebCrawler.WebCrawlerMain;

public class SearchEngine {
	
	
	private Trie<ArrayList<Integer>> trie;
	private final String splitstringsregex = "[[ ]*|[,]*|[)]*|[(]*|[\"]*|[;]*|[-]*|[:]*|[']*|[’]*|[\\.]*|[:]*|[/]*|[!]*|[?]*|[+]*]+";
	private String [] webPages;
	
	
	
	public SearchEngine(String urlName) throws IOException {
		HashSetMain hash =  new HashSetMain();
		WebCrawlerMain wc = new WebCrawlerMain();
		
		this.trie = new Trie<ArrayList<Integer>>();
		
		HashSet<String> uselessWords = hash.savepages("stopwords.txt");
		
		HashSet<String> allLinks = wc.getPageLinks(urlName, 1);
		
		
		
		HashSet<String> temp = null;
		String txt;
		String word;
		String[] splitWords;
		
		Iterator<String> linkIterator = null;
		Iterator<String> wordIterator = null;
		
		linkIterator = allLinks.iterator();
		
		
		int i = 0;
		while(linkIterator.hasNext()) {
			String str = linkIterator.next();
			txt = WebCrawlerMain.htmlToText(str).toLowerCase();
			splitWords = txt.split(splitstringsregex);
			
			temp = new HashSet<String>(Arrays.asList(splitWords));
			temp.remove(uselessWords);
			
			
			
			wordIterator = temp.iterator();
			
			while(wordIterator.hasNext()) {
				word = (String) wordIterator.next();
				ArrayList<Integer> ar = this.trie.search_word(word);
				
				
				if (ar == null) {
					this.trie.insert(word, new ArrayList<Integer>(Arrays.asList(i)));
				} else {
					ar.add(i);
				}
			}

			i++;
		}
		
		
		for (int index = 0; index < allLinks.size() ; ++index) {
//			try {
//				txt = WebCrawlerMain.htmlToText(this.webPages[index]);
//				txt = txt.toLowerCase();
//				words = txt.split(splitstringsregex);
//				
//				
//				
//				temp = new HashSet<String>(Arrays.asList(words));
//
//				
//				temp.removeAll(uselessWords); 
//				
//				iterator = temp.iterator();
//				while(iterator.hasNext()) {
//					word = (String) iterator.next();
//					
//					
//					ArrayList<Integer> ar = this.trie.search_word(word);
//					if (ar == null) {
//					
//						this.trie.insert(word, new ArrayList<Integer>(Arrays.asList(index)));
//					
//					} else {
//						ar.add(index);
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		} 
		 
	}
	
	
	public static void main(String args[]) throws IOException {
		SearchEngine sc = new SearchEngine("https://www.dream11.com/");
	}
}
