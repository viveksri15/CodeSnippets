package tests;

/**
 * Created by viveksrivastava on 23/05/16.
 */
public class TestClass<T extends TestClass> {
    public static void main(String[] args) {
        String x = "http://localhost:6006/1.0.0/card/merchant/hdfc/webVerify";
        String regex = ".*://(" + "localhost|127.0.0.1" + ")(:.*|/.*|$)";
        System.out.println(regex + " " + x.matches(regex));
    }
}
