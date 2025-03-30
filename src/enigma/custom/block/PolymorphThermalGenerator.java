package enigma.custom.block;

import arc.Core;
import arc.func.Func;
import arc.graphics.Color;
import enigma.custom.polymorph.*;
import mindustry.gen.Building;
import mindustry.ui.Bar;
import mindustry.world.blocks.power.ThermalGenerator;

import static enigma.util.Consts.s;

public class PolymorphThermalGenerator extends ThermalGenerator {

	public PolymorphPowerStack produced;
	public PolymorphThermalGenerator(String name) {
		super(name);
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
						"bar.polymorphprov", //very dense notation that amounts to nullproofing
						((PolymorphThermalBuild)entity).produced(((PolymorphThermalGenerator)entity.block).produced.type) * s,
						((PolymorphThermalGenerator)entity.block).produced.type.localizedName
				),
				() -> ((PolymorphThermalGenerator)entity.block).produced.type.color,
				() -> ((ThermalGeneratorBuild)entity).sum
		);
	}

	public class PolymorphThermalBuild extends ThermalGeneratorBuild implements IPolymorphUtilizer{

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
		public PolymorphModule getModule() {
			return module;
		}

		@Override
		public PolymorphPowerType enforced() {
			return produced.type;
		}

		@Override
		public float produced(PolymorphPowerType ofType) {
			return ofType == produced.type ? produced.quantity * sum * efficiency : 0;
		}

		@Override
		public float consumed(PolymorphPowerType ofType) {
			return 0;
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
