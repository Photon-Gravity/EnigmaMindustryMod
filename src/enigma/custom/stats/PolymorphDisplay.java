package enigma.custom.stats;

import arc.graphics.Color;
import arc.scene.Element;
import arc.scene.ui.Image;
import arc.scene.ui.layout.Stack;
import arc.scene.ui.layout.Table;
import arc.util.Scaling;
import arc.util.Strings;
import enigma.custom.polymorph.PolymorphPowerStack;
import mindustry.type.Liquid;
import mindustry.ui.Styles;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.iconMed;

public class PolymorphDisplay extends Table {
	public final PolymorphPowerStack stack;
	public final boolean perSecond;

	public PolymorphDisplay(PolymorphPowerStack stack, boolean perSecond){
		this.stack = stack;
		this.perSecond = perSecond;

		add(new Stack(){{
			add(new Image(stack.type.uiIcon).setScaling(Scaling.fit));
		}}).size(iconMed).padRight(3  + (stack.quantity != 0 && Strings.autoFixed(stack.quantity, 2).length() > 2 ? 8 : 0));

		add((stack.quantity != 0 ? Strings.autoFixed(stack.quantity * 60, 2) + " " : "") + stack.type.localizedName);

		if(perSecond){
			add(StatUnit.perSecond.localized()).padLeft(2).padRight(5).color(Color.lightGray).style(Styles.outlineLabel);
		}
	}
}
