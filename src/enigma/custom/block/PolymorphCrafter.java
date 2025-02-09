package enigma.custom.block;

import arc.Core;
import arc.func.Func;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.struct.Seq;
import enigma.custom.polymorph.PolymorphPowerStack;
import enigma.custom.polymorph.PolymorphPowerType;
import enigma.custom.polymorph.PolymorphSystem;
import enigma.custom.polymorph.interfaces.PolymorphConsumer;
import enigma.custom.polymorph.interfaces.PolymorphUtilizer;
import enigma.custom.stats.EniStatVal;
import mindustry.content.Fx;
import mindustry.gen.Building;
import mindustry.ui.Bar;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.Stat;

import static enigma.util.Consts.DEBUG;

public class PolymorphCrafter extends GenericCrafter {

	public PolymorphPowerStack[] consumedPower;

	public PolymorphCrafter(String name) {
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

	public class PolymorphCrafterBuild extends GenericCrafterBuild implements PolymorphUtilizer, PolymorphConsumer{
		public PolymorphSystem system = null;

		@Override
		public void updateEfficiencyMultiplier() {
			super.updateEfficiencyMultiplier();
			efficiency *= system != null ? system.satisfaction() : 0;
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

		@Override
		public void draw() {
			if(system != null && DEBUG){
				Fx.rand.setSeed(system.ID);
				Draw.color(new Color(Fx.rand.nextInt() | 0x000000FF));
			}
			super.draw();

			if(DEBUG) Draw.reset();
		}
	}
}
