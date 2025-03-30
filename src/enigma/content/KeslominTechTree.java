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
				node(thermoelectricAdapter, Seq.with(new Objectives.OnSector(subductionBarrier), new Objectives.Research(perforationDrill)), () -> {
					node(evaporationDynamo, () -> {});
				});
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

			node(vacuumDrill, Seq.with(new Objectives.Research(geothermalCollector)), () -> {
				node(perforationDrill, Seq.with(new Objectives.Research(ruthenium)), () -> {});
			});

			node(incandescence, Seq.with(new Objectives.Research(thermoacousticSonar)), () -> {
				node(molybdenumWall, () -> {
					node(largeMolybdenumWall, () -> {
						node(gateway, Seq.with(new Objectives.Research(replicator)), () -> {});
					});
					node(rutheniumWall, () -> {
						node(largeRutheniumWall, () -> {});
					});
				});
			});

			node(differentialPump, () ->{
				node(enameledConduit, () -> {
					node(enameledRouter, () -> {});
					node(enameledJunction, () -> {
						node(enameledBridge, () -> {});
					});
				});

			});

			node(thermalCrystallizer, () -> {
				node(thermalDistiller, () -> {});
			});
			node(replicator, () -> {
				node(scald, with(), () -> {});
				node(needle, with(molybdenum, 100, ruthenium, 250), () -> {});
			});

			nodeProduce(molybdenum, () ->{
				nodeProduce(periclase, () ->{
					nodeProduce(irtran, () ->{
						nodeProduce(ruthenium, () ->{
							nodeProduce(fulgoriteFiber, () ->{});
							nodeProduce(caesium, () ->{});
							nodeProduce(causticAmmonia, () ->{
								nodeProduce(distilledAmmonia, () ->{
									nodeProduce(oxidane, () ->{});
									nodeProduce(nitrate, () ->{});
									nodeProduce(nitroxide, () ->{});
								});
								nodeProduce(deuteride, () ->{});
							});

						});

					});
				});
				nodeProduce(therma, () ->{
					nodeProduce(ion, () ->{
						nodeProduce(alkima, () ->{});
						nodeProduce(enigma, () ->{});
					});
					nodeProduce(lux, () ->{});
				});
			});

			node(interphase, () -> {
				node(scattershot, Seq.with(new Objectives.SectorComplete(interphase)), () -> {
					node(subductionBarrier, Seq.with(new Objectives.SectorComplete(scattershot)), () -> {

					});
				});
			});
		});
	}


}
