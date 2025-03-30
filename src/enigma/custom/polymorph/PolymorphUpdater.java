package enigma.custom.polymorph;

import arc.Events;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.EventType;

public class PolymorphUpdater {

	public static Seq<PolymorphSystem> systems;

	public static void init(){
		systems = new Seq<>();

		Events.run(EventType.Trigger.update, () -> {
			//update all systems every update
			for(PolymorphSystem system : systems){
				system.update();
			}
		});
	}

	public static void merge(PolymorphSystem systemA, PolymorphSystem systemB){

		int root = systemA.members.get(0);

		systemA.delete();
		systemB.delete();

		makeSystem(root);
	}

	public static void makeSystem(int pos){
		PolymorphSystem system = new PolymorphSystem();

		systems.add(system);

		Seq<Integer> schedule = new Seq<>();
		schedule.add(pos);

		while(!schedule.isEmpty()){
			int t = schedule.get(0);
			if (Vars.world.build(t) instanceof IPolymorphUtilizer util && !util.getModule().inSystem(system)){
				util.getModule().addToSystem(system);
				schedule.addAll(util.getModule().getLinkedBlocks());
				system.members.addUnique(t);
			}
			schedule.remove(0);
		}
	}
}