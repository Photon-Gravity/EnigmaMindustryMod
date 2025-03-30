package enigma.content;

import arc.graphics.Color;
import mindustry.type.Liquid;

public class EniLiquids {
	public static Liquid causticAmmonia, distilledAmmonia, oxidane, nitrate, nitroxide, deuteride;

	public static void load(){
		causticAmmonia = new Liquid("caustic-ammonia", Color.valueOf("656520"));
		distilledAmmonia = new Liquid("distilled-ammonia", Color.valueOf("97973e"));
		oxidane = new Liquid("oxidane", Color.valueOf("87bec7"));
		nitrate = new Liquid("nitrate", Color.valueOf("8f4c3b"));
		nitroxide = new Liquid("nitroxide", Color.valueOf("c55d42"));

		deuteride = new Liquid("deuteride", Color.valueOf("81973e"));

		oxidane.gas = true;
		nitroxide.gas = true;
	}
}
