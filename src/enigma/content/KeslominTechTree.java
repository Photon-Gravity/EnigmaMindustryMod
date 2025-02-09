package enigma.content;

import arc.struct.ObjectFloatMap;
import arc.struct.Seq;
import mindustry.game.Objectives;
import mindustry.type.Item;

import static enigma.content.EniBlocks.*;
import static enigma.content.EniItems.*;
import static enigma.content.EniPolymorphTypes.*;
import static mindustry.Vars.content;
import static mindustry.content.TechTree.*;

public class KeslominTechTree {

	public static void load(){

		var costMultipliers = new ObjectFloatMap<Item>();
		for(var item : content.items()) costMultipliers.put(item, 0.9f);


		Seq<Objectives.Objective> keslominSector = Seq.with(new Objectives.OnPlanet(EniPlanets.keslomin));

		EniPlanets.keslomin.techTree = nodeRoot("@planet.enigma-keslomin.name", EniBlocks.coreModule, true, () -> {
			context().researchCostMultipliers = costMultipliers;

			node(geothermalCollector, keslominSector, () -> {
				node(polymorphNode, () -> {
					node(thermoacousticSonar, () -> {});
					node(polymorphEnforcer, () -> {});
				});
			});

			node(encasedConveyor, Seq.with(new Objectives.Research(suctionDrill)), () -> {
				node(axisGate, () -> {});
			});

			node(suctionDrill, Seq.with(new Objectives.Research(geothermalCollector)), () -> {});

			node(incandescence, Seq.with(new Objectives.Research(thermoacousticSonar)), () -> {
				node(molybdenumWall, () -> {
					node(largeMolybdenumWall, () -> {});
				});
			});

			node(thermalCrystallizer, () -> {});

			nodeProduce(molybdenum, () ->{
				nodeProduce(periclase, () ->{
					nodeProduce(irtran, () ->{});
				});
				nodeProduce(therma, () ->{});
			});
		});
	}


}
