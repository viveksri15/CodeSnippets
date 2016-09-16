package algos;

import java.util.HashSet;
import java.util.Set;

class TrieNode {
	private Set<TrieNode> nodes = new HashSet<>();
	private char value;

	public TrieNode(char value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		return equals((TrieNode) obj);
	}

	public boolean equals(TrieNode toCompare) {
		return toCompare != null && toCompare.value == this.value;
	}

	public void addNode(TrieNode node) {
		nodes.add(node);
	}

	public boolean contains(TrieNode node) {
		return nodes.contains(node);
	}

	public boolean contains(char c) {
		return nodes.contains(new TrieNode(c));
	}


	public TrieNode getNode(char c) {
		TrieNode nodeToCheck = new TrieNode(c);
		for (TrieNode node : nodes) {
			if (node.equals(nodeToCheck))
				return node;
		}

		return null;
	}

	public Set<TrieNode> getNodes() {
		return nodes;
	}

	public char getValue() {
		return value;
	}

	@Override
	public String toString() {
		return getValue() + "";
	}
}

public class Trie {
	private TrieNode rootNode = new TrieNode((char) 0);

	public static void main(String[] args) {
		Trie trie = new Trie();
		trie.addWord("hi");
		trie.addWord("hill");
		trie.addWord("hello");
		trie.addWord("how");
		trie.addWord("are");
		trie.addWord("you");
		System.out.println(trie.countBeginning("him"));
	}

	public void addWord(String word) {
		TrieNode trie = rootNode;
		for (char c : word.toCharArray()) {
			TrieNode trieN = trie.getNode(c);
			if (trieN == null) {
				trieN = new TrieNode(c);
				trie.addNode(trieN);
			}
			trie = trieN;
		}
		trie.addNode(new TrieNode((char) 0));
	}

	public boolean containsWord(String word) {
		TrieNode trie = rootNode;
		for (char c : word.toCharArray()) {
			trie = trie.getNode(c);
			if (trie == null)
				return false;
		}

		trie = trie.getNode((char) 0);

		return trie != null;

	}

	public int countBeginning(String word) {
		TrieNode trie = rootNode;
		for (char c : word.toCharArray()) {
			trie = trie.getNode(c);
			if (trie == null)
				return 0;
		}

		return trie.getNodes().size();
	}

	public void printTrie(TrieNode rootNode, String path) {
		if (path == null)
			path = "";
		TrieNode endNode = new TrieNode((char) 0);
		for (TrieNode node : rootNode.getNodes()) {
			if (node.equals(endNode))
				System.out.println(path);
			else {
				printTrie(node, path + node.getValue());
			}
		}
	}
}