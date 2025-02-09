package enigma.content;

import arc.graphics.Color;
import arc.struct.Seq;
import enigma.graphics.EniPal;
import mindustry.type.Item;

public class EniItems {
	public static Item molybdenum, periclase, irtran;

	public static Seq<Item> keslominItems = new Seq<>();

	public static void load(){
		molybdenum = new Item("molybdenum", Color.valueOf("868c7a"));
		periclase = new Item("periclase", Color.valueOf("baa2b3"));
		irtran = new Item("irtran", EniPal.irtran);

		keslominItems.addAll(molybdenum, periclase, irtran);
	}
}
