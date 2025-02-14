package enigma.custom.polymorph;

import arc.Events;
import arc.struct.Seq;
import arc.util.Log;
import enigma.custom.block.PolymorphNode;
import enigma.custom.polymorph.interfaces.PolymorphUtilizer;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Building;

import java.util.ArrayList;

public class PolymorphSystemUpdater {
	public static ArrayList<PolymorphSystem> systems = new ArrayList<>();

	public static void init(){
		Events.run(EventType.Trigger.update, () -> {
			//update all systems every update
			for(PolymorphSystem system : systems){
				system.update();
			}
		});

		Events.on(EventType.WorldLoadEvent.class, event -> {
			//Because the systems don't get saved to a block, we have to recalculate all of them when the map is loaded. The calculation is too cumbersome to carry out eachframe, but once upon map load is just fine.
			recalculateSystems();
		});

		Events.on(EventType.BlockBuildEndEvent.class, event -> {
			//too lazy to check everything properly when a block is destroyed so I just ditch the block's system and recalculate everything. It's not like this is done each frame, although when breaking a lot of nodes this could get problematic.
			if(event.tile.build instanceof PolymorphUtilizer util && util.getSystem() != null){
				util.getSystem().delete();

				if(event.tile.build instanceof PolymorphNode.PolymorphNodeBuild n){
					for(int link : n.links){
						if(Vars.world.build(link) instanceof PolymorphNode.PolymorphNodeBuild m){
							m.links.remove((Integer)n.pos());
						}
					}
					n.links.clear();
				}
			}
			recalculateSystems();
		});
	}

	public static void recalculateSystems(){
		for (PolymorphSystem s: systems) {
			s.delete();
		}
		//search through every tile and if it has polymorph and its system is null it gets its own system, which is fully propagated though the network. Most of it is redundancy to make sure there aren't wierd polymorph warp glitches.
		Vars.world.tiles.eachTile(tile -> {
			if(tile.build instanceof PolymorphNode.PolymorphNodeBuild u && u.getSystem() == null){
				propagateNewSystem(tile.pos());
			}
		});
	}

	public static void propagateNewSystem(int origin){
		PolymorphSystem sys = createSystem();

		propagateSystem(origin, sys);
	}

	public static void propagateSystem(int origin, PolymorphSystem system){
		Seq<Integer> scheduled = new Seq<>();
		scheduled.add(origin);

		//slowly churns though the scheduled blocks, adding them to the system and passing the scheduled array in to query the blocks on what else should be scheduled. Required because of power node's branching behaviour. Preventing endless loops is the node's responsibility.
		while(!scheduled.isEmpty()){
			Building b = Vars.world.build(scheduled.first());

			if(b instanceof PolymorphUtilizer u){
				u.addToSystem(system, scheduled);
				system.append(b);
			}

			scheduled.remove(0);
		}
	}

	public static PolymorphSystem createSystem(){
		PolymorphSystem sys = new PolymorphSystem(systems.size());
		systems.add(sys);
		return sys;
	}
}
