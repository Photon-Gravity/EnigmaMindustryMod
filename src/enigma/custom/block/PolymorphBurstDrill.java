package enigma.custom.block;

import arc.Core;
import arc.func.Func;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Interp;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Tmp;
import enigma.custom.polymorph.PolymorphPowerStack;
import enigma.custom.polymorph.PolymorphPowerType;
import enigma.custom.polymorph.PolymorphSystem;
import enigma.custom.polymorph.interfaces.PolymorphConsumer;
import enigma.custom.polymorph.interfaces.PolymorphUtilizer;
import enigma.custom.stats.EniStatVal;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.ui.Bar;
import mindustry.world.blocks.production.BurstDrill;
import mindustry.world.meta.Stat;


public class PolymorphBurstDrill extends BurstDrill {

	TextureRegion[] arrowRegions, arrowBlurRegions;

	public PolymorphPowerStack[] consumedPower;

	public PolymorphBurstDrill(String name) {
		super(name);
	}

	@Override
	public void setStats() {
		super.setStats();

		stats.remove(Stat.powerUse);
		if(consumedPower != null){
			stats.add(Stat.input, EniStatVal.powerTypes(true, consumedPower));
		}
	}

	@Override
	public void setBars(){
		super.setBars();
		addBar("power", makePowerBalance());
	}

	public static Func<Building, Bar> makePowerBalance(){
		return entity -> new Bar(() ->
				Core.bundle.format(
						"bar.polymorphcons", //very dense notation that amounts to nullproofing
						((PolymorphUtilizer)entity).getSystem() != null ? (((PolymorphUtilizer)entity).getSystem().type != null ? ((PolymorphUtilizer)entity).getSystem().type.localizedName : "N/A") : "N/A"
				),
				() -> ((PolymorphUtilizer)entity).getSystem() != null ? (((PolymorphUtilizer)entity).getSystem().type != null ? ((PolymorphUtilizer)entity).getSystem().type.color : Color.gray) : Color.gray,
				() -> Mathf.clamp(
						((PolymorphUtilizer)entity).getSystem() != null ? ((PolymorphUtilizer)entity).getSystem().satisfaction() : 0
				)
		);
	}

	@Override
	public void load() {
		super.load();
		arrowRegions = new TextureRegion[arrows];
		arrowBlurRegions = new TextureRegion[arrows];

		for(int i=0; i < arrows ;i++){
			arrowRegions[i] = Core.atlas.find(name + "-arrow-" + i);
			arrowBlurRegions[i] = Core.atlas.find(name + "-arrow-blur-" + i);
		}
	}

	public class StaticBurstDrillBuild extends BurstDrillBuild implements PolymorphUtilizer, PolymorphConsumer {

		PolymorphSystem system;

		@Override
		public void updateEfficiencyMultiplier() {
			super.updateEfficiencyMultiplier();
			efficiency *= system != null ? system.satisfaction() : 0;
		}

		@Override
		public void draw(){
			Draw.rect(region, x, y);
			drawDefaultCracks();

			Draw.rect(topRegion, x, y);
			if(invertTime > 0 && topInvertRegion.found()){
				Draw.alpha(Interp.pow3Out.apply(invertTime));
				Draw.rect(topInvertRegion, x, y);
				Draw.color();
			}

			if(dominantItem != null && drawMineItem){
				Draw.color(dominantItem.color);
				Draw.rect(itemRegion, x, y);
				Draw.color();
			}

			float fract = smoothProgress;

			for(int j = 0; j < arrows; j++){
				float arrowFract = (arrows - 1 - j);
				float a = Mathf.clamp(fract * arrows - arrowFract);

				//TODO maybe just use arrow alpha and draw gray on the base?
				Draw.z(Layer.block);
				Draw.color(baseArrowColor, arrowColor, a);
				Draw.rect(arrowRegions[j], x, y);
				Draw.color(arrowColor);

				if(arrowBlurRegion.found()){
					Draw.z(Layer.blockAdditive);
					Draw.blend(Blending.additive);
					Draw.alpha(Mathf.pow(a, 10f));
					Draw.rect(arrowBlurRegions[j], x, y);
					Draw.blend();
			}}
			Draw.color();

			if(glowRegion.found()){
				Drawf.additive(glowRegion, Tmp.c2.set(glowColor).a(Mathf.pow(fract, 3f) * glowColor.a), x, y);
			}
		}
		@Override
		public float getConsumed(PolymorphPowerType type) {
			if(!shouldConsume()) return 0;

			for(PolymorphPowerStack stack : consumedPower){
				if(stack.type == type){
					return stack.quantity;
				}
			}
			return 0;
		}

		@Override
		public boolean consumesType(PolymorphPowerType type) {
			for(PolymorphPowerStack stack : consumedPower){
				if(stack.type == type){
					return true;
				}
			}
			return false;
		}

		@Override
		public void setSystem(PolymorphSystem newSystem) {
			this.system = newSystem;
		}

		@Override
		public PolymorphSystem getSystem() {
			return system;
		}

		@Override
		public PolymorphPowerType getEnforcedPowerType() {
			if(consumedPower.length == 1) return consumedPower[0].type;
			return null;
		}

		@Override
		public void addToSystem(PolymorphSystem s, Seq<Integer> scheduled) {
			this.system = s;
		}
	}
}
