package tests;

interface Person {
	int getAge();

	String getName();
}

class PersonAbst {
	private int age;
	private String name;

	protected PersonAbst(int age, String name) {
		this.age = age;
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name + "," + age;
	}
}

public class SortLambda {
	public static void main(String[] args) {
		/*List<PersonAbst> persons = new ArrayList<>();
		persons.add(new PersonAbst(12, "vivek"));
		persons.add(new PersonAbst(11, "vivek1"));

		Collections.sort(persons, (p1, p2) ->
						p1.getAge() - p2.getAge()
		);

		System.out.println(persons);*/
	}
}
