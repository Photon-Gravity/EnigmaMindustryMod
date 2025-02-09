package enigma.content;

import arc.graphics.Color;
import enigma.custom.polymorph.PolymorphPowerType;

public class EniPolymorphTypes {
	public static PolymorphPowerType therma, ion, alkima, engima;

	public static void load(){
		therma = new PolymorphPowerType("therma");
		ion = new PolymorphPowerType("ion");
		alkima = new PolymorphPowerType("alkima");
		engima = new PolymorphPowerType("enigma");

		therma.color = Color.valueOf("ff944a");
		therma.colorDark = Color.valueOf("aa2911");

		ion.color = Color.valueOf("c0edfe");
		ion.colorDark = Color.valueOf("507fe1");

		alkima.color = Color.valueOf("67ff5e");
		alkima.colorDark = Color.valueOf("197341");

		engima.color = Color.valueOf("5cbe7b");
		engima.colorDark = Color.valueOf("3e3b7a");

	}
}
