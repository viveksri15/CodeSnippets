package algos;

interface NodeWorker {
	TreeNode execute(TreeNode node) throws Exception;
}

/**
 * Created by viveksrivastava on 13/03/16.
 */

/*
1 2 3
2 4 5
3 6
4 7 8

----

1 3 2
2 5 4
3   6
4 8 7
*/

class TreeNode {

	private TreeNode left, right;
	private Object data;

	public TreeNode(Object data) throws Exception {
		if (data == null)
			throw new IllegalStateException("Data cannot be null");
		this.data = data;
	}

	public TreeNode addLeft(Object data) throws Exception {
		TreeNode dataNode = new TreeNode(data);
		setLeft(dataNode);
		return dataNode;
	}

	public TreeNode addRight(Object data) throws Exception {
		TreeNode dataNode = new TreeNode(data);
		setRight(dataNode);
		return dataNode;
	}

	public TreeNode getLeft() {
		return left;
	}

	public void setLeft(TreeNode left) {
		this.left = left;
	}

	public TreeNode getRight() {
		return right;
	}

	public void setRight(TreeNode right) {
		this.right = right;
	}

	public Object getData() {
		return data;
	}

	@Override
	public String toString() {
		return data + ", left=" + left + ", right=" + right;
	}
}

class NodeOps {
	public TreeNode doOps(TreeNode node, NodeWorker worker) throws Exception {
		return worker.execute(node);
	}
}

class MirrorImageWorker implements NodeWorker {
	public TreeNode execute(TreeNode node) throws Exception {
		if (node == null) {
			return null;
		}

		TreeNode mirroredNode = new TreeNode(node.getData());
		swapRecursive(node, mirroredNode);

		return mirroredNode;
	}

	private void swapRecursive(TreeNode root, TreeNode mirroredRoot) throws Exception {
		if (root == null)
			return;
		if (mirroredRoot == null)
			return;

		swapLeftRight(root, mirroredRoot);
		swapRecursive(root.getLeft(), mirroredRoot.getRight());
		swapRecursive(root.getRight(), mirroredRoot.getLeft());
	}

	private void swapLeftRight(TreeNode root, TreeNode mirroredRoot) throws Exception {
		if (root == null)
			return;
		if (mirroredRoot == null)
			return;
		if (root.getRight() != null)
			mirroredRoot.setLeft(new TreeNode(root.getRight().getData()));
		if (root.getLeft() != null) {
			mirroredRoot.setRight(new TreeNode(root.getLeft().getData()));
		}
	}
}

class WorkerFactory {
	public static NodeWorker getWorker() {
		//Should be based on config etc
		return new MirrorImageWorker();
	}
}

public class MirrorImageOfTree {
	public static void main(String[] args) throws Exception {
		TreeNode node = new TreeNode(1);
		node.addLeft(2).addRight(4).addLeft(4);
		node.addRight(3);

		System.out.println(node);

		NodeOps nodeOps = new NodeOps();
		TreeNode mirroredNode = nodeOps.doOps(node, WorkerFactory.getWorker());

		System.out.println(mirroredNode);
	}
}