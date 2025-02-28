package enigma.content;

import arc.struct.ObjectFloatMap;
import arc.struct.Seq;
import mindustry.game.Objectives;
import mindustry.type.Item;

import static enigma.content.EniBlocks.*;
import static enigma.content.EniItems.*;
import static enigma.content.EniLiquids.*;
import static enigma.content.EniPolymorphTypes.*;
import static enigma.content.EniSectors.*;
import static enigma.content.EniUnits.*;
import static mindustry.Vars.content;
import static mindustry.content.TechTree.*;
import static mindustry.type.ItemStack.with;

public class KeslominTechTree {

	public static void load(){

		var costMultipliers = new ObjectFloatMap<Item>();
		for(var item : content.items()) costMultipliers.put(item, 1f);


		Seq<Objectives.Objective> keslominSector = Seq.with(new Objectives.OnPlanet(EniPlanets.keslomin));

		EniPlanets.keslomin.techTree = nodeRoot("@planet.enigma-keslomin.name", EniBlocks.coreModule, true, () -> {
			context().researchCostMultipliers = costMultipliers;

			node(geothermalCollector, keslominSector, () -> {
				node(polymorphNode, () -> {
					node(thermoacousticSonar, () -> {});
					node(polymorphEnforcer, () -> {});
				});
			});

			node(encasedConveyor, Seq.with(new Objectives.Research(vacuumDrill)), () -> {
				node(axisGate, () -> {});
				node(filter, () -> {});
				node(thermobaricLauncher, () -> {});
			});

			node(vacuumDrill, Seq.with(new Objectives.Research(geothermalCollector)), () -> {});

			node(incandescence, Seq.with(new Objectives.Research(thermoacousticSonar)), () -> {
				node(molybdenumWall, () -> {
					node(largeMolybdenumWall, () -> {
						node(gateway, Seq.with(new Objectives.Research(replicator)), () -> {});
					});
				});
			});

			node(thermalCrystallizer, () -> {});
			node(replicator, () -> {
				node(scald, with(), () -> {});
			});

			nodeProduce(molybdenum, () ->{
				nodeProduce(periclase, () ->{
					nodeProduce(irtran, () ->{
						nodeProduce(ruthenium, () ->{
							nodeProduce(fulgoriteFiber, () ->{});
							nodeProduce(caesium, () ->{});
							nodeProduce(causticAmmonia, () ->{
								nodeProduce(distilledAmmonia, () ->{
									nodeProduce(deuteride, () ->{});
								});
							});

						});

					});
				});
				nodeProduce(therma, () ->{
					nodeProduce(ion, () ->{});
				});
			});

			node(interphase, () -> {
				node(scattershot, Seq.with(new Objectives.SectorComplete(interphase)), () -> {});
			});
		});
	}


}
