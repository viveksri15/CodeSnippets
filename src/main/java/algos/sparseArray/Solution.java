package algos.sparseArray;

import java.util.*;

/*
    Framework the problem, in which following things will come:

    1. Input and validations module
    2. Out and checked exception handling module
    3. Core algorithm
    4. Internal datastructure to assist the algo
    5. Assumptions
    6. Limitations
    7. Test cases and dry run

    Also, for the sake of solution, the time will have to be less than 20 min
*/

/*
    Assumptions:
    1. Each string are less than 20 characters
    2. We are performing complete string matches
    3. o(1) solution is to use a hashmap
    4. log(n) solution is to use a trie which limits memory. we use hashmap


    Limitations:
    parallel/concurrent insertion could be there.

    DS:
       HashMap <String, Count>

*/

//Specification:
interface IStringStore{
    void put(String s);
    int count(String s);
}

interface IO{
    void readAndPut();
    void readAndCount();
}

//Implementation
final class MapStringStore implements IStringStore{
    private final Map<String, Integer> stringCountMap=new HashMap<>();
    public synchronized void put(String s){
        if(s==null)
            return;
        Integer count=stringCountMap.get(s);
        if(count == null)
            count = 0;
        stringCountMap.put(s, count);
    }

    public synchronized int count(String s){
        if(s==null)
            return 0;
        Integer count= stringCountMap.get(s);
        if(count == null)
            return 0;

        return count;
    }
}

class StdIO implements IO{
    private Scanner sc;
    private IStringStore stringStore;
    public StdIO(){
        sc = new Scanner(System.in);
        stringStore = new MapStringStore();
    }
    public void readAndPut(){

        int count = Integer.parseInt(sc.nextLine());
        for(int i=0; i<count; i++){
            stringStore.put(sc.nextLine());
        }
    }
    public void readAndCount(){
        int count = Integer.parseInt(sc.nextLine());
        for(int i=0; i<count; i++){
            System.out.println(stringStore.count(sc.nextLine()));
        }
    }
}

public class Solution {

    public static void main(String[] args) {
        IO io = new StdIO();
        io.readAndPut();
        io.readAndCount();
    }
}