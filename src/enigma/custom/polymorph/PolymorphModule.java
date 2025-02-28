package enigma.custom.polymorph;

import arc.struct.Seq;
import mindustry.Vars;

public class PolymorphModule {
	public Seq<Integer> links;

	public PolymorphSystem system;

	public int pos;

	public PolymorphModule(int pos){
		links = new Seq<>();
		this.pos = pos;
	}

	public void validate(){
		for(int link : links){
			if(!(Vars.world.build(link) instanceof IPolymorphUtilizer)){
				unlinkFrom(link);
			}
		}
	}

	public void linkTo(PolymorphModule other){
		links.add(other.pos);
		other.links.add(pos);

		if(other.system != system){
			PolymorphUpdater.merge(system, other.system);
		}
	}

	public void unlinkFrom(int otherPos){
		if(links.contains(otherPos)){
			links.remove((Integer)otherPos);
		}

		if(Vars.world.build(otherPos) instanceof IPolymorphUtilizer other && other.getModule().links.contains(pos)){
			other.getModule().links.remove((Integer)pos);

			system.delete();

			PolymorphUpdater.makeSystem(pos);

			if(other.getModule().system != system) {
				PolymorphUpdater.makeSystem(otherPos);
			}
		} else {

			if(system != null )system.delete();

			PolymorphUpdater.makeSystem(pos);
		}
	}

	public PolymorphPowerType getEnforced(){
		return system != null ? system.enforcedType : null;
	}

	public float satisfaction(){
		return system != null ? system.satisfaction() : 0;
	}

	public boolean canConnect(PolymorphModule module){


//		if(module != null) {
//			Log.info(Vars.world.build(pos).block.name + " - " +Vars.world.build(module.pos).block.name + ": " + (module.getEnforced() == getEnforced() || getEnforced() == null || module.getEnforced() == null));
//		}
//		else Log.info("other null");

		return module != null && system != null && system.members.size > 0 && (module.getEnforced() == getEnforced() || getEnforced() == null || module.getEnforced() == null);
	}
}
