package enigma.custom.block;

import arc.Core;
import arc.func.Func;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.struct.Seq;
import enigma.custom.stats.EniStatVal;
import enigma.custom.stats.EniStats;
import enigma.custom.polymorph.PolymorphPowerStack;
import enigma.custom.polymorph.PolymorphPowerType;
import enigma.custom.polymorph.PolymorphSystem;
import enigma.custom.polymorph.interfaces.PolymorphProvider;
import enigma.custom.polymorph.interfaces.PolymorphUtilizer;
import mindustry.content.Fx;
import mindustry.gen.Building;
import mindustry.ui.Bar;
import mindustry.world.blocks.power.ThermalGenerator;
import mindustry.world.meta.Stat;

import static enigma.util.Consts.*;

public class PolymorphThermalGenerator extends ThermalGenerator {

	public PolymorphPowerStack produced;
	public PolymorphThermalGenerator(String name) {
		super(name);
	}

	@Override
	public void setStats() {
		super.setStats();

		stats.remove(Stat.basePowerGeneration);
		stats.add(EniStats.polymorphProduction, EniStatVal.power(produced, true));
	}

	@Override
	public void setBars(){
		super.setBars();

		removeBar("power");
		addBar("power", makePowerBalance());
	}

	public static Func<Building, Bar> makePowerBalance(){
		return entity -> new Bar(() ->
				Core.bundle.format(
						"bar.polymorphprov",
						((PolymorphThermalBuild)entity).getProvided(((PolymorphThermalGenerator)entity.block).produced.type) * 60,
						(((PolymorphThermalGenerator)entity.block).produced.type != null ? ((PolymorphThermalGenerator)entity.block).produced.type.localizedName : "N/A")
				),
				() -> ((PolymorphThermalGenerator)entity.block).produced.type != null ? ((PolymorphThermalGenerator)entity.block).produced.type.color : Color.gray,
				() -> Mathf.clamp(
						entity.efficiency()
				)
		);
	}

	public class PolymorphThermalBuild extends ThermalGeneratorBuild implements PolymorphUtilizer, PolymorphProvider {

		PolymorphSystem system;

		@Override
		public void draw() {
			if(system != null && DEBUG){
				Fx.rand.setSeed(system.ID);
				Draw.color(new Color(Fx.rand.nextInt() | 0x000000FF));
			}
			super.draw();

			if(DEBUG) Draw.reset();
		}

		@Override
		public void updateEfficiencyMultiplier() {
			super.updateEfficiencyMultiplier();
		}

		@Override
		public float getProvided(PolymorphPowerType type) {
			return type == produced.type ? (sum + attribute.env()) * produced.quantity : 0;
		}

		@Override
		public boolean providesType(PolymorphPowerType type) {
			return type == produced.type;
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
			return produced.type;
		}

		@Override
		public void addToSystem(PolymorphSystem s, Seq<Integer> scheduled) {
			setSystem(s);
		}
	}
}
