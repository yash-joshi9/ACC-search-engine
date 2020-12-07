package WebCrawler;

import java.util.HashMap;

public class Trie<t> {
	private Node<t> root;
	public int size;
	
	public Trie() {
		this.root = new Node<t>();
		this.size = 0;
	}
	//insert key and values in trie
	public void insert(String s, t value) {
		HashMap<Character, Node<t>> children = this.root.children;
		Node<t> node = null;
		
		for (int i = 0; i < s.length(); ++i) {
			char c = s.charAt(i);
			
			if (children.containsKey(c)) {
				node = children.get(c);
			} else {
				node = new Node<t>(c);
				children.put(c,node);
			}
			
			if (i==s.length()-1) // is the end of the word
				node.value = value;
			
			children = node.children;
		}
		this.size += 1;
	}
	
	 
	public t search_word(String s) {
		
		HashMap<Character, Node<t>> children = this.root.children;
		Node<t> node = null;
		t ans = null;
		
		for (int i = 0; i < s.length(); ++i) {
			char c = s.charAt(i);
			
			if (children.containsKey(c)) {
				node = children.get(c);
			} else {
				return null;
			}
			if (i == s.length()-1) {
				ans = node.value;
			}
			children = node.children;
		}
		return ans; // page index gets returned if exists else null
	}

}
