package algos;/*

	Problem: reverse linked-list in groups of m. For a list where length<m, reverse the pending

*/


interface LLWorker {
	LLNode doWork(LLNode node);
}

class LLNode {
	private int data;
	private LLNode next;

	public LLNode(int data) {
		this.data = data;
	}

	public LLNode addNext(int data) {
		LLNode llNode = new LLNode(data);
		setNext(llNode);
		return llNode;
	}

	public LLNode getNext() {
		return next;
	}

	public void setNext(LLNode next) {
		this.next = next;
	}

	public int getData() {
		return data;
	}

	@Override
	public String toString() {
		return data + "";
//		return data + "," + next;
	}

	public String getAll() {
		StringBuilder stringBuffer = new StringBuilder();
		LLNode llNode = this;
		while (llNode != null) {
			stringBuffer.append(llNode.getData()).append(",");
			llNode = llNode.getNext();
		}
		return stringBuffer.toString();
	}
}

class GroupReverseWorder implements LLWorker {

	private int lengthToReverse = 0;

	public int getLengthToReverse() {
		return lengthToReverse;
	}

	public void setLengthToReverse(int length) {
		this.lengthToReverse = length;
	}

	public LLNode doWork(LLNode node) {
		if (node == null || lengthToReverse == 0)
			return node;

		LLNode dummyHead = new LLNode(Integer.MIN_VALUE);
		dummyHead.setNext(node);

		//1-2-3-4-5-6

		LLNode nextNode = node;
		LLNode start = dummyHead;
//		System.out.println("dummyHead = " + dummyHead.getAll());
		do {
			LLNode currentNode = reverse(start, nextNode);
			start = currentNode;
			nextNode = currentNode.getNext();
//			System.out.println("dummyHead_new = " + dummyHead.getAll());
		} while (nextNode != null);

		return dummyHead.getNext();
	}

	private LLNode reverse(LLNode headNode, LLNode root) {
		LLNode previous = root;
		LLNode toChange = root.getNext();

		for (int i = 0; i < lengthToReverse - 1 && toChange != null; i++) {
			LLNode next = toChange.getNext();
			toChange.setNext(previous);
			previous = toChange;
			toChange = next;
		}

		root.setNext(toChange);
		headNode.setNext(previous);
		return root;
	}
}

class LLOps {
	public LLNode execute(LLNode node, LLWorker worker) {
		return worker.doWork(node);
	}
}

class LLWorkerFactory {
	public static LLWorker getInstance() {
		return new GroupReverseWorder();
	}
}

public class TestLLOps {
	public static void main(String[] args) {
		LLWorker llWorker = LLWorkerFactory.getInstance();
		if (llWorker instanceof GroupReverseWorder)
			((GroupReverseWorder) llWorker).setLengthToReverse(5);

		LLNode llNode = new LLNode(1);
		llNode.addNext(2).addNext(3).addNext(4).addNext(5).addNext(6).addNext(7).addNext(8).addNext(9).addNext(10);
		System.out.println(llNode);

		LLOps llOps = new LLOps();
		LLNode finalNode = llOps.execute(llNode, llWorker);

		System.out.println(finalNode.getAll());
	}
}