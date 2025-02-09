package enigma.custom.polymorph.interfaces;

import enigma.custom.polymorph.PolymorphPowerType;

public interface PolymorphProvider {
	float getProvided(PolymorphPowerType type);
	boolean providesType(PolymorphPowerType type);
}
