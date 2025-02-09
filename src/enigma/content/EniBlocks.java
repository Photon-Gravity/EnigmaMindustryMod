package enigma.content;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import arc.math.Interp;
import enigma.custom.block.*;
import enigma.custom.polymorph.PolymorphPowerStack;
import enigma.graphics.EniPal;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.part.RegionPart;
import mindustry.gen.Sounds;
import mindustry.graphics.CacheLayer;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.draw.*;
import mindustry.world.meta.Attribute;

import static enigma.content.EniItems.*;
import static enigma.content.EniPolymorphTypes.therma;
import static enigma.util.Consts.px;
import static enigma.util.Consts.s;
import static mindustry.type.ItemStack.with;

public class EniBlocks {
	public static Block
		granite, sparseGranite, periclaseFloor, biotite, sparseBiotite, biotiteHot, crimsonMoss, wulfenite, denseWulfenite, ammoniaDeeper, ammoniaDeep, ammonia, ammoniaPericlase, ammoniaGranite, ammoniaSparseGranite, ammoniaBiotite, ammoniaSparseBiotite,
		graniteWall, biotiteWall, biotitePeridotWall, biotitePeridotCluster, peridotCrystal,
		graniteBoulder, biotiteBoulder, wulfeniteBoulder,

		molybdenumOre,

		incandescence,

		suctionDrill,
		encasedConveyor, axisGate,

