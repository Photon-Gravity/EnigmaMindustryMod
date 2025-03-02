package enigma.custom.block;

import arc.Core;
import arc.func.Func;
import arc.graphics.Color;
import arc.math.Mathf;
import enigma.custom.polymorph.*;
import enigma.custom.stats.EniStatVal;
import enigma.custom.stats.EniStats;
import mindustry.gen.Building;
import mindustry.ui.Bar;
import mindustry.world.blocks.distribution.MassDriver;

public class PolymorphMassDriver extends MassDriver {

	public PolymorphPowerStack consumed;

	public PolymorphMassDriver(String name) {
		super(name);
	}

	@Override
	public void setStats() {
		super.setStats();

		stats.add(EniStats.polymorphConsumption, EniStatVal.power(consumed, true));
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
						((IPolymorphUtilizer)entity).getModule() != null &&((IPolymorphUtilizer)entity).getModule().getEnforced() != null ? ((IPolymorphUtilizer)entity).getModule().getEnforced().localizedName : "N/A"
				),
				() -> ((IPolymorphUtilizer)entity).getModule() != null &&((IPolymorphUtilizer)entity).getModule().getEnforced() != null ? ((IPolymorphUtilizer)entity).getModule().getEnforced().color : Color.gray,
				() -> ((IPolymorphUtilizer)entity).getModule() != null ? ((IPolymorphUtilizer)entity).getModule().satisfaction() : 0
		);
	}

	public class PolymorphMassDriverBuild extends MassDriverBuild implements IPolymorphUtilizer{

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

			efficiency *= module != null ? module.satisfaction() : 0;
		}

		@Override
		public PolymorphModule getModule() {
			return module;
		}

		@Override
		public PolymorphPowerType enforced() {
			return consumed.type;
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
