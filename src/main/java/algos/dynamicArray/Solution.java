package algos.dynamicArray;

/**
 * Created by viveksrivastava on 07/04/16.
 */

/*
    Framework of the problem:

	1. Input data
		Validations
	2. Core algorithm
	3. Out put data
		Exception Handling
	4. Assumptions
	5. bounded input
	6. Test Framework
	7. Assumptions


	Assumptions
		- Ignoring invalid sequence

	Core algo
		1. DS:
			HashMap of arrays
				Key: algos.X XOR lastAns MOD N

				HashMap<Integer, ArrayList<Integer>>

*/


import java.io.*;
import java.util.*;


class SequenceProcessorFactory {
    public static ISequenceProcessor getSequenceProcessor() {
        return new DASequenceProcessor();
    }
}

interface ISequenceProcessor {
    void process(int numberOfSequence, String query);
}

class DASequenceProcessor implements ISequenceProcessor {
    private final Map<Integer, List<Integer>> dataStore = new HashMap<>();
    private int lastAns = 0;
    private IOHandler ioHandler = IOHandlerFactory.getHandler();

    public void process(int numberOfSequence, String query) {
        if (query == null)
            return;
        String[] vals = query.split("\\W");

        if (vals.length != 3)
            return;

        int x = Integer.parseInt(vals[1]);
        int y = Integer.parseInt(vals[2]);
        int position = ((x ^ lastAns) % numberOfSequence);
        List<Integer> listAtPosition = dataStore.get(position);
//        System.out.println("position = " + position);
//        System.out.println("listAtPosition = " + listAtPosition);
        if (vals[0].equals("1")) {

            if (listAtPosition == null) {
                listAtPosition = new ArrayList<Integer>();
                dataStore.put(position, listAtPosition);
            }
            listAtPosition.add(y);
        } else if (vals[0].equals("2")) {

            if (listAtPosition == null)
                return;

            int size = listAtPosition.size();

            lastAns = listAtPosition.get(y % size);
            ioHandler.println(lastAns);
        }
    }
}

class IOHandlerFactory {
    public static IOHandler getHandler() {
        return new STDIOHandler();
    }
}

interface IOHandler {
    void println(Object obj);

    String readLine() throws Exception;
}

class STDIOHandler implements IOHandler {
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public void println(Object obj) {
        System.out.println(obj);
    }

    public String readLine() throws Exception {
        return br.readLine();
    }
}

public class Solution {

    public static void main(String[] args) throws Exception {
        IOHandler ioHandler = IOHandlerFactory.getHandler();
        String metaData = ioHandler.readLine();
        String[] metaDatas = metaData.split("\\W");
        if (metaDatas.length != 2)
            return;
        int sizeOfSequence = Integer.parseInt(metaDatas[0]);
        int sizeOfQuery = Integer.parseInt(metaDatas[1]);

        ISequenceProcessor sequenceProcessor = SequenceProcessorFactory.getSequenceProcessor();
        for (int i = 0; i < sizeOfQuery; i++) {
            sequenceProcessor.process(sizeOfSequence, ioHandler.readLine());
        }
    }
}