		polymorphNode, polymorphEnforcer, geothermalCollector,
		molybdenumWall, largeMolybdenumWall, thermoacousticSonar,
		thermalCrystallizer,
		coreModule;
	public static void load(){
		//environment
		wulfenite = new Floor("wulfenite"){{
			variants = 5;
		}};

		denseWulfenite = new Floor("dense-wulfenite"){{
			variants = 5;
			blendGroup = wulfenite;
		}};

		granite = new Floor("granite"){{
			variants = 5;
		}};

		sparseGranite = new Floor("sparse-granite"){{
			variants = 5;
			blendGroup = granite;
		}};

		periclaseFloor = new Floor("periclase-floor"){{
			variants = 5;
			itemDrop = periclase;
		}};

		biotite = new Floor("biotite"){{
			variants = 5;
		}};

		sparseBiotite = new Floor("sparse-biotite"){{
			variants = 5;
			blendGroup = biotite;
		}};

		biotiteHot = new Floor("biotite-hot"){{
			variants = 5;
			attributes.set(Attribute.heat, 0.25f);
		}};

		crimsonMoss = new Floor("crimson-moss"){{
			variants = 5;
		}};


		ammoniaDeeper = new Floor("ammonia-deeper"){{
			variants = 0;
			//TODO liquiddrop
			liquidMultiplier = 0.5f;
			isLiquid = true;
			cacheLayer = CacheLayer.water;
			drownTime = 150f;
			speedMultiplier = 0.15f;
			statusDuration = 150f;
		}};

		ammoniaDeep = new Floor("ammonia-deep"){{
			variants = 0;
			//TODO liquiddrop
			liquidMultiplier = 0.5f;
			isLiquid = true;
			cacheLayer = CacheLayer.water;
			drownTime = 200f;
			speedMultiplier = 0.2f;
			statusDuration = 120f;
		}};

		ammonia = new Floor("ammonia"){{
			speedMultiplier = 0.5f;
			variants = 0;
			statusDuration = 90f;
			isLiquid = true;
			drownTime = 200f;
			//TODO liquiddrop
			cacheLayer = CacheLayer.water;
			albedo = 0.9f;
		}};

		ammoniaPericlase = new ShallowLiquid("ammonia-periclase"){{
			speedMultiplier = 0.8f;
			statusDuration = 50f;
			albedo = 0.9f;
			supportsOverlay = true;
		}};

		ammoniaGranite = new ShallowLiquid("ammonia-granite"){{
			speedMultiplier = 0.8f;
			statusDuration = 50f;
			albedo = 0.9f;
			supportsOverlay = true;
		}};

		ammoniaSparseGranite = new ShallowLiquid("ammonia-sparse-granite"){{
			speedMultiplier = 0.8f;
			statusDuration = 50f;
			albedo = 0.9f;
			supportsOverlay = true;
		}};

		ammoniaBiotite = new ShallowLiquid("ammonia-biotite"){{
			speedMultiplier = 0.8f;
			statusDuration = 50f;
			albedo = 0.9f;
			supportsOverlay = true;
		}};

		ammoniaSparseBiotite= new ShallowLiquid("ammonia-sparse-biotite"){{
			speedMultiplier = 0.8f;
			statusDuration = 50f;
			albedo = 0.9f;
			supportsOverlay = true;
		}};

		((ShallowLiquid)ammoniaPericlase).set(ammonia, periclaseFloor);
		((ShallowLiquid)ammoniaGranite).set(ammonia, granite);
		((ShallowLiquid)ammoniaSparseGranite).set(ammonia, sparseGranite);
		((ShallowLiquid)ammoniaBiotite).set(ammonia, biotite);
		((ShallowLiquid)ammoniaSparseBiotite).set(ammonia, sparseBiotite);

		graniteWall = new StaticWall("granite-wall"){{
			variants = 5;
			granite.asFloor().wall = this;
		}};

		biotiteWall = new StaticWall("biotite-wall"){{
			variants = 5;
			biotite.asFloor().wall = this;
		}};

		biotitePeridotWall = new StaticWall("biotite-peridot-wall"){{
			variants = 5;
		}};

		biotitePeridotCluster = new TallBlock("biotite-peridot-cluster"){{
			variants = 3;
			clipSize = 128f;
			shadowAlpha = 0.5f;
			shadowOffset = -2.5f;
		}};

		peridotCrystal = new TallBlock("peridot-crystal"){{
			variants = 1;
			clipSize = 128f;
			shadowAlpha = 0.5f;
			shadowOffset = -2.5f;
		}};

		graniteBoulder = new Prop("granite-boulder"){{
			variants = 5;
			granite.asFloor().decoration = this;
		}};

		biotiteBoulder = new Prop("biotite-boulder"){{
			variants = 5;
			biotite.asFloor().decoration = this;
		}};

		wulfeniteBoulder = new Prop("wulfenite-boulder"){{
			variants = 5;
			requirements(Category.effect, with(molybdenum, 10));
			placeablePlayer = false;

			wulfenite.asFloor().decoration = this;
			denseWulfenite.asFloor().decoration = this;
		}};

		//ore
		molybdenumOre = new OreBlock("molybdenum-ore"){{
			itemDrop = molybdenum;
		}};

		//turrets
		incandescence = new ItemPolymorphTurret("incandescence"){
			@Override
			public void init() {
				super.init();
				fogRadius = 2;
			}{
			size = 2;
			health = 2000;
			requirements(Category.turret, with(molybdenum, 55));
			researchCost = with(molybdenum, 110);

			consumed = new PolymorphPowerStack(therma, 50/s);
			reload = s;
			range = 180;

			minWarmup = 0.25f;
			recoil = 8 * px;
			cooldownTime = 0.5f*s;
			outlineColor = EniPal.outline;
			squareSprite = false;
			shootSound = Sounds.artillery;

			ammo(molybdenum, new ArtilleryBulletType(3f, 200){{
				frontColor = EniPal.blackbodyLight;
				backColor = trailColor = EniPal.blackbodyDark;

				lifetime = 90;

				height = 12;
				width = 9;

				trailWidth = 3;
				trailLength = 6;

				fragBullets = 4;
				fragBullet = new BasicBulletType(3f, 50){{
					frontColor = EniPal.blackbodyLight;
					backColor = trailColor = EniPal.blackbodyDark;

					lifetime = 20;

					height = 8;
					width = 6;

					trailWidth = 1.5f;
					trailLength = 3;
				}};
			}});

			drawer = new DrawTurret("enigma-hardened-"){{
				for(int i = 0; i < 2; i ++){
					int f = i;
					parts.add(new RegionPart("-wing-" + (i == 0 ? "l" : "r")){{
						progress = PartProgress.warmup.curve(Interp.smooth);
						under = true;
						moveX = 6 * px * (f == 0 ? -1 : 1);
						heatColor = therma.color;

						moves.add(new PartMove(PartProgress.recoil, 0, 8*px, 0));
					}});
				}
				parts.add(new RegionPart("-barrel"){{
					moveY = -6 * px;
					progress = PartProgress.recoil;
					under = true;
				}});
			}};

			limitRange();
		}};

		//drills
		suctionDrill = new PolymorphBurstDrill("suction-drill"){{
			size = 2;
			health = 1000;
			requirements(Category.production, with(molybdenum, 20));
			researchCost = with(molybdenum, 55);

			arrows = 8;
			arrowColor = therma.color;
			baseArrowColor = EniPal.outline;

			consumedPower = new PolymorphPowerStack[]{new PolymorphPowerStack(therma, 37.5f/s)};
			drillTime = 5*s;
			hardnessDrillMultiplier = s;
		}};

		//distribution
		encasedConveyor = new ShadedConveyor("encased-conveyor"){{
			health = 250;
			requirements(Category.distribution, with(molybdenum, 1));
			researchCost = with(molybdenum, 10);

			speed = 0.05f;
			displayedSpeed = 7f;
		}};

		axisGate = new AxisGate("axis-gate"){{
			health = 400;
			requirements(Category.distribution, with(molybdenum, 5));
			researchCost = with(molybdenum, 40);
		}};

		//power
		polymorphNode = new PolymorphNode("polymorph-node"){{
			size = 1;
			health = 100;
			requirements(Category.power, with(molybdenum, 5));
			researchCost = with(molybdenum, 25);

			linkCount = 10;
			linkDistance = 8;
		}};

		polymorphEnforcer = new PolymorphEnforcer("polymorph-enforcer"){{
			size = 1;
			health = 150;
			requirements(Category.power, with(molybdenum, 10));
			researchCost = with(molybdenum, 100);
		}};

		geothermalCollector = new PolymorphThermalGenerator("geothermal-collector"){{
			health = 500;
			size = 2;
			requirements(Category.power, with(molybdenum, 85));
			researchCost = with(molybdenum, 45);

			powerProduction = 0;
			produced = new PolymorphPowerStack(therma, 100/s);

			squareSprite = false;
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawGlowRegion(){{ color = therma.color; suffix = "-glow"; }},
				new DrawDefault()
			);
		}};

