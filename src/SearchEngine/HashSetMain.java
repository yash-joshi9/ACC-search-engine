package SearchEngine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class HashSetMain {

	public HashSet<String> savepages(String filename) {
		HashSet<String> hash = new HashSet<String>();
		String line = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			while ((line = br.readLine()) != null) {
				hash.add(line);
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("no such file");
			
			
		}
		catch (IOException e){
			System.out.println("Error occured");
		}
		return hash;
	}
}
