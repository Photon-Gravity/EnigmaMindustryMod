package enigma.content;

import arc.graphics.Color;
import arc.struct.Seq;
import enigma.graphics.EniPal;
import mindustry.type.Item;

public class EniItems {
	public static Item molybdenum, periclase, irtran, ruthenium, fulgoriteFiber, caesium;

	public static Seq<Item> keslominItems = new Seq<>();

	public static void load(){
		molybdenum = new Item("molybdenum", Color.valueOf("868c7a"));
		periclase = new Item("periclase", Color.valueOf("baa2b3"));
		irtran = new Item("irtran", EniPal.irtran);
		ruthenium = new Item("ruthenium", Color.valueOf("733a4f"));
		fulgoriteFiber = new Item("fulgorite-fiber", Color.valueOf("2b1d1d"));
		caesium = new Item("caesium", Color.valueOf("733a4f"));

		ruthenium.hidden = true;
		fulgoriteFiber.hidden = true;
		caesium.hidden = true;

		keslominItems.addAll(molybdenum, periclase, irtran, ruthenium, fulgoriteFiber, caesium);
	}
}
