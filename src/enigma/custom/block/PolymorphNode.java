package enigma.custom.block;

import arc.Core;
import arc.func.Boolf;
import arc.func.Cons;
import arc.func.Func;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Intersector;
import arc.math.geom.Point2;
import arc.struct.Seq;
import arc.util.Nullable;
import arc.util.Structs;
import arc.util.Time;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import enigma.custom.polymorph.PolymorphPowerType;
import enigma.custom.polymorph.PolymorphSystem;
import enigma.custom.polymorph.PolymorphSystemUpdater;
import enigma.custom.polymorph.interfaces.PolymorphUtilizer;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.core.Renderer;
import mindustry.core.UI;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.Edges;
import mindustry.world.Tile;
import mindustry.world.meta.Env;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import static enigma.util.Consts.DEBUG;
import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;

public class PolymorphNode extends Block {

	public int linkCount = 10, linkDistance = 8;

	protected static int returnInt = 0;

	TextureRegion laser, laserEnd;

	public PolymorphNode(String name) {
		super(name);
		update = true;
		configurable = true;
		consumesPower = false;
		outputsPower = false;
		canOverdrive = false;
		swapDiagonalPlacement = true;
		schematicPriority = -10;
		drawDisabled = false;
		envEnabled |= Env.space;
		destructible = true;

		config(Integer.class, (entity, value) -> {
			PolymorphNodeBuild thisNode = (PolymorphNodeBuild) entity;
			PolymorphSystem system = thisNode.system;
			Building other = world.build(value);
			boolean contains = other != null && thisNode.links.contains(other.pos()), valid = other != null;
			if(contains){
				//if other is a node, remove this from the links
				if(other instanceof PolymorphNodeBuild otherNode) {
					otherNode.links.remove((Integer)thisNode.pos());
				}
				//remove other from this node's links
				thisNode.links.remove(value);

				//obliterate the system, note that the link changes from previous steps don't affect the actual system yet
				system.delete();

				//restore the new system
				PolymorphSystemUpdater.propagateNewSystem(thisNode.pos());

				//if other block is a node and is not entailed within this node's new system, it gets its own new system
				if(other instanceof PolymorphNodeBuild otherNode && !thisNode.system.contains(otherNode.pos())){
					PolymorphSystemUpdater.propagateNewSystem(otherNode.pos());
				}


			}else if(other instanceof PolymorphUtilizer util && linkValid(entity, other, true) && valid && thisNode.links.size < linkCount && ((other instanceof PolymorphNodeBuild && (util.getSystem().type == thisNode.system.type || util.getSystem() == null || util.getSystem().type == null || thisNode.system.type == null) || util.getSystem() == null))){

				//modify links
				thisNode.links.add(((Building)util).pos());

				if(util instanceof PolymorphNodeBuild node){
					node.links.add(thisNode.pos());
				}

				//modify systems
				if(thisNode.getSystem() != util.getSystem()){
					thisNode.getSystem().delete(); //we must do this on the node's side, because not all utilizers are required to know what they're connected to. We can't do this if both systems are the same.

					if(util.getSystem() != null){
						PolymorphSystemUpdater.propagateSystem(thisNode.pos(), util.getSystem());
					} else {
						PolymorphSystemUpdater.propagateNewSystem(thisNode.pos());
					}
				}
			}
		});

		config(Point2[].class, (tile, value) -> {
			Seq<Integer> old = ((PolymorphNodeBuild)tile).links.copy();

			//clear old
			for(int i = 0; i < old.size; i++){
				configurations.get(Integer.class).get(tile, old.get(i));
			}

			//set new
			for(Point2 p : value){
				configurations.get(Integer.class).get(tile, Point2.pack(p.x + tile.tileX(), p.y + tile.tileY()));
			}
		});
	}

	@Override
	public void init(){
		super.init();

		clipSize = Math.max(clipSize, linkDistance * tilesize);
	}

	@Override
	public void load() {
		super.load();

		laser = Core.atlas.find(name + "-laser");
		laserEnd = Core.atlas.find(name + "-laser-end");
	}

	@Override
	public void drawPlace(int x, int y, int rotation, boolean valid) {
		super.drawPlace(x, y, rotation, valid);

		Draw.color(valid ? Pal.accent : Pal.remove);
		Drawf.circles(x * 8, y * 8, linkDistance * tilesize);
		Draw.reset();
	}

	@Override
	public void setStats() {
		super.setStats();
		stats.add(Stat.range, linkDistance, StatUnit.blocks);
		stats.add(Stat.powerConnections, linkCount);
	}

	@Override
	public void setBars(){
		super.setBars();
		addBar("power", makePowerBalance());
		addBar("batteries", makeBatteryBalance());

		addBar("connections", entity -> new Bar(() ->
				Core.bundle.format("bar.powerlines", ((PolymorphNodeBuild)entity).links.size, linkCount),
				() -> Pal.items,
				() -> ((PolymorphNodeBuild)entity).links.size / (float)linkCount
		));
	}

