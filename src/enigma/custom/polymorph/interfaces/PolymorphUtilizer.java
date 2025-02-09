package enigma.custom.polymorph.interfaces;

import arc.struct.Seq;
import enigma.custom.polymorph.PolymorphPowerType;
import enigma.custom.polymorph.PolymorphSystem;

/**Interface that should be applied to any Building children that are supposed to contain polymorph power. Note that other interfaces are required on top of this one for storage, consumers and producers.*/
public interface PolymorphUtilizer {
	void setSystem(PolymorphSystem newSystem);
	PolymorphSystem getSystem();
	PolymorphPowerType getEnforcedPowerType();

	void addToSystem(PolymorphSystem s, Seq<Integer> scheduled);
}
