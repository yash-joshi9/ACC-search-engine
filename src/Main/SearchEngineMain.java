package Main;

import java.io.IOException;

import SearchEngine.SearchEngine;
import SearchEngine.PageRank;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class SearchEngineMain {

	private static final String URL_PAGE = "https://www.w3.org/";
	
	public static void main(String [] args) throws IOException {
		
		 SearchEngine SE = new SearchEngine();
		HashSet<String> links = SE.createTrie(URL_PAGE);
		
		
		boolean flag = true;
		Scanner sc = new Scanner(System.in);
		String search_word;
		
		while(flag) {
			System.out.println("Enter a word you want to search: ");
			
			search_word = sc.next();
			if(!search_word.equals(null)) {
				String [] index = new String[1];
				index[0] = search_word;
				String[] webpages = SE.search(index);
				
				try {
					if (webpages == null) {
						System.out.println("Please enter a keyword or query!");
					}
					else {
						Map<String, Integer> unsortedLinks = null;
						unsortedLinks = new HashMap<>();
						
						//Stores all the links in HashMap
						for (String url : webpages) {
							
							unsortedLinks.put(url, PageRank.WordFrequency(url, search_word));
						}
						//to sort the links according to occurence of the word
						LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
						
				        unsortedLinks.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
				        
				        
				         System.out.println("---------------------------------");
				         System.out.println("Page Rank | \t | Your Search Result");
				         System.out.println("---------------------------------");
				         
				        for (Map.Entry<String, Integer> entry : reverseSortedMap.entrySet()) {
				            System.out.println(entry.getValue()+"\t \t"+entry.getKey());
				        }
				        
				        System.out.println("Do you want to continue?(y/n):");
				        String op = sc.next();
				        
				        if(op.equals("n")) {
				        	flag = false;
				        	System.out.println("Bye");
				        	System.exit(1);
				        }
					}
				
				}
				catch(Exception e) {
					System.out.println(e.getMessage());
				}
		}
		else {
			System.out.println("\n\nEnter a valid search word!!!\n\n");
			continue;
		}
		
	}
}
	
}
