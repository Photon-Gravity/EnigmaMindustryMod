package enigma.custom.block;

import arc.Core;
import arc.func.Func;
import arc.graphics.Color;
import arc.struct.Seq;
import enigma.custom.polymorph.*;
import enigma.custom.stats.EniStatVal;
import enigma.custom.stats.EniStats;
import mindustry.gen.Building;
import mindustry.ui.Bar;
import mindustry.world.blocks.production.GenericCrafter;

import static enigma.util.Consts.s;

public class MultiPolymorphCrafter extends GenericCrafter {

	public Seq<PolymorphPowerStack> consumed = new Seq<>(), produced = new Seq<>();

	public MultiPolymorphCrafter(String name) {
		super(name);
	}

	@Override
	public void setStats() {
		super.setStats();

		if(consumed.size > 0){
			stats.add(EniStats.polymorphConsumption, EniStatVal.powerTypes(true, consumed));
		}
		if(produced.size > 0){
			stats.add(EniStats.polymorphProduction, EniStatVal.powerTypes(true, produced));
		}
	}

	@Override
	public void setBars(){
		super.setBars();
		for(PolymorphPowerStack stack : consumed){
			addBar("power-consume-"+stack.type.name, makePowerBalanceFor(stack.type));
		}
	}
	public static Func<Building, Bar> makePowerBalanceFor(PolymorphPowerType type){
		return entity -> new Bar(() ->
				Core.bundle.format(
						"bar.polymorphcons", //very dense notation that amounts to nullproofing
						((IPolymorphUtilizer)entity).getModule() != null && type != null ? type.localizedName : "N/A"
				),
				() -> ((IPolymorphUtilizer)entity).getModule() != null && type != null ? type.color : Color.gray,
				() -> ((IPolymorphUtilizer)entity).getModule() != null && ((IPolymorphUtilizer)entity).getModule().inSystem() ? ((IPolymorphUtilizer)entity).getModule().satisfaction(type) : 0
		);
	}

	public static Func<Building, Bar> makePowerProductionFor(PolymorphPowerType type){
		return entity -> new Bar(() ->
				Core.bundle.format(
						"bar.polymorphprov", //very dense notation that amounts to nullproofing
						((MultiPolymorphCrafterBuild)entity).produced(type)/s,
						type.localizedName
				),
				() -> type.color,
				() -> entity.efficiency
		);
	}

	public class MultiPolymorphCrafterBuild extends GenericCrafterBuild implements IPolymorphUtilizer{

		MultiSystemModule module;

		@Override
		public void update() {
			super.update();

			if(module == null) {
				module = new MultiSystemModule(pos());
			}

			if(efficiency > 0.001f){
				for(PolymorphPowerStack stack : produced){
					stack.type.unlock();
				}
			}
		}

		@Override
		public void updateEfficiencyMultiplier() {
			super.updateEfficiencyMultiplier();

			for(PolymorphPowerStack cons : consumed){
				efficiency *= module != null ? module.satisfaction(cons.type) : 0;
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
			return module != null && efficiency > 0 && produced.contains(e -> e.type == ofType) ? produced.find(e -> e.type == ofType).quantity * efficiency() / module.systemsWithPowerType(ofType) : 0;
		}

		@Override
		public float consumed(PolymorphPowerType ofType) {
			return module != null && consumed.contains(e -> e.type == ofType) ? consumed.find(e -> e.type == ofType).quantity / module.systemsWithPowerType(ofType) : 0;
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
