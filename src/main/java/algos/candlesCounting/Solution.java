package algos.candlesCounting;

//import com.google.common.collect.ImmutableList;

import java.util.*;

/**
 * Created by viveksrivastava on 16/05/16.
 */
public class Solution {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int candleCount = scanner.nextInt();
		int maxColorCount = scanner.nextInt();
		List<Candle> candles = new ArrayList<>();
		for (int i = 0; i < candleCount; i++) {
			Candle candle = new Candle(scanner.nextInt(), scanner.nextInt());
			candles.add(candle);
		}
//		Collections.sort(candles);
//		System.out.println(candles);
		long count = Candle.count(0, new CandleColorCollection(new HashSet<Integer>()), candles, maxColorCount, -1);
		System.out.println(count);
	}
}

class Candle implements Comparable<Candle> {

	private static Map<Integer, Map<CandleColorCollection, Long>> dpMap = new HashMap<>();

	private int height;
	private int color;

	Candle(int height, int color) {
		this.height = height;
		this.color = color;
	}

	public static long count(final int position, final CandleColorCollection candleColorCollection, final List<Candle> candles, final int maxColorCount, int lastHeight) {
		long sum = 0;

//		Long newSum = getFromDP(position, candleColorCollection);
//		if (newSum != null)
//			return newSum;

		if (position == candles.size())
			return 0;

//		List<Integer> candlesTillNow1 = new ArrayList<>(candlesTillNow);
//		candlesTillNow1.add(0);

		long withoutSum = count(position + 1, new CandleColorCollection(candleColorCollection), candles, maxColorCount, lastHeight);
		long withSum = 0;

		CandleColorCollection candleColorCollection1 = new CandleColorCollection(candleColorCollection);
		if (lastHeight < candles.get(position).height) {
			candleColorCollection1.addCandle(candles.get(position).color);

			withSum += count(position + 1, candleColorCollection1, candles, maxColorCount, candles.get(position).height);

			if (candleColorCollection1.getColors().size() == maxColorCount) {
				sum = 1;
//				System.out.println("GOT " + candlesTillNow + " " + sum);
			}
		}

		/*System.out.println(candlesTillNow + " " + candles.get(position) + " "
						+ candleColorCollection + " " +
						withoutSum + "," + withSum + "," + sum
		);*/
		sum += withoutSum + withSum;
		//[4889,1, 2730,1, 44562,2, 3172,2, 14841,1, 44706,1, 15011,1, 6015,1]
//		System.out.println(candlesTillNow + " " + sum);
		addToDP(position, candleColorCollection1, sum);
		return sum;
	}

	private static void addToDP(int position, CandleColorCollection candleColorCollection, long sum) {
//		System.out.println("Position=" + position + " colors=" + candleColorCollection + " " + sum);
		Map<CandleColorCollection, Long> candleColorCollectionIntegerMap = dpMap.get(position);
		if (candleColorCollectionIntegerMap == null) {
			candleColorCollectionIntegerMap = new HashMap<>();
			dpMap.put(position, candleColorCollectionIntegerMap);
		}
		candleColorCollectionIntegerMap.put(candleColorCollection, sum);
	}

	private static Long getFromDP(int position, CandleColorCollection candleColorCollection) {
		Map<CandleColorCollection, Long> candleColorCollectionIntegerMap = dpMap.get(position);
		if (candleColorCollectionIntegerMap != null) {
			Long integer = candleColorCollectionIntegerMap.get(candleColorCollection);
			return integer;
		}
		return null;
	}

	@Override
	public String toString() {
		return height + "," + color;
	}

	@Override
	public int compareTo(Candle o) {
		return height - o.height;
	}
}

class CandleColorCollection {
	private final Set<Integer> colors;

	public CandleColorCollection(Set<Integer> colors) {
		this.colors = colors;
	}

	public CandleColorCollection(CandleColorCollection candleColorCollection) {
		colors = new HashSet<>(candleColorCollection.colors);
	}

	public void addCandle(int c) {
		colors.add(c);
	}

	public Set<Integer> getColors() {
		return colors;
	}

	@Override
	public boolean equals(Object obj) {
		assert obj instanceof CandleColorCollection;
		CandleColorCollection candleColorCollection = (CandleColorCollection) obj;
		if (colors.size() != candleColorCollection.colors.size())
			return false;
		for (Integer c : colors)
			if (!candleColorCollection.colors.contains(c))
				return false;
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		int[] primes = new int[]{7, 11, 13, 17, 19, 23, 29};
		int i = 0;
		for (Integer c : colors)
			hash += c * primes[i];
		return hash;
	}

	@Override
	public String toString() {
		return colors.toString();
	}

	public boolean contains(int color) {
		return colors.contains(color);
	}
}
