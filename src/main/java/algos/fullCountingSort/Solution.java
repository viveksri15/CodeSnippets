package algos.fullCountingSort;

import java.util.*;

public class Solution {

	public static void main(String[] args) {
		Map<Integer, List<Integer>> treeMap = new HashMap<>();
		Scanner scanner = new Scanner(System.in);
		int inputSize = scanner.nextInt();
		Map<Integer, String> keyVal = new HashMap<>();
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < inputSize; i++) {
			int key = scanner.nextInt();
			String val = scanner.nextLine().trim();
			List<Integer> list = treeMap.get(key);
			if (list == null) {
				list = new LinkedList<>();
				treeMap.put(key, list);
			}
			list.add(i);
			keyVal.put(i, val);
		}
		System.out.println((System.currentTimeMillis() - t1));
		List<Integer> keyList = new ArrayList<>(treeMap.keySet());
		Collections.sort(keyList);
		System.out.println((System.currentTimeMillis() - t1));
//        System.out.println(keyVal);
		//Map.Entry<Integer, List<Integer>> first = null;
		StringBuilder builder = new StringBuilder();
		//while((first = treeMap.pollFirstEntry()) != null){
		for (Integer key : keyList) {
			List<Integer> indexes = treeMap.get(key);
			for (int i : indexes) {
				if (i >= inputSize / 2)
					builder.append(keyVal.get(i)).append(" ");
				else
					builder.append("- ");
			}
		}
		System.out.println((System.currentTimeMillis() - t1));
		System.out.println(builder.toString().trim());
	}
}