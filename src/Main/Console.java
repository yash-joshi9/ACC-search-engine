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

public class Console {

	private static final String URL_PAGE = "https://www.w3.org/";
	private static final String wordRegex = "[[ ]*|[,]*|[)]*|[(]*|[\"]*|[;]*|[-]*|[:]*|[']*|[’]*|[\\.]*|[:]*|[/]*|[!]*|[?]*|[+]*]+";
	
	public static void main(String [] args) throws IOException {
		
		long s_time, e_time;
		
		SearchEngine SE = new SearchEngine();
		System.out.println("Creating HashTable using Tries...");
		//s_time = System.currentTimeMillis();
		HashSet<String> links = SE.createTrie(URL_PAGE);
		//e_time = System.currentTimeMillis();
		System.out.println("\nHash Table created...");
		
		//System.out.println("\n\nTime taken to create a Hash table: " + (e_time - s_time));
		
		
		boolean flag = true;
		Scanner sc = new Scanner(System.in);
		String search_word;
		
		while(flag) {
			System.out.println("Enter a word you want to search: ");
			
			search_word = sc.next();
			if(!search_word.equals(null)) {
				String [] index = search_word.split(wordRegex);
				
				//s_time = System.currentTimeMillis();
				String[] webpages = SE.search(index);
				//e_time = System.currentTimeMillis();
				
				//System.out.println("\n\nTime taken to Search the word: " + (e_time - s_time));
				
				try {
					if (webpages == null) {
						//Suggest words
					}
					else {
						Map<String, Integer> unsortedLinks = null;
						unsortedLinks = new HashMap<>();
						
						//Stores all the links in HashMap
						for (String url : webpages) {
							
							unsortedLinks.put(url, PageRank.WordFrequency(url, search_word));
						}
						//To sort the links according to frequency of the word in a page
						LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
						
				        unsortedLinks.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
				        
				        System.out.println("\nPrinting Search Results...\n");
				        
				        System.out.println("----------------------------------------------------------------------------------------");
				        System.out.println("Page Rank \t\t Your Search Result");
				        System.out.println("----------------------------------------------------------------------------------------");
				        int cnt = 1;
				        for (Map.Entry<String, Integer> entry : reverseSortedMap.entrySet()) {
				        	if(cnt > 5)
				        		break;
				            System.out.println(entry.getValue() + "         | " + entry.getKey());
				            cnt++;
				        }
				        System.out.println("----------------------------------------------------------------------------------------");
				        
				        System.out.println("\n\nDo you want to continue?(y/n):");
				        
				        while(true) {
				        	String op = sc.next();
				        	if(op.equals("n")) {
					        	flag = false;
					        	System.out.println("Thank You for using our Search Engine!!");
					        	System.exit(1);
					        }
				        	else if(op.equals("y")) {
				        		break;
				        	}
				        	else {
				        		System.out.println("Enter a valid option (y/n): ");
				        	}
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
