package enigma.custom.block;

import arc.struct.Seq;
import enigma.custom.polymorph.PolymorphPowerType;
import enigma.custom.polymorph.PolymorphSystem;
import enigma.custom.polymorph.interfaces.PolymorphProvider;
import enigma.custom.polymorph.interfaces.PolymorphUtilizer;
import mindustry.gen.Building;
import mindustry.world.Block;

public class PolymorphSource extends Block {
	public PolymorphSource(String name) {
		super(name);
		update = true;
	}

	public class PolymorphSourceBuild extends Building implements PolymorphUtilizer, PolymorphProvider {
		PolymorphSystem system;
		@Override
		public float getProvided(PolymorphPowerType type) {
			return 1000000f;
		}

		@Override
		public boolean providesType(PolymorphPowerType type) {
			return true;
		}

		@Override
		public void setSystem(PolymorphSystem newSystem) {
			this.system = newSystem;
		}

		@Override
		public PolymorphSystem getSystem() {
			return system;
		}

		@Override
		public PolymorphPowerType getEnforcedPowerType() {
			return null;
		}

		@Override
		public void addToSystem(PolymorphSystem s, Seq<Integer> scheduled) {
			setSystem(s);
		}
	}
}
