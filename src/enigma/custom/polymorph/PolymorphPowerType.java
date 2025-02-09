package enigma.custom.polymorph;


import arc.Core;
import arc.graphics.Color;
import arc.struct.Seq;
import enigma.content.EniShaders;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.logic.LAccess;
import mindustry.logic.Senseable;

import java.util.ArrayList;

public class PolymorphPowerType extends UnlockableContent implements Senseable {

	public Color color = Color.white.cpy(), colorDark = Color.gray.cpy();
	Color barColor;
	boolean hidden = false;

	public int id;

	public EniShaders.BlockShader thinShader, bulkShader;


	public static Seq<PolymorphPowerType> all = new Seq<>();
	public PolymorphPowerType(String name) {
		super(name);

		this.id = all.size;
		all.add(this);

		this.localizedName = Core.bundle.get("power." + this.name + ".name", this.name);
		this.description = Core.bundle.getOrNull("power." + this.name + ".description");
		this.details = Core.bundle.getOrNull("power." + this.name + ".details");
		this.unlocked = Core.settings != null && Core.settings.getBool(this.name + "-unlocked", false);
	}

	@Override
	public void init(){
		super.init();
	}

	@Override
	public boolean isHidden(){
		return hidden;
	}

	public Color barColor(){
		return barColor == null ? color : barColor;
	}

	@Override
	public ContentType getContentType() {
		return ContentType.effect_UNUSED;
	}

	@Override
	public double sense(LAccess sensor) {
		if(sensor == LAccess.color) return color.toFloatBits();
		return 0;
	}

	@Override
	public String toString(){
		return localizedName;
	}

}
