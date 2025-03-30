package enigma.graphics.draw;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.draw.DrawRegion;

public class DrawBlades extends DrawRegion {
	int blades;

	float rot, radRot;
	TextureRegion invRegion, previewRegion;
	public DrawBlades(String suffix, int blades, float speed){
		super(suffix, speed);
		this.blades = blades;
		radRot = (float)Math.PI*2 / blades;
		rot = 360f / blades;
	}

	@Override
	public void load(Block block) {
		super.load(block);
		invRegion = Core.atlas.find(block.name + suffix + "-inv");
		previewRegion = Core.atlas.find(block.name + suffix + "-preview");
	}

	@Override
	public void draw(Building build) {
		float r = (build.totalProgress() * rotateSpeed) % 360;

		if(rotateSpeed < 0){
			r += 360;
		}

		for(int i=0;i < blades;i++){
			float cRot = (r + rot*i) % 360;
			int sector = (int)Math.floor(cRot / 90);

			Draw.rect(region, build.x, build.y, cRot);

			if(sector == 1){
				Draw.alpha((cRot-90)/90);
			} else if (sector == 3){
				Draw.alpha(1 - (cRot-270)/90);
			}

			if(sector != 0){
				Draw.rect(invRegion, build.x, build.y, cRot);
				Draw.alpha(1);
			}
		}
	}

	@Override
	public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
		super.drawPlan(block, plan, list);
		Draw.rect(previewRegion, plan.x, plan.y);
	}

	@Override
	public TextureRegion[] icons(Block block) {
		return new TextureRegion[]{previewRegion};
	}
}
