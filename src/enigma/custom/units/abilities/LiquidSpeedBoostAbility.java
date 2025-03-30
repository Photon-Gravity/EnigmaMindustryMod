package enigma.custom.units.abilities;

import arc.Core;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;
import mindustry.world.meta.Stat;


public class LiquidSpeedBoostAbility extends Ability {
	float speedMult = 2;

	@Override
	public void update(Unit unit) {
		super.update(unit);

		if (Vars.world.floor((int) (unit.x / 8), (int) (unit.y() / 8)).isLiquid) {
			unit.speedMultiplier *= speedMult;
		}
	}

	@Override
	public void addStats(Table t) {
		t.add("[lightgray]" + Stat.speedMultiplier.localized() + ": [white]" + Math.round(speedMult * 100f) + "%");
	}

}
