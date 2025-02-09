package enigma.custom.polymorph.interfaces;

import enigma.custom.polymorph.PolymorphPowerType;
import enigma.custom.polymorph.PolymorphSystem;

public interface PolymorphStorage {
	float getStored(PolymorphPowerType type);
	float getStorable(PolymorphPowerType type);
	boolean storesType(PolymorphPowerType type);
	void storePolymorph(float quantity, PolymorphPowerType type);
}
