package enigma.custom.block;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.meta.BlockGroup;

import static enigma.util.Consts.s;

public class AxisGate extends Block {

	public float transferDelay = 1/14f*s;

	TextureRegion topRegion;
	public AxisGate(String name) {
		super(name);
		update = true;
		rotate = true;
		hasItems = true;
		itemCapacity = 1;

		rotateDraw = false;
		drawArrow = false;
		group = BlockGroup.transportation;
	}

	@Override
	public void load() {
		super.load();

		topRegion = Core.atlas.find(name + "-top");
	}

	@Override
	protected TextureRegion[] icons() {
		return new TextureRegion[]{region, topRegion};
	}

	@Override
	public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
		Draw.rect(region, plan.x*8, plan.y*8);
		Draw.rect(topRegion, plan.x * 8, plan.y * 8, (plan.rotation & 1) * 90);
	}

	@Override
	public boolean rotatedOutput(int x, int y){
		return false;
	}

	public class AxisGateBuild extends Building {

		int transferCounter = 0;
		float progress = 0;

		@Override
		public void update() {
			super.update();

			if(progress > transferDelay && items.any()){
				boolean sw = ((transferCounter & 1) == 0);

				Building target = (nearby((rotation + (sw ? 2 : 0)) % 4) != null && nearby((rotation + (sw ? 2 : 0)) % 4).acceptItem(this, items.first())) ? nearby((rotation + (sw ? 2 : 0)) % 4) :
						((nearby((rotation + (sw ? 0 : 2)) % 4) != null && nearby((rotation + (sw ? 0 : 2)) % 4).acceptItem(this, items.first())) ? nearby((rotation + (sw ? 0 : 2)) % 4) :
						((nearby((rotation + (sw ? 1 : 3)) % 4) != null && nearby((rotation + (sw ? 1 : 3)) % 4).acceptItem(this, items.first())) ? nearby((rotation + (sw ? 1 : 3)) % 4) :
						((nearby((rotation + (sw ? 3 : 1)) % 4) != null && nearby((rotation + (sw ? 3 : 1)) % 4).acceptItem(this, items.first())) ? nearby((rotation + (sw ? 3 : 1)) % 4) : null)));

				if(target != null){
					target.handleItem(this, items.first());
					items.remove(items.first(), 1);
					progress = 0;
					transferCounter++;
				}
			} else {
				progress += delta();
			}
		}

		@Override
		public void draw() {
			super.draw();
			Draw.rect(topRegion, x, y, (rotation & 1) * 90);
		}

		@Override
		public boolean acceptItem(Building source, Item item) {
			return items.total() < itemCapacity;
		}
	}
}
