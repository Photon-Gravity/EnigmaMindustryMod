package enigma.custom.block;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.util.Eachable;
import mindustry.Vars;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.blocks.distribution.Conveyor;

public class ShadedConveyor extends Conveyor {
	public TextureRegion[][] overlay;
	public TextureRegion cap0, cap1;
	public ShadedConveyor(String name) {
		super(name);
	}

	@Override
	public void load() {
		super.load();
		overlay = new TextureRegion[5][4];
		for(int i=0;i < 4;i++){
			for(int j=0;j < 4;j++) {
				overlay[i][j] = Core.atlas.find(name + "-overlay-" + i + "-" + j);
			}
		}
		cap0 = Core.atlas.find(name+"-cap-0");
		cap1 = Core.atlas.find(name+"-cap-1");

	}

	@Override
	public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
		super.drawPlanRegion(plan, list);
		int[] bits = plan.x >= 0 && plan.y >= 0 && plan.x <= Vars.world.width() && plan.y <= Vars.world.height() ? buildBlending(Vars.world.tile(plan.x, plan.y), plan.rotation, null, true) : new int[]{0, 0, 0, 0};
		int blendbits = bits[0];
		int blendsclx = bits[1];
		int blendscly = bits[2];

		boolean rotateCorner = blendbits == 1 && (blendsclx * blendscly == -1);
		boolean rotateYMerge = blendbits == 2 && (blendsclx * blendscly == -1);
		boolean rotateTMerge = blendbits == 4;

		Draw.rect(overlay[blendbits == 4 ? 2 : blendbits][(plan.rotation
				- (rotateCorner ? 1 : 0)
				- (rotateYMerge ? 2 : 0)
				- (blendbits == 2  || blendbits == 4 ? 1 : 0)
				+ (rotateTMerge ? 1 : 0) + 256) % 4], plan.x*8, plan.y*8);

	}

	@Override
	public void drawPlace(int x, int y, int rotation, boolean valid) {
		super.drawPlace(x, y, rotation, valid);

		Draw.color();
		int[] bits = x >= 0 && y >= 0 ? buildBlending(Vars.world.tile(x, y), rotation, null, true) : new int[]{0, 0, 0, 0};
		int blendbits = bits[0];
		int blendsclx = bits[1];
		int blendscly = bits[2];

		boolean rotateCorner = blendbits == 1 && (blendsclx * blendscly == -1);
		boolean rotateYMerge = blendbits == 2 && (blendsclx * blendscly == -1);
		boolean rotateTMerge = blendbits == 4;

		Draw.rect(overlay[blendbits == 4 ? 2 : blendbits][(rotation
				- (rotateCorner ? 1 : 0)
				- (rotateYMerge ? 2 : 0)
				- (blendbits == 2  || blendbits == 4 ? 1 : 0)
				+ (rotateTMerge ? 1 : 0) + 256) % 4], x*8, y*8);
	}

	public class ShadedConveyorBuild extends ConveyorBuild {
		@Override
		public void draw() {
			super.draw();

			boolean rotateCorner = blendbits == 1 && (blendsclx * blendscly == -1);
			boolean rotateYMerge = blendbits == 2 && (blendsclx * blendscly == -1);
			boolean rotateTMerge = blendbits == 4;

			Draw.rect(overlay[blendbits == 4 ? 2 : blendbits][(rotation
					- (rotateCorner ? 1 : 0)
					- (rotateYMerge ? 2 : 0)
					- (blendbits == 2  || blendbits == 4 ? 1 : 0)
					+ (rotateTMerge ? 1 : 0) + 256) % 4], x, y);

			if(blendbits == 0 && nearby((rotation+2)%4) == null) Draw.rect(rotation == 0 || rotation == 3 ? cap1 : cap0, x, y, (rotation+2)%4 * 90);

			if(nearby(rotation) == null) Draw.rect(rotation == 1 || rotation == 2 ? cap1 : cap0, x, y, rotation * 90);
		}

		@Override
		public void handleItem(Building source, Item item) {
			super.handleItem(source, item);
		}
	}
}