		//defense
		molybdenumWall = new Wall("molybdenum-wall"){{
			size = 1;
			health = 750;
			requirements(Category.defense, with(molybdenum, 6));
			researchCost = with(molybdenum, 128);
		}};

		largeMolybdenumWall = new Wall("large-molybdenum-wall"){{
			size = 2;
			health = 750 * 4;
			requirements(Category.defense, with(molybdenum, 24));
			researchCost = with(molybdenum, 512);
		}};

		thermoacousticSonar = new PolymorphRadar("thermoacoustic-sonar"){{
			size = 1;
			health = 500;
			requirements(Category.defense, with(molybdenum, 16));
			researchCost = with(molybdenum, 128);

			fogRadius = 25;

			outlineColor = EniPal.outline;
			glowColor = therma.color;
			consumed = new PolymorphPowerStack(therma, 30/s);
		}};

		//crafting
		thermalCrystallizer = new PolymorphCrafter("thermal-crystallizer"){{
			health = 1750;
			size = 3;
			requirements(Category.crafting, with(molybdenum, 135));
			researchCost = with(molybdenum, 270);

			craftTime = 1.5f*s;
			consumeItem(periclase, 4);
			consumedPower = new PolymorphPowerStack[]{new PolymorphPowerStack(therma, 225/s)};

			outputItem = new ItemStack(irtran, 3);

			squareSprite = false;
			drawer = new DrawMulti(
					new DrawRegion("-bottom"),
					new DrawGlowRegion(){{ color = therma.color; suffix = "-glow"; }},
					new DrawDefault()
			);
		}};

		//effect
		coreModule = new CoreBlock("core-module"){
		@Override
		public void init() {
			super.init();
			fogRadius = 20;
		}
		@Override
		public void load(){
			super.load();
			uiIcon = fullIcon = Core.atlas.find(name + "-icon");
		}
		@Override
		public TextureRegion[] icons(){
			return new TextureRegion[]{fullIcon};
		}
		{
			size = 4;
			health = 10000;
			requirements(Category.effect, with(molybdenum, 1650));

			itemCapacity = 4500;
			unitType = EniUnits.pion;
			alwaysUnlocked = true;
			thrusterLength = 28 * px;
		}};
	}
}
