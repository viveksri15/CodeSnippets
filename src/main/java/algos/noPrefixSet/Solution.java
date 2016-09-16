package algos.noPrefixSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
16:42
Framework of the problem
1. Input and Validations
2. Output and checked exceptions if any
3. Core algorithm strategy
4. algos.A datastructure it uses internally
5. Small Test Case
6. Assumptions
7. Limitations: like on size of data, in-memory operations

*/

/*
    Assumptions: letters from a-j
    Limitations: data should fit in memory w.r.t length of string, number of strings
*/

//Conceptual level
/*
    Core algorithm:
    algos.Trie DS
        trie will be n-ary tree, with strings terminated by a special node

        this DS has an issue of memory fragmentation

        ---
        //at a certian memory cost, I can also use an Array at every level
        ---

    Fast fail algo where in lets say the string is aaaabbaba

    if I find aaa string already present in trie, return false;
*/

//Specifications
interface Trie {
    boolean addWithPrefixCheck(String s);
}

//Implementation

class NAryTrie implements Trie {

    private class Node {
        private Map<Character, Node> characterNodeMap = new HashMap<>();
        private boolean isStringTerminated = false;
        final Character character;

        public Node(Character character) {
            this.character = character;
        }

        public void setStringTerminated(boolean isStringTerminated) {
            this.isStringTerminated = isStringTerminated;
        }

        public boolean getStringTerminated() {
            return isStringTerminated;
        }

        public void addNode(Node node) {
            characterNodeMap.put(node.character, node);
        }

        public Node getNode(Character c) {
            return characterNodeMap.get(c);
        }
    }

    private Node rootNode = new Node(new Character('0'));

    public boolean addWithPrefixCheck(String s) {
        //Validate Input
        if (!Validations.validateInput(s))
            return false;

        Node node = rootNode;
        boolean created = false;
        for (char c : s.toCharArray()) {
            Character character = new Character(c);
            Node childNode = node.getNode(character);
            if (childNode == null) {
                childNode = new Node(character);
                node.addNode(childNode);
                created = true;
            } else if (childNode.getStringTerminated())
                return false;

            node = childNode;
        }
        node.setStringTerminated(true);
        if (!created)
            return false;
        return true;
    }
}

class TrieFactory {
    public static Trie getInstance() {
        return new NAryTrie();
    }
}

class Validations {
    public static boolean validateInput(String s) {
        if (s == null || s.equals(""))
            return false;
        return true;
    }
}

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int inpSize = in.nextInt();
        in.nextLine();
        Trie trie = TrieFactory.getInstance();
        boolean isValid = true;
        String invalidString = null;
        for (int i = 0; i < inpSize; i++) {
            String line = in.nextLine();
            if (isValid) {
                isValid = trie.addWithPrefixCheck(line);
                if (!isValid)
                    invalidString = line;
            }
        }
        if (isValid)
            System.out.println("GOOD SET");
        else {
            System.out.println("BAD SET");
            System.out.println(invalidString);
        }
    }
}