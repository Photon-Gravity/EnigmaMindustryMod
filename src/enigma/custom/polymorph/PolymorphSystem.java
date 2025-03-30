package enigma.custom.polymorph;

import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;

public class PolymorphSystem {

	Seq<Integer> members;

	PolymorphPowerType enforcedType;

	static int lastID = 0;

	public int id;

	public PolymorphSystem(){
		members = new Seq<>();
		id = lastID++;
	}


	public void update(){
		enforcedType = getEnforced();

		if (Vars.state.isCampaign() && members != null && members.size > 0 && Vars.world.build(members.get(0)) != null && Vars.world.build(members.get(0)).team == Vars.state.rules.defaultTeam && enforcedType != null && !enforcedType.unlocked()) enforcedType.unlock();

		storePower(batteryDelta());

		validateMembers();

//		if(members.size == 0){
//			delete();
//		}
	}

	public void validateMembers(){
		for(int member : members){
			if(Vars.world.build(member) instanceof IPolymorphUtilizer util && util.getModule() != null){
				util.getModule().validate();
			} else {
				members.remove((Integer)member);
			}
		}
	}

	public void delete(){
		for(int member : members){
			if(Vars.world.build(member) instanceof IPolymorphUtilizer util){
				util.getModule().removeFromSystem(this);
			}
		}

		PolymorphUpdater.systems.remove(this);
	}
	public PolymorphPowerType getEnforced(){
		for(int member : members){
			if(Vars.world.build(member) instanceof IPolymorphUtilizer u && u.enforced() != null){
				return u.enforced();
			}
		}
		return null;
	}


	public float satisfaction(){
		return Math.min(Math.max((produced() + stored()) / (consumed() + 0.0001f), 0), 1);
	}
	public float balance(){
		return produced() - consumed();
	}

	public float produced(){
		float sum = 0;

		for(int member : members){
			if(Vars.world.build(member) instanceof IPolymorphUtilizer u){
				sum += u.produced(enforcedType);
			}
		}

		return sum;
	}

	public float consumed(){
		float sum = 0;

		for(int member : members){
			if(Vars.world.build(member) instanceof IPolymorphUtilizer u){
				sum += u.consumed(enforcedType);
			}
		}

		return sum;
	}

	public float storable(){
		float sum = 0;

		for(int member : members){
			if(Vars.world.build(member) instanceof IPolymorphUtilizer u){
				sum += u.storable(enforcedType);
			}
		}

		return sum;
	}

	public float stored(){
		float sum = 0;

		for(int member : members){
			if(Vars.world.build(member) instanceof IPolymorphUtilizer u){
				sum += u.stored(enforcedType);
			}
		}

		return sum;
	}
	public float batteryDelta(){
		return Math.min(Math.max(produced() - consumed(), 0), storable() - stored());
	}

	public void storePower(float power){

		for(int member : members){
			if(Vars.world.build(member) instanceof IPolymorphUtilizer u){
				if(power > 0){
					float deposited = Math.min(u.storable(enforcedType) - u.stored(enforcedType), power);

					power -= deposited;
					u.store(new PolymorphPowerStack(enforcedType, deposited));
					if(power == 0) break;
				} else {
					float deposited = Math.max(-u.stored(enforcedType), power);

					power -= deposited;
					u.store(new PolymorphPowerStack(enforcedType, deposited));
					if(power == 0) break;
				}
			}
		}
	}
}
