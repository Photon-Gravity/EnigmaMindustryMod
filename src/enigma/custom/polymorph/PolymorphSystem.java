package enigma.custom.polymorph;

import arc.struct.Seq;
import enigma.custom.polymorph.interfaces.PolymorphConsumer;
import enigma.custom.polymorph.interfaces.PolymorphProvider;
import enigma.custom.polymorph.interfaces.PolymorphStorage;
import enigma.custom.polymorph.interfaces.PolymorphUtilizer;
import mindustry.Vars;
import mindustry.gen.Building;

import java.util.ArrayList;

public class PolymorphSystem {
	public Seq<Integer> providers = new Seq<>();
	public Seq<Integer> storage = new Seq<>();
	public Seq<Integer> consumers = new Seq<>();
	public Seq<Integer> all = new Seq<>();
	public PolymorphPowerType type;

	public int ID;

	public PolymorphSystem(int ID){
		this.ID = ID;
	}
	//updating
	public void delete(){
		for(int b : all){
			if(Vars.world.build(b) instanceof PolymorphUtilizer u) u.setSystem(null);
			else all.remove((Integer)b);
		}
	}

	public void update(){
		if(type == null) {
			determineType();
		}

		if(type != null){
			distributePower(effectiveDelta(), type);
			if(!type.unlocked() && provided() > 0) type.unlock();
		}
	}

	public void determineType(){
		for(int b : all){
			if(Vars.world.build(b) instanceof PolymorphUtilizer u) {
				if(u.getEnforcedPowerType() != null) {
					type = u.getEnforcedPowerType();
					break;
				}
			} else all.remove((Integer)b);
		}
	}

	public void distributePower(float power, PolymorphPowerType t){
		for(int b: storage){
			if(Vars.world.build(b) instanceof PolymorphStorage u) {
				float powerToStore = power > 0 ? Math.min(u.getStorable(t) - u.getStored(t), power) : Math.max(u.getStored(t), power);

				power -= powerToStore;
				u.storePolymorph(powerToStore, t);
			}
			else all.remove((Integer)b);
		}
	}

	public void append(Building b){
		if(b instanceof PolymorphUtilizer){
			all.add(b.pos());
			if(b instanceof PolymorphProvider) providers.add(b.pos());
			if(b instanceof PolymorphStorage) storage.add(b.pos());
			if(b instanceof PolymorphConsumer) consumers.add(b.pos());
		}
	}

	//maths
	public float provided(){
		float totalProvided = 0;


		for(int b : providers){
			if(Vars.world.build(b) instanceof PolymorphProvider u){
				totalProvided += u.getProvided(type);
			} else all.remove((Integer)b);
		}
		return totalProvided;
	}

	public float stored(){
		float totalStored = 0;
		for(int b : storage){
			if(Vars.world.build(b) instanceof PolymorphStorage u){
				totalStored += u.getStored(type);
			} else all.remove((Integer)b);
		}
		return totalStored;
	}

	public float storable(){
		float totalStorable = 0;
		for(int b : storage){
			if(Vars.world.build(b) instanceof PolymorphStorage u){
				totalStorable += u.getStorable(type);
			} else all.remove((Integer)b);

		}
		return totalStorable;
	}

	public float consumed(){
		float totalConsumed = 0;
		for(int b : consumers){
			if(Vars.world.build(b) instanceof PolymorphConsumer u){
				totalConsumed += u.getConsumed(type);
			} else all.remove((Integer)b);
		}
		return totalConsumed;
	}

	public float satisfaction(){
		return Math.min(Math.max(provided()/(consumed() + stored()), 0), 1f);
	}
	public float balance(){
		return provided() - consumed();
	}

	public float delta() {
		return provided() - consumed();
	}

	public float effectiveDelta(){
		return delta() > 0 ? Math.min(delta(), storable() - stored()) : Math.max(delta(), stored());
	}

	public boolean contains(int pos){
		return all.contains(pos);
	}
}
