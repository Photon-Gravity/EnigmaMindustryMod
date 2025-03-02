package enigma.custom.block;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.layout.Table;
import arc.util.io.Reads;
import arc.util.io.Writes;
import enigma.custom.polymorph.*;
import mindustry.gen.Building;
import mindustry.gen.Unit;
import mindustry.world.Block;
import mindustry.world.DirectionalItemBuffer;
import mindustry.world.blocks.ItemSelection;

import static mindustry.Vars.headless;
import static mindustry.Vars.renderer;

public class PolymorphSource extends Block {
	TextureRegion effectRegion;
	float sourced = 1000000;

	public PolymorphSource(String name) {
		super(name);
		update = true;

		configurable = true;
		saveConfig = true;
		clearOnDoubleTap = true;

		config(PolymorphPowerType.class, (PolymorphSourceBuild tile, PolymorphPowerType p) -> tile.filter = p);
		configClear((PolymorphSourceBuild tile) -> tile.filter = null);
	}

	@Override
	public void load() {
		super.load();
		effectRegion = Core.atlas.find(name+"-effect");
	}

	public class PolymorphSourceBuild extends Building implements IPolymorphUtilizer {
		PolymorphPowerType filter;

		PolymorphModule module;

		@Override
		public void update() {
			super.update();

			if(module == null) {
				module = new PolymorphModule(pos());

			}
			if(!getModule().inSystem()){
				PolymorphUpdater.makeSystem(pos());
			}
		}


		@Override
		public void draw(){
			super.draw();

			if(filter != null){
				Draw.color(filter.color);
				Draw.rect(effectRegion, x, y);
				Draw.color();
			}
		}

		@Override
		public void buildConfiguration(Table table){
			ItemSelection.buildTable(PolymorphSource.this, table, PolymorphPowerType.all, () -> filter, this::configure, selectionRows, selectionColumns);
		}

		@Override
		public void configured(Unit player, Object value){
			super.configured(player, value);

			if(!headless){
				renderer.minimap.update(tile);
			}
		}

		@Override
		public PolymorphPowerType config(){
			return filter;
		}

		@Override
		public void write(Writes write){
			super.write(write);
			write.s(filter == null ? -1 : filter.id);
		}

		@Override
		public void read(Reads read, byte revision){
			super.read(read, revision);
			short filterFinder = read.s();

			filter = filterFinder == -1 ? null : PolymorphPowerType.all.find(e -> e.id==filterFinder);

			if(revision == 1){
				new DirectionalItemBuffer(20).read(read);
			}
		}

		@Override
		public PolymorphModule getModule() {
			return module;
		}

		@Override
		public PolymorphPowerType enforced() {
			return filter;
		}

		@Override
		public float produced(PolymorphPowerType ofType) {
			return ofType == filter && filter != null ? sourced : 0;
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