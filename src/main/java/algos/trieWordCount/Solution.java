package algos.trieWordCount;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by viveksrivastava on 13/05/16.
 */
public class Solution {
	public static void main(String[] args) throws IOException {


		BufferedReader bi = new BufferedReader(new InputStreamReader(System.in));
		String line;
		Worker target = null;
		int cases = -1;
		int count = 0;
		while ((line = bi.readLine()) != null) {
			if (cases == -1) {
				cases = Integer.parseInt(line);
				target = new Worker(cases);
				new Thread(target).start();
			} else {
				count++;
				target.stringBlockingQueue.add(line);
			}
			if (count == cases)
				break;
		}
	}
}

class Worker implements Runnable {

	public final BlockingQueue<String> stringBlockingQueue;
	private int cases;

	public Worker(int cases) {
		this.cases = cases;
		stringBlockingQueue = new ArrayBlockingQueue<String>(cases);
	}

	@Override
	public void run() {
		StringBuffer stringBuffer = new StringBuffer();
		Trie trie = new Trie();
		long t0 = System.currentTimeMillis();

		for (int i = 0; i < cases; i++) {
			try {
				String line = stringBlockingQueue.take();
				if (line.startsWith("add ")) {
					line = line.replace("add ", "");
					trie.addWord(line);
				} else if (line.startsWith("find ")) {
					line = line.replace("find ", "");
					stringBuffer.append(trie.countBeginning(line)).append("\n");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(stringBuffer.toString());
		System.err.println("t0 = " + (System.currentTimeMillis() - t0));
	}
}


class TrieNode {
	int size = 0;
	private Set<TrieNode> nodes = new HashSet<>();
	private char value;

	public TrieNode(char value) {
		this.value = value;
	}

	public boolean equals(char c) {
		return equals(new TrieNode(c));
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
		return getValue() + "," + size;
	}
}

class Trie {
	private TrieNode rootNode = new TrieNode((char) 0);
	private Map<String, TrieNode> quickLookup = new HashMap<>();

	private TrieNodeLookup getQuickLookup(String word) {
//		System.out.println(quickLookup);
		TrieNodeLookup trieNodeLookup = new TrieNodeLookup();
		if (word.length() > 3) {
			int startIndex = (word.length() / 3) * 3;
			String hashWord = word.substring(0, startIndex);
			TrieNode node = quickLookup.get(hashWord);
			while (node == null && hashWord.length() > 3) {
				startIndex = ((hashWord.length() - 1) / 3) * 3;
				hashWord = word.substring(0, startIndex);
				node = quickLookup.get(hashWord);
			}
			if (node != null) {
				trieNodeLookup.startIndex = hashWord.length();
				trieNodeLookup.trieNode = node;
				return trieNodeLookup;
			}
		}

		trieNodeLookup.startIndex = 0;
		trieNodeLookup.trieNode = rootNode;

		return trieNodeLookup;
	}

	public void addWord(String word) {
		TrieNode trie = rootNode;
		int count = 0;
		StringBuilder stringBuffer = new StringBuilder();
//		TrieNodeLookup quickLookup = getQuickLookup(word);
//		System.out.println("quickLookup.startIndex = " + quickLookup.startIndex + " " + quickLookup.trieNode.toString());
//		TrieNode trie = quickLookup.trieNode;
//		stringBuffer.append(word.substring(0, quickLookup.startIndex));
//		System.out.println("trie = " + trie + " " + stringBuffer);

//		if (word.length() > quickLookup.startIndex) {
//			word = word.substring(quickLookup.startIndex);

		for (char c : word.toCharArray()) {
			stringBuffer.append(c);
			count++;
			TrieNode trieN = trie.getNode(c);
			if (trieN == null) {
				trieN = new TrieNode(c);
				trie.addNode(trieN);
			}
			trieN.size++;
			trie = trieN;
			if (count % 3 == 0) {
				this.quickLookup.put(stringBuffer.toString(), trie);
//				System.out.println(this.quickLookup);
			}
		}
//		}
		TrieNode node = new TrieNode((char) 0);
		node.size++;
		trie.addNode(node);
	}

	public boolean containsWord(String word) {
//		TrieNode trie = rootNode;
		TrieNodeLookup quickLookup = getQuickLookup(word);
		TrieNode trie = quickLookup.trieNode;
		if (word.length() < quickLookup.startIndex) {
			word = word.substring(quickLookup.startIndex);
			for (char c : word.toCharArray()) {
				trie = trie.getNode(c);
				if (trie == null)
					return false;
			}
		}

		trie = trie.getNode((char) 0);

		return trie != null;

	}

	public int countBeginning(String word) {
		TrieNode trie = rootNode;
		int count = 0;
		for (char c : word.toCharArray()) {
			trie = trie.getNode(c);
			if (trie == null)
				return 0;
		}

		return trie.size;

		/*Queue<TrieNode> trieNodes = new ArrayDeque<>();
		trieNodes.add(trie);

		while (!trieNodes.isEmpty()) {
			TrieNode trieNode = trieNodes.poll();
			for (TrieNode node : trieNode.getNodes()) {
				if (node.equals((char) 0))
					count++;
				else
					trieNodes.add(node);
			}
		}

		return count;*/
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

	class TrieNodeLookup {
		TrieNode trieNode;
		int startIndex;
	}
}