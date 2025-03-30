package enigma.custom.block;

import arc.Core;
import arc.func.Func;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import enigma.custom.polymorph.*;
import mindustry.Vars;
import mindustry.core.Renderer;
import mindustry.core.UI;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.Block;

import static enigma.util.Consts.px;
import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;


public class PolymorphNode extends Block {

	public int linkRange = 8;

	public float pulseScl = 7, pulseMag = 0.05f;
	public float laserWidth = 0.4f, laserOffset = 10 * px / tilesize;

	TextureRegion laser, laserEnd, effectRegion;
	public PolymorphNode(String name) {
		super(name);
		update = true;
		clipSize = (size + 2 * linkRange) * (tilesize + 2f);
	}

	@Override
	public void load() {
		super.load();

		laser = Core.atlas.find(name + "-laser");
		laserEnd = Core.atlas.find(name + "-laser-end");
		effectRegion = Core.atlas.find(name+"-effect");

	}

	@Override
	public void setBars(){
		super.setBars();

		addBar("power", makePowerBalance());
		addBar("batteries", makeBatteryBalance());
	}

	@Override
	public void drawPlace(int x, int y, int rotation, boolean valid){
		for(int i = 0; i < 4; i++){
			int maxLen = linkRange + size/2;
			Building dest = null;
			var dir = Geometry.d4[i];
			int dx = dir.x, dy = dir.y;
			int offset = size/2;
			for(int j = 1 + offset; j <= linkRange + offset; j++){
				var other = world.build(x + j * dir.x, y + j * dir.y);

				if(other instanceof IPolymorphUtilizer && other.team == Vars.player.team()){
					maxLen = j;
					dest = other;
					break;
				}
			}

			Drawf.dashLine(Pal.placing,
					x * tilesize + dx * (tilesize * size / 2f + 2),
					y * tilesize + dy * (tilesize * size / 2f + 2),
					x * tilesize + dx * (maxLen) * tilesize,
					y * tilesize + dy * (maxLen) * tilesize
			);

			if(dest != null){
				Drawf.square(dest.x, dest.y, dest.block.size * tilesize/2f + 2.5f, 0f);
			}
		}
	}

	public static Func<Building, Bar> makePowerBalance(){
		return entity -> new Bar(() ->
				Core.bundle.format(
						"bar.polymorphbalance",
						((getBalanceFrom(entity) >= 0 ? "+" : "") + UI.formatAmount((long)(getBalanceFrom(entity) * 60))),
						getPolymorphFrom(entity) != null ? getPolymorphFrom(entity).localizedName : "N/A"
				),
				() -> getPolymorphFrom(entity) != null ? getPolymorphFrom(entity).color : Color.gray,
				() -> entity instanceof IPolymorphUtilizer util && util.getModule() != null ? util.getModule().satisfaction(util.getModule().getEnforced()) : 0
		);
	}

	public static Func<Building, Bar> makeBatteryBalance(){
		return entity -> new Bar(() ->
				Core.bundle.format(
						"bar.polymorphstored",
						(UI.formatAmount((long)getStoredFrom(entity))), UI.formatAmount((long)getStorableFrom(entity))
				),
				() -> getPolymorphFrom(entity) != null ? getPolymorphFrom(entity).color : Color.gray,
				() -> Mathf.clamp(getStoredFrom(entity) / getStorableFrom(entity))
		);
	}

	public static float getBalanceFrom(Building entity){
		return entity instanceof IPolymorphUtilizer util && util.getModule() != null && util.getModule().inSystem() ? util.getModule().firstSystemFor(util.getModule().getEnforced()).balance() : 0;
	}

	public static float getStoredFrom(Building entity){
		return entity instanceof IPolymorphUtilizer util && util.getModule() != null && util.getModule().inSystem() ? util.getModule().firstSystemFor(util.getModule().getEnforced()).stored() : 0;
	}

	public static float getStorableFrom(Building entity){
		return entity instanceof IPolymorphUtilizer util && util.getModule() != null && util.getModule().inSystem() ? util.getModule().firstSystemFor(util.getModule().getEnforced()).storable() : 0;
	}

	public static PolymorphPowerType getPolymorphFrom(Building entity){
		return entity instanceof IPolymorphUtilizer util && util.getModule() != null ? util.getModule().getEnforced() : null;
	}

	public class PolymorphNodeBuild extends Building implements IPolymorphUtilizer {
		PolymorphModule module;

