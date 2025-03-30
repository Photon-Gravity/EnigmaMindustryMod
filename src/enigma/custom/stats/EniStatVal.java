package enigma.custom.stats;

import arc.struct.Seq;
import enigma.custom.polymorph.PolymorphPowerStack;
import mindustry.type.LiquidStack;
import mindustry.ui.LiquidDisplay;
import mindustry.world.meta.StatValue;

public class EniStatVal {

	public static StatValue power(PolymorphPowerStack stack, boolean perSecond){
		return table -> table.add(new PolymorphDisplay(stack, perSecond));
	}

	public static StatValue powerTypes(boolean perSecond, PolymorphPowerStack... stacks){
		return table -> {
			for(var stack : stacks){
				table.add(new PolymorphDisplay(stack, perSecond)).padRight(5);
			}
		};
	}

	public static StatValue powerTypes(boolean perSecond, Seq<PolymorphPowerStack> stacks){
		return table -> {
			for(var stack : stacks){
				table.add(new PolymorphDisplay(stack, perSecond)).padRight(5);
			}
		};
	}
}
