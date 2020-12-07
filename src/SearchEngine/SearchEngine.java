package SearchEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
<<<<<<< HEAD
import java.util.List;
import WebCrawler.Trie;
import WebCrawler.WebCrawlerMain;


public class SearchEngine {
	
	private static final String URL_PAGE = "https://www.w3.org/";
	private static Trie<ArrayList<Integer>> trie;
	private final String wordRegex = "\\w+";
	private static String [] webPages;
	private static HashSet<String> allLinks;
	private static String [] webPagesArray;
	
	public HashSet<String> createTrie(String urlName) throws IOException {
		HashSetMain hash =  new HashSetMain();
		WebCrawlerMain wc = new WebCrawlerMain();
		 
		
		this.trie = new Trie<ArrayList<Integer>>();
		
		HashSet<String> uselessWords = hash.savepages("stopwords.txt");
		
		allLinks = wc.getPageLinks(urlName, 1);
		
		
		
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
			txt = WebCrawlerMain.htmlToText(str);
			if(txt == null) {
				continue;
			}
			txt = txt.toLowerCase();
			splitWords = txt.split(wordRegex);
			
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
		
		System.out.println(trie.size);
		return allLinks;
		
	}
	
	public String[] search (String[] index) {
		
		int[] votes = new int[allLinks.size()];
		List<String> links = new ArrayList<String>(allLinks);
		
		
		
		ArrayList<Integer> tmp = null;
		for (int i = 0; i < index.length; ++i) {
			tmp = this.trie.search_word(index[i].toLowerCase());
			if (tmp != null) {
				for (int k = 0; k < tmp.size(); k++) {
					votes[tmp.get(k)]++;
				}
			} else {
				System.out.println("The word <" + index[i] + "> is not in any file!" );
				
				return null;
			}
		}
		
		/*answers stores the indexes of the webPages*/ 
		ArrayList<String> webPages = new ArrayList<String>();
		for (int p = 0; p < votes.length; ++p) {
			if (votes[p] == index.length) {
				webPages.add(links.get(p));
			}
		}
		return webPages.toArray(new String[0]);
	}
	
	
	public static void main(String args[]) throws IOException {
		SearchEngine sc = new SearchEngine();
		sc.createTrie(URL_PAGE);
=======

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
>>>>>>> branch 'main' of https://github.com/yash-joshi9/ACC-search-engine.git
	}
}
