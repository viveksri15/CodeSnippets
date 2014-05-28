package in.viveksrivastava.examples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static java.util.Collections.sort;

/**
 * Created by Vivek on 28-05-2014.
 * <p>
 * This is a small example of lambdas in java
 */
public class LambdaExample {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("*********");
        list.add("***");
        list.add("***********");
        list.add("*****");
        list.add("******");
        list.add("*");
        list.add("*****");
        list.add("*********");
        list.add("*************");
        list.add("**");

        //basic example
        //sorting the above list

        sort(list, (String s1, String s2) -> {
            if (s1.length() > s2.length())
                return 1;
            else if (s1.length() < s2.length())
                return -1;
            return 0;
        });

        //This example shows lambda usage without
        list.forEach((s) ->
                System.out.println(s));
    }
}
