package enigma.content;

import arc.graphics.Color;
import enigma.custom.polymorph.PolymorphPowerType;
import enigma.graphics.EniPal;

public class EniPolymorphTypes {
	public static PolymorphPowerType therma, ion, lux, alkima, enigma;

	public static void load(){
		therma = new PolymorphPowerType("therma");
		ion = new PolymorphPowerType("ion");
		lux = new PolymorphPowerType("lux");
		alkima = new PolymorphPowerType("alkima");
		enigma = new PolymorphPowerType("enigma");

		therma.color = Color.valueOf("ff944a");
		therma.colorDark = Color.valueOf("aa2911");

		ion.color = Color.valueOf("c0edfe");
		ion.colorDark = Color.valueOf("507fe1");

		lux.color = EniPal.irtranLight;
		lux.colorDark = EniPal.irtran;

		alkima.color = Color.valueOf("67ff5e");
		alkima.colorDark = Color.valueOf("197341");

		enigma.color = Color.valueOf("5cbe7b");
		enigma.colorDark = Color.valueOf("3e3b7a");

	}
}
