package enigma.custom.polymorph;

public interface IPolymorphUtilizer {
	PolymorphModule getModule();

	PolymorphPowerType enforced();
	float produced(PolymorphPowerType ofType);

	float consumed(PolymorphPowerType ofType);

	float storable(PolymorphPowerType ofType);

	float stored(PolymorphPowerType ofType);

	void store(PolymorphPowerStack stack);
}
