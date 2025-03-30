package enigma.custom.polymorph;

import arc.struct.Seq;
import mindustry.Vars;

public class PolymorphModule {
	public Seq<Integer> links;

	private PolymorphSystem system;

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

		if(!other.inSystem(system)){
			other.addToSystem(system);
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

			if(!other.getModule().inSystem(system)) {
				PolymorphUpdater.makeSystem(otherPos);
			}
		} else {

			if(system != null )system.delete();

			PolymorphUpdater.makeSystem(pos);
		}
	}

	/**Adds the block to the specified system. WARNING: This does NOT update the links.*/
	public void addToSystem(PolymorphSystem nsys){
		if(this.system == null){
			this.system = nsys;
		} else {
			PolymorphUpdater.merge(system, nsys);
		}
	}

	public boolean inSystem(PolymorphSystem csys){
		return this.system == csys;
	}

	public PolymorphSystem firstSystemFor(PolymorphPowerType type){
		if(type == system.enforcedType){
			return system;
		}
		return null;
	}

	public PolymorphSystem firstSystem(){
		return system;
	}
	public boolean inSystem(){
		return this.system != null;
	}
	/**Removes the block from the specified system. WARNING: This does NOT update the system in any way, or the links.*/
	public void removeFromSystem(PolymorphSystem rsys){
		if(this.system == rsys) this.system = null;
	}
	public int systemsWithPowerType(PolymorphPowerType type){
		return system.enforcedType == type ? 1 : 0;
	}

	public Seq<Integer> getLinkedBlocks(){
		return links;
	}

	public PolymorphPowerType getEnforced(){
		return system != null ? system.enforcedType : null;
	}

	public float satisfaction(PolymorphPowerType type){
		return system != null && system.enforcedType == type && type != null ? system.satisfaction() : 0;
	}

	public boolean canConnect(PolymorphModule module){


//		if(module != null) {
//			Log.info(Vars.world.build(pos).block.name + " - " +Vars.world.build(module.pos).block.name + ": " + (module.getEnforced() == getEnforced() || getEnforced() == null || module.getEnforced() == null));
//		}
//		else Log.info("other null");

		return module != null && system != null && system.members.size > 0 && (module.getEnforced() == getEnforced() || getEnforced() == null || module.getEnforced() == null);
	}
}
