package enigma.custom.block;

import arc.Core;
import arc.func.Func;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.struct.Seq;
import enigma.custom.polymorph.PolymorphPowerStack;
import enigma.custom.polymorph.PolymorphPowerType;
import enigma.custom.polymorph.PolymorphSystem;
import enigma.custom.polymorph.interfaces.PolymorphConsumer;
import enigma.custom.polymorph.interfaces.PolymorphUtilizer;
import enigma.custom.stats.EniStatVal;
import enigma.custom.stats.EniStats;
import mindustry.gen.Building;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.ItemTurret;

public class ItemPolymorphTurret extends ItemTurret {
	public PolymorphPowerStack consumed;

	public ItemPolymorphTurret(String name) {
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
						((PolymorphUtilizer)entity).getSystem() != null ? (((PolymorphUtilizer)entity).getSystem().type != null ? ((PolymorphUtilizer)entity).getSystem().type.localizedName : "N/A") : "N/A"
				),
				() -> ((PolymorphUtilizer)entity).getSystem() != null ? (((PolymorphUtilizer)entity).getSystem().type != null ? ((PolymorphUtilizer)entity).getSystem().type.color : Color.gray) : Color.gray,
				() -> Mathf.clamp(
						((PolymorphUtilizer)entity).getSystem() != null ? ((PolymorphUtilizer)entity).getSystem().satisfaction() : 0
				)
		);
	}

	public class ItemPolymorphTurretBuild extends ItemTurretBuild implements PolymorphUtilizer, PolymorphConsumer {

		PolymorphSystem system;
		@Override
		public void updateEfficiencyMultiplier() {
			super.updateEfficiencyMultiplier();
			efficiency *= system != null ? system.satisfaction() : 0;
		}

		@Override
		public float getConsumed(PolymorphPowerType type) {
			return consumed.quantity * warmup();
		}

		@Override
		public boolean consumesType(PolymorphPowerType type) {
			return type == consumed.type;
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
			return consumed.type;
		}

		@Override
		public void addToSystem(PolymorphSystem s, Seq<Integer> scheduled) {
			setSystem(s);
		}
	}
}
