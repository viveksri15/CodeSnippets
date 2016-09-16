package algos;//package in.viveksrivastava.linkedListProblems

/*
*  3 -> 4 -> 5 -> 9 -> 6 -> 1 -> 3 -> 5 -> 4 -> 10
*  3 -> 4 -> 5 -> 1 -> 3 -> 5 -> 4 -> 6 -> 9 -> 10
*
*/

/*
*	Modularity and Testability
*	
*/

class Node {
	private Node next = null;
	private Integer data = null;

	public Node(int data) {
		this.data = data;
	}

	public Node getNext() {
		return next;
	}

	public Integer getData() {
		return data;
	}

	public void append(Node next) {
		this.next = next;
	}

	public void append(Node head, Integer data) {

		if (data == null)
			return;

		if (head.next == null) {
			Node next = new Node(data);
			head.next = next;
		} else {
			append(head.next, data);
		}
	}
}


class LinkedListOps {
	public Node split(Node head, int center) {
		Node lessList = null;
		Node lessListHead = null;
		Node moreEqualList = null;
		Node moreEqualListHead = null;
		if (head == null)
			return null;

		int countOfEqual = 0;

		while (true) {

			Integer data = head.getData();

			if (data < center) {
				if (lessList == null) {
					lessList = new Node(data);
					lessListHead = lessList;
				} else {
					lessList.append(lessList, data);
					lessList = lessList.getNext();
				}
			} else if (data == center) {
				countOfEqual++;
			} else {
				if (moreEqualList == null) {
					moreEqualList = new Node(data);
					moreEqualListHead = moreEqualList;
				} else {
					moreEqualList.append(moreEqualList, data);
					moreEqualList = moreEqualList.getNext();
				}
			}

			head = head.getNext();

			if (head == null)
				break;
		}

		int start = 0;
		if (lessList == null && countOfEqual > 0) {
			lessList = new Node(center);
			lessListHead = lessList;
			start++;
		}

		if (countOfEqual > 0) {
			for (int i = start; i < countOfEqual; i++) {
				lessList.append(lessList, center);
				lessList = lessList.getNext();
			}
		}

		if (moreEqualListHead == null)
			return lessListHead;

		if (lessListHead == null)
			return moreEqualListHead;

		lessList.append(moreEqualListHead);

		return lessListHead;
	}
}

public class NodeSplitMain {
	public static void main(String[] args) {
		Node head = new Node(3);
		head.append(head, 4);
		head.append(head, 5);
		head.append(head, 9);
		head.append(head, 6);
		head.append(head, 1);
		head.append(head, 3);
		head.append(head, 5);
		head.append(head, 4);
		head.append(head, 10);

		LinkedListOps linkedListOps = new LinkedListOps();

		Node newHead = linkedListOps.split(head, 0);

		while (true) {
			if (newHead != null) {
				System.out.println(newHead.getData());
				newHead = newHead.getNext();
			} else
				break;
		}
	}
}