package tdd;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.gson.*;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by viveksrivastava on 30/06/16.
 */
public class UnexpectedGsonBehaviorTest {

	@Test
	public void testUseCustomFruitsInstanceCreator() {
		/*Gson gson = new GsonBuilder()
				.registerTypeAdapter(Fruits.class, new InstanceCreator<Fruits>() {
					public Fruits createInstance(Type type) {
						return new FruitsImpl();
					}
				}).create();*/

		InterfaceAdapter<Fruits> typeAdapter = new InterfaceAdapter<>();
//		Gson gson = new Gson();
//		String fruitsJson = "{\"fruitList\": [\"apples\", \"pears\", \"bananas\"]}";
		List<String> fruitsList = new ArrayList<>();
		fruitsList.add("vivek");
		fruitsList.add("ishant");
		Fruits fruits = new FruitsImpl();
		fruits.setFruitList(fruitsList);

		Kryo kryo = new Kryo();
		Output output = new Output(1024);
		kryo.writeClassAndObject(output, fruits);

		Input input = new Input(output.toBytes());
		Fruits fruits1 = kryo.readObject(input, Fruits.class);

		System.out.println("fruits1 = " + fruits1);


//		Gson gson = new GsonBuilder().registerTypeAdapter(Fruits.class, typeAdapter)
//				.create();


//		String fruitsJson = gson.toJson(fruits);
//		System.out.println("input: " + fruitsJson);
//		Fruits fruitsObj = gson.fromJson(fruitsJson, Fruits.class);
//		System.out.println("output: " + fruitsObj);

	}

	public interface Fruits {
		List<String> getFruitList();

		void setFruitList(List<String> fruitList);

	}

	final class InterfaceAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
		public JsonElement serialize(T object, Type interfaceType, JsonSerializationContext context) {
			final JsonObject wrapper = new JsonObject();
			wrapper.addProperty("type", object.getClass().getName());
			wrapper.add("data", context.serialize(object));
			return wrapper;
		}

		public T deserialize(JsonElement elem, Type interfaceType, JsonDeserializationContext context) throws JsonParseException {
			final JsonObject wrapper = (JsonObject) elem;
			final JsonElement typeName = get(wrapper, "type");
			final JsonElement data = get(wrapper, "data");
			final Type actualType = typeForName(typeName);
			return context.deserialize(data, actualType);
		}

		private Type typeForName(final JsonElement typeElem) {
			try {
				return Class.forName(typeElem.getAsString());
			} catch (ClassNotFoundException e) {
				throw new JsonParseException(e);
			}
		}

		private JsonElement get(final JsonObject wrapper, String memberName) {
			final JsonElement elem = wrapper.get(memberName);
			if (elem == null)
				throw new JsonParseException("no '" + memberName + "' member found in what was expected to be an interface wrapper");
			return elem;
		}
	}

	public class FruitsImpl implements Fruits {

		private List<String> fruitList;

		public List<String> getFruitList() {
			return fruitList;
		}

		public void setFruitList(List<String> fruitList) {
			this.fruitList = fruitList;
		}

		public String toString() {
			return "FruitsImpl{" + "fruitList=" + fruitList + '}';
		}
	}

}