package algos.Kadane;

/*
	Framework
		Input + Validation
		Output and formatting
		DS
		Algo
		Assumptions
		Limitations
		Tests
*/

/*
	Assumptions
		Integers
	Limitations
		Int limitations: 2^32
*/

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Specification:
interface ISum {
	int add(int number);
}

interface IO {
	void read();

	void write() throws IOException;
}

//implementation

class ContinuousSum implements ISum {

	private Integer sumTillNow;
	private Integer maxSum;

	public int add(int number) {
		if (sumTillNow == null) {
			sumTillNow = number;
			maxSum = sumTillNow;
		} else if (sumTillNow < 0) {
			sumTillNow = number;
		} else {
			sumTillNow = sumTillNow + number;
		}

		if (maxSum == null || maxSum < sumTillNow)
			maxSum = sumTillNow;
		return maxSum;
	}
}

class NonContinuousSum implements ISum {

	private Integer sumTillNow;

	public int add(int number) {
		if (sumTillNow == null) {
			sumTillNow = number;
		} else if (sumTillNow < 0 && number > sumTillNow && number < 0) {
			sumTillNow = number;
		} else if (number > 0) {
			if (sumTillNow < 0)
				sumTillNow = 0;

			sumTillNow += number;
		}
		return sumTillNow;
	}
}

class StreamIO implements IO {
	private InputStream inputStream;
	private OutputStream outputStream;
	private List<Integer> result = new ArrayList<>();

	public StreamIO(InputStream inputStream, OutputStream outputStream) {
		this.inputStream = inputStream;
		this.outputStream = outputStream;
	}

	public void read() {
		Scanner sc = new Scanner(inputStream);
		int testCases = sc.nextInt();

		for (int i = 0; i < testCases; i++) {

			List<ISum> adders = new ArrayList<>();
			adders.add(new ContinuousSum());
			adders.add(new NonContinuousSum());

			int elementCount = sc.nextInt();
			for (int j = 0; j < elementCount; j++) {
				int element = sc.nextInt();
				for (ISum adder : adders) {
					if (j == elementCount - 1)
						result.add(adder.add(element));
					else
						adder.add(element);
				}
			}
			try {
				write();
				result.clear();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void write() throws IOException {
		int k = 0;
		for (Integer res : result) {
			k++;
			if (k < result.size())
				outputStream.write((res + " ").getBytes());
			else
				outputStream.write((res + "\n").getBytes());
		}
	}
}

public class Solution {
	public static void main(String[] args) throws IOException {
		IO io = new StreamIO(System.in, System.out);
		io.read();
	}
}