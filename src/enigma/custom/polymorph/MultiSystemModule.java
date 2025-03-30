package enigma.custom.polymorph;

import arc.struct.Seq;
import mindustry.Vars;

public class MultiSystemModule extends PolymorphModule{
	public Seq<Integer> links;

	private Seq<PolymorphSystem> systems;

	public int pos;

	public MultiSystemModule(int pos){
		super(pos);
		this.systems = new Seq<>();
		links = new Seq<>();
		this.pos = pos;
	}

	@Override
	public void validate(){
		for(int link : links){
			if(!(Vars.world.build(link) instanceof IPolymorphUtilizer)){
				unlinkFrom(link);
			}
		}

		for(PolymorphSystem system : systems){
			boolean onlyThis = true;
			for(int member : system.members){
				if(member != pos){
					onlyThis = false;
					break;
				}
			}
			if(onlyThis){
				system.delete();
			}
		}
	}

	@Override
	public void linkTo(PolymorphModule other){
		links.add(other.pos);
		other.links.add(pos);



		if(!inSystem(other.firstSystem())){ //this ONLY works because multi-system modules don't connect to each other directly and this is why they CANNOT under ANY CIRCUMSTANCES.
			addToSystem(other.firstSystem());
		}
	}

	@Override
	public void unlinkFrom(int otherPos){
		if(links.contains(otherPos)){
			links.remove((Integer)otherPos);
		}

		if(Vars.world.build(otherPos) instanceof IPolymorphUtilizer other && other.getModule().links.contains(pos)){
			other.getModule().links.remove((Integer)pos);

			for(int link : links){
				PolymorphSystem sys = Vars.world.build(link) instanceof IPolymorphUtilizer util ? util.getModule().firstSystem() : null;

				if(sys != null){
					sys.delete();
					PolymorphUpdater.makeSystem(link);
				}
			}

			if(!other.getModule().inSystem()) {
				PolymorphUpdater.makeSystem(otherPos);
			}
		} else {
			for(int link : links){
				PolymorphSystem sys = Vars.world.build(link) instanceof IPolymorphUtilizer util ? util.getModule().firstSystem() : null;

				if(sys != null){
					sys.delete();
					PolymorphUpdater.makeSystem(link);
				}
			}
		}
	}

	/**Adds the block to the specified system. WARNING: This does NOT update the links.*/
	@Override
	public void addToSystem(PolymorphSystem nsys){
		if(!inSystem(nsys)){
			systems.addUnique(nsys);
			nsys.members.addUnique(pos);
		}
	}

	@Override
	public boolean inSystem(PolymorphSystem csys){
		return systems.contains(csys);
	}

	@Override
	public PolymorphSystem firstSystemFor(PolymorphPowerType type){
		for(PolymorphSystem sys : systems){
			if(sys.enforcedType == type) return sys;
		}
		return null;
	}

	@Override
	public PolymorphSystem firstSystem() {
		return systems.first();
	}

	@Override
	public boolean inSystem(){
		return systems.size > 0;
	}
	/**Removes the block from the specified system. WARNING: This does NOT update the system in any way, or the links.*/
	@Override
	public void removeFromSystem(PolymorphSystem rsys){
		if(systems.contains(rsys)) systems.remove(rsys);
	}

	@Override
	public int systemsWithPowerType(PolymorphPowerType type){
		int sum = 0;
		for(PolymorphSystem sys : systems){
			if(sys.enforcedType == type) sum++;
		}
		return sum;
	}

	@Override
	public Seq<Integer> getLinkedBlocks(){
		return new Seq<>();
	}

	@Override
	public PolymorphPowerType getEnforced(){
		return null;
	}

	@Override
	public float satisfaction(PolymorphPowerType type){
		int count = 0;
		float sum = 0;

		for(PolymorphSystem sys : systems){
			if(sys.enforcedType == type){
				sum += sys.satisfaction();
				count++;
			}
		}

		return count != 0 ? sum/count : 0;
	}

	@Override
	public boolean canConnect(PolymorphModule module){
		return module != null;
	}
}
