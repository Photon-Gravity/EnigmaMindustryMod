package enigma.custom.polymorph.interfaces;

import enigma.custom.polymorph.PolymorphPowerType;

public interface PolymorphConsumer {
	float getConsumed(PolymorphPowerType type);
	boolean consumesType(PolymorphPowerType type);
}