		int[] visualLinks = new int[4];
		@Override
		public void update() {
			super.update();
			if(module == null) {
				module = new PolymorphModule(pos());

			}
			if(!getModule().inSystem()){
				PolymorphUpdater.makeSystem(pos());
			}

			if(module != null){
				for(int i=0; i <4 ; i++) {
					checkDir(i);
				}
			}
		}

		public void checkDir(int rot){
			Building found = null;

			visualLinks[rot] = -1;

			boolean hitInsulated = false;

			for (int i = 1; i <= linkRange; i++) {

				Building b = Vars.world.build(tileX() + Geometry.d4x[rot] * i, tileY() + Geometry.d4y[rot] * i); //get next block

				if(found != null && found.isInsulated()){ //stop trying to connect if hit insulated block
					hitInsulated = true;
				} else if (!hitInsulated && found == null && b != null && getModule().links.contains(b.pos())){ //if the first found block is already linked, mark as found.
					found = b;
					visualLinks[rot] = i;
				} else if (!hitInsulated && found == null && b instanceof IPolymorphUtilizer util && team == b.team && util.getModule() != null && util.getModule().canConnect(getModule()) && getModule().canConnect(util.getModule())) { //if first found is not in the system and the system is compatible with this one, link it
					module.linkTo(util.getModule());
					found = b;
					visualLinks[rot] = i;
				} else if (found != null && b != null && found.pos() != b.pos() && getModule().links.contains(b.pos())) { //if we've already found our link and a block behind it is still linked, unlink it.
					getModule().unlinkFrom(b.pos());
				}
			}

		}

		@Override
		public void draw() {
			//rand.setSeed(module != null && module.system != null ? module.system.id : 0);
			//Draw.color(new Color(module != null ? rand.nextInt() | 0x000000FF : 0xFFFFFFFF));

			super.draw();

			//Draw.reset();

			if(Mathf.zero(Renderer.laserOpacity)) return;




			Draw.color(
					module != null && module.getEnforced() != null ?
					module.getEnforced().color :
					Color.gray,

					module != null && module.getEnforced() != null ?
					module.getEnforced().colorDark :
					Color.black,

					module != null ? 1f - module.satisfaction(module.getEnforced()) : 1f
			);

			if(module != null && module.getEnforced() != null) Draw.rect(effectRegion, x, y);

			Draw.z(Layer.power);
			Draw.alpha(Renderer.laserOpacity);
			float w = laserWidth + Mathf.absin(pulseScl, pulseMag);

			for(int i = 0; i < 4 ; i++){

				int link = visualLinks[i];

				Building build = Vars.world.build(tileX() + Geometry.d4x(i) * link, tileY() + Geometry.d4y(i) * link);

				if(build instanceof IPolymorphUtilizer && build.wasVisible && (!(build.block instanceof PolymorphNode node) ||
						(build.tileX() != tileX() && build.tileY() != tileY()) ||
						(build.id > id && linkRange >= node.linkRange) || linkRange > node.linkRange)){

					//don't draw lasers for adjacent blocks
					if(link > 1 + size/2){
						drawLaser(x + tilesize * Geometry.d4x(i) * laserOffset, y +tilesize * Geometry.d4y(i) * laserOffset, x + tilesize * Geometry.d4x(i) * (link - laserOffset), y + tilesize * Geometry.d4y(i) * (link - laserOffset), size, size, w);
					}
				}
			}

			Draw.reset();
		}

		public void drawLaser(float x1, float y1, float x2, float y2, int size1, int size2, float w){
			float angle1 = Angles.angle(x1, y1, x2, y2),
					vx = Mathf.cosDeg(angle1), vy = Mathf.sinDeg(angle1),
					len1 = size1 * tilesize / 2f - 1.5f, len2 = size2 * tilesize / 2f - 1.5f;

			Drawf.laser(laser, laserEnd, x1 + vx*len1, y1 + vy*len1, x2 - vx*len2, y2 - vy*len2, w);
		}

		@Override
		public PolymorphModule getModule() {
			return module;
		}

		@Override
		public PolymorphPowerType enforced() {
			return null;
		}

		@Override
		public float produced(PolymorphPowerType ofType) {
			return 0;
		}

		@Override
		public float consumed(PolymorphPowerType ofType) {
			return 0;
		}

		@Override
		public float storable(PolymorphPowerType ofType) {
			return 0;
		}

		@Override
		public float stored(PolymorphPowerType ofType) {
			return 0;
		}

		@Override
		public void store(PolymorphPowerStack stack) {

		}
	}
}