	public static Func<Building, Bar> makePowerBalance(){
		return entity -> new Bar(() ->
				Core.bundle.format(
						"bar.polymorphbalance", //very dense notation that amounts to nullproofing
						((((PolymorphUtilizer)entity).getSystem() != null ? ((PolymorphUtilizer)entity).getSystem().balance() : 0) >= 0 ? "+" : "") + UI.formatAmount((long)(((PolymorphUtilizer)entity).getSystem() != null ? ((PolymorphUtilizer)entity).getSystem().balance() * 60 : 0)),
				            ((PolymorphUtilizer)entity).getSystem() != null ? (((PolymorphUtilizer)entity).getSystem().type != null ? ((PolymorphUtilizer)entity).getSystem().type.localizedName : "N/A") : "N/A"
				),
				() -> ((PolymorphUtilizer)entity).getSystem() != null ? (((PolymorphUtilizer)entity).getSystem().type != null ? ((PolymorphUtilizer)entity).getSystem().type.color : Color.gray) : Color.gray,
				() -> Mathf.clamp(
						((PolymorphUtilizer)entity).getSystem() != null ? ((PolymorphUtilizer)entity).getSystem().satisfaction() : 0
				)
		);
	}

	public static Func<Building, Bar> makeBatteryBalance(){
		return entity -> new Bar(() ->
				Core.bundle.format( //more nullproofing, this time blown up
						"bar.polymorphstored",
						UI.formatAmount(
								(long)(((PolymorphUtilizer)entity).getSystem() != null ? ((PolymorphUtilizer)entity).getSystem().stored() : 0)
						),
						UI.formatAmount(
								(long)(((PolymorphUtilizer)entity).getSystem() != null ? ((PolymorphUtilizer)entity).getSystem().storable() : 0)
						)
				),
				() -> ((PolymorphUtilizer)entity).getSystem() != null && ((PolymorphUtilizer)entity).getSystem().type != null ? ((PolymorphUtilizer)entity).getSystem().type.color : Color.gray,
				() -> Mathf.clamp(
						((PolymorphUtilizer)entity).getSystem() != null ? ((PolymorphUtilizer)entity).getSystem().stored() / ((PolymorphUtilizer)entity).getSystem().storable() : 0
				)
		);
	}


	public boolean linkValid(Building tile, Building link, boolean checkMaxNodes){
		if(tile == link || link == null || !(link instanceof PolymorphUtilizer) || tile.team != link.team) return false;

		if(overlaps(tile, link, linkDistance * tilesize) || (link.block instanceof PolymorphNode node && overlaps(link, tile, node.linkDistance * tilesize))){
			if(checkMaxNodes && link.block instanceof PolymorphNode node){
				return ((PolymorphNodeBuild)link).links.size < node.linkCount || ((PolymorphNodeBuild)link).links.contains(tile.pos());
			}
			return true;
		}
		return false;
	}

	protected boolean overlaps(Building src, Building other, float range){
		return overlaps(src.x, src.y, other.tile(), range);
	}
	protected boolean overlaps(float srcx, float srcy, Tile other, float range){
		return Intersector.overlaps(Tmp.cr1.set(srcx, srcy, range), other.getHitbox(Tmp.r1));
	}

	protected void getPotentialLinks(Tile tile, Team team, Cons<Building> others){
		Boolf<Building> valid = other ->
				other != null && other.tile() != tile &&
				other instanceof PolymorphUtilizer &&
				overlaps(tile.x * tilesize + offset, tile.y * tilesize + offset, other.tile(), linkDistance * tilesize) &&
				other.team == team &&
				!((PolymorphNodeBuild)tile.build).system.contains(other.pos()) &&
				!(
						other instanceof PolymorphNodeBuild obuild &&
						obuild.links.size >= ((PolymorphNode)obuild.block).linkCount
				) &&
				!Structs.contains(Edges.getEdges(size), p -> { //do not link to adjacent buildings
					var t = world.tile(tile.x + p.x, tile.y + p.y);
					return t != null && t.build == other;
				});

		tempBuilds.clear();

		var worldRange = linkDistance * tilesize;
		var tree = team.data().buildingTree;
		if(tree != null){
			tree.intersect(tile.worldx() - worldRange, tile.worldy() - worldRange, worldRange * 2, worldRange * 2, build -> {
				if(valid.get(build) && !tempBuilds.contains(build)){
					tempBuilds.add(build);
				}
			});
		}

		tempBuilds.sort((a, b) -> {
			int type = -Boolean.compare(a.block instanceof PolymorphNode, b.block instanceof PolymorphNode);
			if(type != 0) return type;
			return Float.compare(a.dst2(tile), b.dst2(tile));
		});

		returnInt = 0;

		tempBuilds.each(valid, t -> {
			if(returnInt ++ < linkCount){
				others.get(t);
			}
		});
	}

