package WebCrawler;

import java.util.HashMap;

public class Node<t> {
	char k;
	t value;
	HashMap<Character, Node<t>> children;
	
	public Node() {
		this.children = new HashMap<Character, Node<t>>();
	}
	
	public Node(char key) {
		this.k = key;
		this.children = new HashMap<Character, Node<t>>();
	}
}
