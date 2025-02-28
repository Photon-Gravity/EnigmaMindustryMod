package enigma.custom.block;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.layout.Table;
import arc.util.Eachable;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.gen.Unit;
import mindustry.type.Item;
import mindustry.world.DirectionalItemBuffer;
import mindustry.world.Tile;
import mindustry.world.blocks.ItemSelection;
import mindustry.world.blocks.distribution.Junction;
import mindustry.world.meta.BlockGroup;

import static mindustry.Vars.*;

public class ItemFilter extends Junction {
	public TextureRegion filterRegion;

	public ItemFilter(String name) {
		super(name);
		configurable = true;
		unloadable = false;
		saveConfig = true;
		clearOnDoubleTap = true;
		group = BlockGroup.transportation;

		config(Item.class, (ItemFilter.ItemFilterBuild tile, Item item) -> tile.filter = item);
		configClear((ItemFilter.ItemFilterBuild tile) -> tile.filter = null);
	}

	@Override
	public void drawPlanConfig(BuildPlan plan, Eachable<BuildPlan> list){
		if(list != null){
			drawPlanConfigCenter(plan, plan.config, name + "-filter", false);
		}
	}

	@Override
	public void load() {
		super.load();
		filterRegion = Core.atlas.find(name+"-filter");
	}

	@Override
	public int minimapColor(Tile tile){
		var build = (ItemFilter.ItemFilterBuild)tile.build;
		return build == null || build.filter == null ? 0 : build.filter.color.rgba();
	}

	public class ItemFilterBuild extends JunctionBuild{
		Item filter;

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
		public boolean acceptItem(Building source, Item item) {
			return super.acceptItem(source, item) && (filter == null || filter == item);
		}

		@Override
		public void buildConfiguration(Table table){
			ItemSelection.buildTable(ItemFilter.this, table, content.items(), () -> filter, this::configure, selectionRows, selectionColumns);
		}

		@Override
		public void configured(Unit player, Object value){
			super.configured(player, value);

			if(!headless){
				renderer.minimap.update(tile);
			}
		}

		@Override
		public Item config(){
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
			filter = content.item(read.s());

			if(revision == 1){
				new DirectionalItemBuffer(20).read(read);
			}
		}
	}
}
