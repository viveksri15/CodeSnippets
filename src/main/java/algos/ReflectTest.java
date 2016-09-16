package algos;

/**
 * Created by viveksrivastava on 20/12/15.
 */
public class ReflectTest {
	public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
		Class<? extends Reflect> reflect = (Class<? extends Reflect>) Class.forName("algos.Reflect");
		Reflect reflect1 = reflect.newInstance();
		Reflect reflect2 = reflect.newInstance();

		System.out.println("(reflect1 == reflect2) = " + (reflect1 == reflect2));
		reflect1.x = 1;
		reflect2.x = 2;
		System.out.println("reflect1 = " + reflect1.x);
		System.out.println("reflect1 = " + reflect2.x);
	}
}

class Reflect {
	public int x = 0;
}
