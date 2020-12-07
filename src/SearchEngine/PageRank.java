/**
 * PageRank.java: Ranks the web-pages according to the number of frequency of the word in the document corresponding to that word
 */

package SearchEngine;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PageRank {
	private static String wordRegex = "[[ ]*|[,]*|[)]*|[(]*|[\"]*|[;]*|[-]*|[:]*|[']*|[’]*|[\\.]*|[:]*|[/]*|[!]*|[?]*|[+]*]+";
	
	public static int  WordFrequency(String URL, String WORD) throws IOException{
		
		//Creates a map element to store the rank of a page corresponding to 'WORD'
		Map<String, WordElement> count_map = new HashMap<String, WordElement>();

		//Connect to the page to fetch the content
		Document webpage = Jsoup.connect(URL).get();

		//Get the page content from the body section of web-page
		String pageContent = webpage.body().text();

		//Create a reader to read each line from the text
		BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(pageContent.getBytes(StandardCharsets.UTF_8))));
		String line;
		while ((line = br.readLine()) != null) {
			
			String words [] = line.split(wordRegex);
			
			for (String word : words) {
				
				if ("".equals(word)) {
					continue;
				}
				
				WordElement e = count_map.get(word);
				
				if (word.equalsIgnoreCase(WORD)) {
					
					if (e == null) {
						
						e = new WordElement();
						e.word = word;
						e.count = 0;
						count_map.put(word, e);
						
					}
					
					e.count++;
				}
			}
		}
		
		br.close();
		
		//Sort all the words
		SortedSet<WordElement> sortedWords = new TreeSet<WordElement>(count_map.values());
		int i = 0;
		int MAX_WORDS = 1000;

		LinkedList <String> uselessWords = new LinkedList <>();
		try {
			
			BufferedReader wordReader = new BufferedReader(new FileReader("stopwords.txt"));
			String w;
			while ((w = wordReader.readLine()) != null) {
				uselessWords.add(w);
			}
			wordReader.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("No such file");
		}

		for (WordElement word : sortedWords) {
			if (i >= MAX_WORDS) { 
				break;
			}

			if (uselessWords.contains(word.word)) {
				i++;
				MAX_WORDS++;
			}
			else {
				i++;
				return word.count;
			}

		}
		return 0;

	}
	
	//Comparable class to search for words in the HashMAp
	public static class WordElement implements Comparable<WordElement> {
		String word;
		int count;

		@Override
		public int hashCode() {
			return word.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			return word.equals(((WordElement) obj).word);
		}

		@Override
		public int compareTo(WordElement b) {
			return b.count - count;
		}
	}
	
}
