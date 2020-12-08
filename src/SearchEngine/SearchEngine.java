package SearchEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import WebCrawler.Trie;
import WebCrawler.WebCrawlerMain;


public class SearchEngine {
	
	private static final String URL_PAGE = "https://www.w3.org/";
	private static Trie<ArrayList<Integer>> trie;
	private final String wordRegex = "[[ ]*|[,]*|[)]*|[(]*|[\"]*|[;]*|[-]*|[:]*|[']*|[’]*|[\\.]*|[:]*|[/]*|[!]*|[?]*|[+]*]+";
	private static String [] webPages;
	private static HashSet<String> allLinks;
	private static String [] webPagesArray;
	private static LinkedList<String> suggestions = new LinkedList<>(); 
	
	public HashSet<String> createTrie(String urlName) throws IOException {
		HashSetMain hash =  new HashSetMain();
		WebCrawlerMain wc = new WebCrawlerMain();
		 
		
		trie = new Trie<ArrayList<Integer>>();
		
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
			
			if(txt.length() == 0) {
				continue;
			}
			
			txt = txt.toLowerCase();
			splitWords = txt.split(wordRegex);
			
			for(String s: splitWords) {
				suggestions.add(s);
			}
			
			suggestions.removeAll(uselessWords);
			
			temp = new HashSet<String>(Arrays.asList(splitWords));
			temp.remove(uselessWords);
			
			wordIterator = temp.iterator();
			
			while(wordIterator.hasNext()) {
				word = (String) wordIterator.next();
				ArrayList<Integer> ar = trie.search_word(word);
				
				
				if (ar == null) {
					trie.insert(word, new ArrayList<Integer>(Arrays.asList(i)));
				} else {
					ar.add(i);
				}
			}

			i++;
		}
		
		//System.out.println(trie.size);
		return allLinks;
	
	}
	
	public String[] search (String[] index) {
		
		int[] votes = new int[allLinks.size()];
		List<String> links = new ArrayList<String>(allLinks);
		
		
		
		ArrayList<Integer> tmp = null;
		for (int i = 0; i < index.length; ++i) {
			tmp = trie.search_word(index[i].toLowerCase());
			
			
			
			if (tmp != null) {
				for (int k = 0; k < tmp.size(); k++) {
					votes[tmp.get(k)]++;
				}
			} else {
				System.out.println("The word <" + index[i] + "> is not in any file!" );
				
				suggestWords(index[i]);
				
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
	
	public static void suggestWords(String str) {
				
		int dist = 10000;
		String suggest = "No Suggestions!";
		
		for(String temp: suggestions) {
			int d = EditDistance.editDistance(str, temp);
			if(d < dist) {
				suggest = temp;
				dist = d;
			}
		}
		
		System.out.println("Did you mean " + suggest + "?");
		
	}
	
	
	public static void main(String args[]) throws IOException {
		SearchEngine sc = new SearchEngine();
		sc.createTrie(URL_PAGE);
		String[] str = {"W3"};
		sc.search(str);
	}
}
