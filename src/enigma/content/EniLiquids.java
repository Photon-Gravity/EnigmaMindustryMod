package enigma.content;

import arc.graphics.Color;
import mindustry.type.Liquid;

public class EniLiquids {
	public static Liquid causticAmmonia, distilledAmmonia, deuteride;

	public static void load(){
		causticAmmonia = new Liquid("caustic-ammonia", Color.valueOf("656520"));
		distilledAmmonia = new Liquid("distilled-ammonia", Color.valueOf("97973e"));
		deuteride = new Liquid("deuteride", Color.valueOf("81973e"));
	}
}
