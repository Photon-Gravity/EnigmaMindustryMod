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
import enigma.custom.polymorph.*;
import enigma.custom.stats.EniStatVal;
import mindustry.core.UI;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.ui.Bar;
import mindustry.world.blocks.production.BurstDrill;
import mindustry.world.meta.Stat;


public class PolymorphBurstDrill extends BurstDrill {

	TextureRegion[] arrowRegions, arrowBlurRegions;

	public PolymorphPowerStack consumed;

	public PolymorphBurstDrill(String name) {
		super(name);
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
						((IPolymorphUtilizer)entity).getModule() != null && ((IPolymorphUtilizer)entity).getModule().getEnforced() != null ? ((IPolymorphUtilizer)entity).getModule().getEnforced().localizedName : "N/A"
				),
				() -> ((IPolymorphUtilizer)entity).getModule() != null && ((IPolymorphUtilizer)entity).getModule().getEnforced() != null ? ((IPolymorphUtilizer)entity).getModule().getEnforced().color : Color.gray,
				() -> ((IPolymorphUtilizer)entity).getModule() != null ? ((IPolymorphUtilizer)entity).getModule().satisfaction(((PolymorphBurstDrill)entity.block).consumed.type) : 0
		);
	}

	@Override
	public void setStats() {
		super.setStats();

		stats.remove(Stat.powerUse);
		if(consumed != null){
			stats.add(Stat.input, EniStatVal.power(consumed, true));
		}
	}

	@Override
	public void load() {
		super.load();
		arrowRegions = new TextureRegion[arrows];
		arrowBlurRegions = new TextureRegion[arrows];

		for(int i=0; i < arrows ;i++){
			arrowRegions[i] = Core.atlas.find(name + "-arrow-" + (arrows-1-i));
			arrowBlurRegions[i] = Core.atlas.find(name + "-arrow-blur-" + i);
		}
	}

	public class StaticBurstDrillBuild extends BurstDrillBuild implements IPolymorphUtilizer {

		PolymorphModule module;

		@Override
		public void update() {
			super.update();

			if(module == null) {
				module = new PolymorphModule(pos());

			}
			if(!getModule().inSystem()){
				PolymorphUpdater.makeSystem(pos());
			}
		}


		@Override
		public void updateEfficiencyMultiplier() {
			super.updateEfficiencyMultiplier();
			efficiency *= module != null ? module.satisfaction(consumed.type) : 0;
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
		public PolymorphModule getModule() {
			return module;
		}

		@Override
		public PolymorphPowerType enforced() {
			return null;
		}

		@Override
		public float produced(PolymorphPowerType ofType) {
			return 0;
		}

		@Override
		public float consumed(PolymorphPowerType ofType) {
			return ofType == consumed.type ? consumed.quantity : 0;
		}

		@Override
		public float storable(PolymorphPowerType ofType) {
			return 0;
		}

		@Override
		public float stored(PolymorphPowerType ofType) {
			return 0;
		}

		@Override
		public void store(PolymorphPowerStack stack) {

		}
	}
}