	public class PolymorphNodeBuild extends Building implements PolymorphUtilizer{
		PolymorphSystem system;

		public Seq<Integer> links = new Seq<>();

		@Override
		public void placed() {
			super.placed();

			PolymorphSystemUpdater.propagateNewSystem(pos());

			int[] total = {0};
			getPotentialLinks(tile, team, link -> {
				if(total[0]++ < linkCount){
					configure(link.pos());
				}
			});
		}

		@Override
		public void update() {
			super.update();

			for(int link : links){
				if (!(Vars.world.build(link) instanceof PolymorphUtilizer)){
					links.remove((Integer)link);
					system.delete();
					PolymorphSystemUpdater.recalculateSystems();
				}
			}
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
			this.system = s;

			for(int link : links){
				if(!system.contains(link)) scheduled.add(link);
			}
		}

		@Override
		public boolean onConfigureBuildTapped(Building other){
			if(linkValid(this, other, true)){
				configure(other.pos());
				return false;
			}

			if(this == other){ //double tapped
				if(this.links.size == 0 || Core.input.shift()){ //find links
					int[] total = {0};
					getPotentialLinks(tile, team, link -> {
						if(total[0]++ < linkCount){
							configure(link.pos());
						}
					});
				}else{ //clear links
					while(links.size > 0){
						configure(links.get(0));
					}
				}
				deselect();
				return false;
			}

			return true;
		}

		@Override
		public void drawSelect(){
			super.drawSelect();

			Lines.stroke(1f);

			Draw.color(Pal.accent);
			Drawf.circles(x, y, linkDistance * tilesize);
			Draw.reset();
		}

		@Override
		public void drawConfigure(){

			Drawf.circles(x, y, tile.block().size * tilesize / 2f + 1f + Mathf.absin(Time.time, 4f, 1f));

			Drawf.circles(x, y, linkDistance * tilesize);

			for(int x = (int)(tile.x - linkDistance - 2); x <= tile.x + linkDistance + 2; x++){
				for(int y = (int)(tile.y - linkDistance - 2); y <= tile.y + linkDistance + 2; y++){
					Building link = world.build(x, y);

					if(link != this && linkValid(this, link, false)){
						boolean linked = links.contains(link.pos());

						if(linked){
							Drawf.square(link.x, link.y, link.block.size * tilesize / 2f + 1f, Pal.place);
						}
					}
				}
			}

				Draw.reset();
		}

		@Override
		public void draw(){

			if(system != null && DEBUG){
				Fx.rand.setSeed(system.ID);
				Draw.color(new Color(Fx.rand.nextInt() | 0x000000FF));
			}
			super.draw();

			if(DEBUG) Draw.reset();

			if(Mathf.zero(Renderer.laserOpacity) || isPayload()) return;

			Draw.z(Layer.power);
			setupColor(system != null ? system.satisfaction() : 1, system != null ? system.type : null);

			for(int i = 0; i < links.size; i++){
				Building link = Vars.world.build(links.get(i));

				if(!linkValid(this, link, true)) continue;

				if(link.block instanceof PolymorphNode && link.id >= id) continue;

				drawLaser(x, y, link.x, link.y, size, link.block.size);
			}

			Draw.reset();
		}

		public void drawLaser(float x1, float y1, float x2, float y2, int size1, int size2){
			float angle1 = Angles.angle(x1, y1, x2, y2),
					vx = Mathf.cosDeg(angle1), vy = Mathf.sinDeg(angle1),
					len1 = size1 * tilesize / 2f - 1.5f, len2 = size2 * tilesize / 2f - 1.5f;

			Drawf.laser(laser, laserEnd, x1 + vx*len1, y1 + vy*len1, x2 - vx*len2, y2 - vy*len2, 0.25f);
		}

		protected void setupColor(float satisfaction, @Nullable PolymorphPowerType type){
			Draw.color(type != null ? type.color : Color.gray, type != null ? type.colorDark : Color.gray, (1f - satisfaction) * 0.86f + Mathf.absin(3f, 0.1f));
			Draw.alpha(Renderer.laserOpacity);
		}

		@Override
		public Point2[] config(){
			Point2[] out = new Point2[links.size];
			for(int i = 0; i < out.length; i++){
				out[i] = Point2.unpack(links.get(i));
			}
			return out;
		}

		@Override
		public void write(Writes write) {
			super.write(write);

			write.i(links.size);
			for(int link : links){
				write.i(link);
			}
		}

		@Override
		public void read(Reads read, byte revision) {
			super.read(read, revision);

			int linksSize = read.i();
			for(int i = 0; i<linksSize; i++){
				links.add(read.i());
			}
		}
	}
}
