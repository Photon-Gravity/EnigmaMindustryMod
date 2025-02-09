package enigma.custom.block;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.io.Reads;
import arc.util.io.Writes;
import enigma.custom.polymorph.PolymorphPowerType;
import enigma.custom.polymorph.PolymorphSystem;
import enigma.custom.polymorph.interfaces.PolymorphUtilizer;
import mindustry.gen.Building;
import mindustry.gen.Unit;
import mindustry.world.Block;
import mindustry.world.DirectionalItemBuffer;
import mindustry.world.blocks.ItemSelection;

import static mindustry.Vars.headless;
import static mindustry.Vars.renderer;

public class PolymorphEnforcer extends Block {
	TextureRegion filterRegion;

	public PolymorphEnforcer(String name) {
		super(name);
		update = true;

		configurable = true;
		saveConfig = true;
		clearOnDoubleTap = true;

		config(PolymorphPowerType.class, (PolymorphEnforcer.PolymorphEnforcerBuild tile, PolymorphPowerType p) -> tile.filter = p);
		configClear((PolymorphEnforcer.PolymorphEnforcerBuild tile) -> tile.filter = null);
	}

	@Override
	public void load() {
		super.load();
		filterRegion = Core.atlas.find(name+"-filter");
	}

	public class PolymorphEnforcerBuild extends Building implements PolymorphUtilizer {
		PolymorphPowerType filter;

		PolymorphSystem system;

		@Override
		public void draw(){
			super.draw();

			if(filter != null){
				Draw.color(filter.color);
				Draw.rect(filterRegion, x, y);
				Draw.color();
			}
		}

		@Override
		public void buildConfiguration(Table table){
			ItemSelection.buildTable(PolymorphEnforcer.this, table, PolymorphPowerType.all, () -> filter, this::configure, selectionRows, selectionColumns);
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
		public void setSystem(PolymorphSystem newSystem) {
			this.system = newSystem;
		}

		@Override
		public PolymorphSystem getSystem() {
			return system;
		}

		@Override
		public PolymorphPowerType getEnforcedPowerType() {
			return filter;
		}

		@Override
		public void addToSystem(PolymorphSystem s, Seq<Integer> scheduled) {
			setSystem(s);
		}
	}
}
