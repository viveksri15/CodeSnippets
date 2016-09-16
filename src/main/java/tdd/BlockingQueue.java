package tdd;

/**
 * Created by viveksrivastava on 10/05/16.
 */
/*
	Framework of the problem:
	Problem statment
	Input
	Output
	Algo
	DS
	Limitations
	Assumptions
	Test Cases

 */
public interface BlockingQueue<T> {
	void put(T t);

	T get() throws InterruptedException;

	int getSize();
}