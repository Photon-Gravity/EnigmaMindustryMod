package enigma.content;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import arc.math.Interp;
import arc.struct.Seq;
import enigma.custom.block.*;
import enigma.custom.polymorph.PolymorphPowerStack;
import enigma.graphics.EFx;
import enigma.graphics.EniPal;
import enigma.graphics.draw.DrawBlades;
import mindustry.content.StatusEffects;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.ShrapnelBulletType;
import mindustry.entities.part.RegionPart;
import mindustry.gen.Sounds;
import mindustry.graphics.CacheLayer;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.AutoDoor;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.distribution.DirectionLiquidBridge;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.liquid.LiquidJunction;
import mindustry.world.blocks.liquid.LiquidRouter;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.draw.*;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.BuildVisibility;

import static enigma.content.EniItems.*;
import static enigma.content.EniLiquids.*;
import static enigma.content.EniPolymorphTypes.*;
import static enigma.content.EniUnits.*;
import static enigma.util.Consts.*;
import static mindustry.type.ItemStack.with;

public class EniBlocks {
	public static Block
		granite, sparseGranite, periclaseFloor, biotite, sparseBiotite, biotiteHot, crimsonMoss, tartaricSediment, tartaricRock, wulfenite, denseWulfenite, ammoniaDeeper, ammoniaDeep, ammonia, ammoniaPericlase, ammoniaGranite, ammoniaSparseGranite, ammoniaBiotite, ammoniaSparseBiotite, ammoniaTartaricSediment, ammoniaTartaricRock,
		graniteWall, biotiteWall, biotitePeridotWall, biotitePeridotCluster, peridotCrystal,
		graniteBoulder, biotiteBoulder, wulfeniteBoulder, tartaricBoulder,

		molybdenumOre, rutheniumOre,

		incandescence, deprivation, chasm,

		vacuumDrill, perforationDrill,
		encasedConveyor, axisGate, filter, thermobaricLauncher,
		differentialPump, enameledConduit, enameledRouter, enameledJunction, enameledBridge,

		polymorphNode, polymorphEnforcer, geothermalCollector, geothermalSiphon, thermoelectricAdapter, evaporationDynamo, polymorphSource,
		molybdenumWall, largeMolybdenumWall, rutheniumWall, largeRutheniumWall, fulgoriteFiberWall, largeFulgoriteFiberWall, gateway, thermoacousticSonar,
		thermalCrystallizer, thermalDistiller, combustionReactor, photochemicalRefinery, dischargeExtruder,

		replicator,
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

		tartaricSediment = new Floor("tartaric-sediment"){{
			variants = 5;
		}};

		tartaricRock = new Floor("tartaric-rock"){{
			variants = 5;
		}};


		ammoniaDeeper = new Floor("ammonia-deeper"){{
			variants = 0;
			liquidDrop = causticAmmonia;
			isLiquid = true;
			cacheLayer = CacheLayer.water;
			drownTime = 150f;
			speedMultiplier = 0.15f;
			statusDuration = 150f;
		}};

		ammoniaDeep = new Floor("ammonia-deep"){{
			variants = 0;
			liquidDrop = causticAmmonia;
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
			liquidDrop = causticAmmonia;
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

		ammoniaTartaricSediment= new ShallowLiquid("ammonia-tartaric-sediemnt"){{
			speedMultiplier = 0.8f;
			statusDuration = 50f;
			albedo = 0.9f;
			supportsOverlay = true;
		}};

		ammoniaTartaricRock = new ShallowLiquid("ammonia-tartaric-rock"){{
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
		((ShallowLiquid)ammoniaTartaricSediment).set(ammonia, tartaricSediment);
		((ShallowLiquid)ammoniaTartaricRock).set(ammonia, tartaricRock);

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

		tartaricBoulder = new Prop("tartaric-boulder"){{
			variants = 5;
			tartaricSediment.asFloor().decoration = this;
			tartaricRock.asFloor().decoration = this;
		}};

		wulfeniteBoulder = new Prop("wulfenite-boulder"){{
			variants = 5;

			wulfenite.asFloor().decoration = this;
			denseWulfenite.asFloor().decoration = this;
		}};

		//ore
		molybdenumOre = new OreBlock("molybdenum-ore"){{
			itemDrop = molybdenum;
			variants = 4;
		}};

		rutheniumOre = new OreBlock("ruthenium-ore"){{
			itemDrop = ruthenium;
			variants = 5;
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
			targetAir = true;

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

		deprivation = new ItemPolymorphTurret("deprivation"){
			@Override
			public void init() {
				super.init();
				fogRadius = 2;
			}

			{
				size = 3;
				health = 4500;
				requirements(Category.turret, with(molybdenum, 85, irtran, 20, ruthenium, 40));
				researchCost = with(molybdenum, 170, irtran, 50, ruthenium, 100);

				consumed = new PolymorphPowerStack(ion, 500 / s);
				reload = 0.6666f * s;
				range = 130;
				targetAir = false;

				minWarmup = 0;
				recoil = 12 * px;
				cooldownTime = 0.5f * s;
				outlineColor = EniPal.outline;
				squareSprite = false;
				shootSound = Sounds.shotgun;

				ammo(irtran, new ShrapnelBulletType() {{
					shootY = -10*px;

					serrations = 16;
					serrationLenScl = 4f;
					length = 130f;
					width = 18;
					fromColor = EniPal.irtranLight;
					toColor = EniPal.irtran;
					collidesAir = false;

					damage = 350f;
					pierceCap = 3;
					ammoMultiplier = 2f;
				}});

				drawer = new DrawTurret("enigma-hardened-") {{
					for (int i = 0; i < 2; i++) {
						int f = i;
						parts.add(new RegionPart("-breech-" + (i == 0 ? "l" : "r")) {{
							progress = PartProgress.recoil;
							under = true;
							moveX = 10 * px * (f == 0 ? -1 : 1);
						}});
					}
					for (int i = 0; i < 2; i++) {
						int f = i;
						parts.add(new RegionPart("-cover-" + (i == 0 ? "l" : "r")) {{
							progress = PartProgress.recoil;
							under = true;
							moveX = 9 * px * (f == 0 ? -1 : 1);
							moveY = -9 * px;
						}});
					}
				}};
			}
		};

		chasm = new ItemTurret("chasm"){
			@Override
			public void init() {
				super.init();
				fogRadius = 2;
			}
			{
				size = 3;
				health = 4500;
				requirements(Category.turret, with(molybdenum, 75, ruthenium, 50, caesium, 30));
				researchCost = with(molybdenum, 170, irtran, 50, ruthenium, 100);

				reload = 2 * s;
				range = 200;
				targetAir = false;

				minWarmup = 0.375f;
				recoil = 12 * px;
				cooldownTime = 0.5f * s;

				outlineColor = EniPal.outline;
				squareSprite = false;
				shootSound = Sounds.artillery;
				shoot.shots = 5;
				inaccuracy = 15f;
				velocityRnd = 0.2F;

				ammo(
					caesium, new ArtilleryBulletType(2f, 50, "shell"){{
						hitEffect = EFx.caesiumExplosion;
						knockback = 0.8f;
						lifetime = 80f;
						width = height = 14f;
						collidesTiles = false;
						ammoMultiplier = 4f;
						splashDamageRadius = 45f * 0.75f;
						splashDamage = 150;
						backColor = trailColor = EniPal.caesium;
						frontColor = EniPal.caesiumLight;

						status = StatusEffects.blasted;
					}}
				);
				drawer = new DrawTurret("enigma-hardened-") {{
					parts.add(new RegionPart("-barrel"){{
						progress = PartProgress.recoil;
						moveY = -6 * px;
					}});
					for (int i = 0; i < 2; i++) {
						parts.add(new RegionPart("-panel-" + (i == 0 ? "l" : "r")) {{
							progress = PartProgress.warmup;
							under = true;
							moveY = -7 * px;
						}});
					}
					for (int i = 0; i < 2; i++) {
						int f = i;
						parts.add(new RegionPart("-stabilizer-" + (i == 0 ? "l" : "r")) {{
							progress = PartProgress.recoil;
							moveRot = 30 * (f == 0 ? -1 : 1);
						}});
					}
				}};

		}};

		//drills
		vacuumDrill = new PolymorphBurstDrill("vacuum-drill"){{
			size = 2;
			health = 1000;
			requirements(Category.production, with(molybdenum, 20));
			researchCost = with(molybdenum, 55);

			arrows = 8;
			arrowColor = therma.color;
			baseArrowColor = EniPal.outline;
			tier = 1;

			consumed = new PolymorphPowerStack(therma, 37.5f/s);
			drillTime = 5*s;
			hardnessDrillMultiplier = s;
		}};

		perforationDrill = new PolymorphBurstDrill("perforation-drill"){{
			size = 4;
			health = 5000;
			requirements(Category.production, with(molybdenum, 90, irtran, 33));
			researchCost = with(molybdenum, 540, irtran, 133);

			arrows = 6;
			arrowColor = ion.color;
			baseArrowColor = EniPal.outline;
			tier = 2;
			itemCapacity = 40;

			consumed = new PolymorphPowerStack(ion, 1000f/s);
			drillTime = 4*s;
			hardnessDrillMultiplier = 0.5f*s;

			squareSprite = false;
		}};

		//distribution
		encasedConveyor = new ShadedConveyor("encased-conveyor"){
			@Override
			public void init(){
				super.init();
				junctionReplacement = filter;
			}
			{
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

		filter = new ItemFilter("filter"){{
			size = 1;
			health = 200;
			requirements(Category.distribution, with(molybdenum, 8));
			researchCost = with(molybdenum, 64);
			squareSprite = false;
		}};
		thermobaricLauncher = new PolymorphMassDriver("thermobaric-launcher"){{
			size = 2;
			health = 1000;
			requirements(Category.distribution, with(molybdenum, 100, irtran, 10));
			researchCost = with(molybdenum, 250, irtran, 15);

			itemCapacity = 35;
			reload = 5 * s;
			consumed = new PolymorphPowerStack(therma, 12.5f/s);
			range = 20 * 32 * px;

			squareSprite = false;
			outlineColor = EniPal.outline;
		}};
		//liquid

		differentialPump = new PolymorphPump("differential-pump"){{
			size = 2;
			health = 1750;
			requirements(Category.liquid, with(ruthenium, 30, molybdenum, 55));
			researchCost = with(molybdenum, 130, ruthenium, 95);

			pumpAmount = 0.4f;

			liquidCapacity = 96;
			consumed = new PolymorphPowerStack(therma, 50/s);

			squareSprite = false;
			drawer = new DrawMulti(
					new DrawRegion("-bottom"),
					new DrawPumpLiquid(),
					new DrawDefault()
			);
		}};
		enameledConduit = new ShadedConduit("enameled-conduit"){
			@Override
			public void init(){
				super.init();
				junctionReplacement = enameledJunction;
				rotBridgeReplacement = enameledBridge;
			}{
			health = 250;
			requirements(Category.liquid, with(ruthenium, 1));
			researchCost = with(ruthenium, 20);

			liquidCapacity = 25;
		}};

		enameledRouter = new LiquidRouter("enameled-router"){{
			size = 1;
			health = 500;
			requirements(Category.liquid, with(ruthenium, 8, irtran, 2));
			researchCost = with(ruthenium, 100, irtran, 15);

			liquidCapacity = 250;
		}};

		enameledJunction = new LiquidJunction("enameled-junction"){{
			health = 500;
			requirements(Category.liquid, with(ruthenium, 5));
			researchCost = with(ruthenium, 75);
		}};

		enameledBridge = new DirectionLiquidBridge("enameled-bridge"){{
			health = 500;
			requirements(Category.liquid, with(ruthenium, 10, irtran, 4));
			researchCost = with(ruthenium, 150, irtran, 45);

			range = 4;
		}};

		//power
		polymorphNode = new PolymorphNode("polymorph-node"){{
			size = 1;
			health = 100;
			requirements(Category.power, with(molybdenum, 5));
			researchCost = with(molybdenum, 25);

			linkRange = 16;
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
			produced = new PolymorphPowerStack(therma, 125/s);

			squareSprite = false;
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawGlowRegion(){{ color = therma.color; suffix = "-glow"; }},
				new DrawDefault()
			);
		}};

		geothermalSiphon = new PolymorphThermalGenerator("geothermal-siphon"){{
			size = 3;
			health = 1500;
			requirements(Category.power, with(molybdenum, 160, ruthenium, 55));
			researchCost = with(molybdenum, 1260, ruthenium, 480);

			powerProduction = 0;
			consumeLiquid(distilledAmmonia, 5/s);

			produced = new PolymorphPowerStack(therma, 500/s);

			squareSprite = false;
			drawer = new DrawMulti(
					new DrawRegion("-bottom"),
					new DrawGlowRegion(){{ color = therma.color; suffix = "-glow"; }},
					new DrawDefault()
			);
		}};

		thermoelectricAdapter = new MultiPolymorphCrafter("thermoelectric-adapter"){{
			size = 2;
			health = 600;
			requirements(Category.power, with(molybdenum, 80, irtran, 40));
			researchCost = with(molybdenum, 320, irtran, 240);

			consumed = new Seq<>();
			consumed.add(new PolymorphPowerStack(therma, 250/s));

			produced = new Seq<>();
			produced.add(new PolymorphPowerStack(ion, 750/s));

			squareSprite = false;
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawDefault()
			);
		}};

		evaporationDynamo = new MultiPolymorphCrafter("evaporation-dynamo"){{
			size = 3;
			health = 1200;
			requirements(Category.power, with(molybdenum, 100, irtran, 40, ruthenium, 50));
			researchCost = with(molybdenum, 400, irtran, 200, ruthenium, 310);

			consumed = new Seq<>();
			consumed.add(new PolymorphPowerStack(therma, 250/s));
			consumeLiquid(distilledAmmonia, 10/s);

			produced = new Seq<>();
			produced.add(new PolymorphPowerStack(ion, 1250/s));

			squareSprite = false;
			updateEffect = EFx.ammoniaPlume;
			updateEffectChance = 0.25f;
			drawer = new DrawMulti(
					new DrawRegion("-bottom"),
					new DrawLiquidTile(distilledAmmonia, 2f),
					new DrawDefault(),
					new DrawBlades("-blade", 12, 360/s),
					new DrawRegion("-top")
			);
		}};

		polymorphSource = new PolymorphSource("polymorph-source"){{
			size = 1;
			requirements(Category.power, with());
			buildVisibility = BuildVisibility.sandboxOnly;
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

		rutheniumWall = new Wall("ruthenium-wall"){{
			size = 1;
			health = 1250;
			requirements(Category.defense, with(ruthenium, 6));
			researchCost = with(ruthenium, 128);
		}};

		largeRutheniumWall = new Wall("large-ruthenium-wall"){{
			size = 2;
			health = 1250 * 4;
			requirements(Category.defense, with(ruthenium, 24));
			researchCost = with(ruthenium, 512);
		}};
		fulgoriteFiberWall = new Wall("fulgorite-fiber-wall"){{
			size = 1;
			health = 950;
			requirements(Category.defense, with(fulgoriteFiber, 6));
			researchCost = with(fulgoriteFiber, 128);

			insulated = true;
			absorbLasers = true;
		}};

		largeFulgoriteFiberWall = new Wall("large-fulgorite-fiber-wall"){{
			size = 2;
			health = 950 * 4;
			requirements(Category.defense, with(fulgoriteFiber, 24));
			researchCost = with(fulgoriteFiber, 512);

			insulated = true;
			absorbLasers = true;
		}};

		gateway = new AutoDoor("gateway"){{
			size = 2;
			health = 750 * 4;
			requirements(Category.defense, with(molybdenum, 18, irtran, 6));
			researchCost = with(molybdenum, 256, irtran, 64);
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
			consumed = new PolymorphPowerStack(therma, 225/s);

			outputItem = new ItemStack(irtran, 3);

			squareSprite = false;
			drawer = new DrawMulti(
					new DrawRegion("-bottom"),
					new DrawGlowRegion(){{ color = therma.color; suffix = "-glow"; }},
					new DrawDefault()
			);
		}};

		thermalDistiller = new PolymorphCrafter("thermal-distiller"){{
			size = 3;
			health = 4000;
			requirements(Category.crafting, with(molybdenum, 90, ruthenium, 40, irtran, 10));
			researchCost = with(molybdenum, 630, ruthenium, 360, irtran, 81);

			consumeLiquid(causticAmmonia, 24/s);
			consumed = new PolymorphPowerStack(therma, 75/s);

			outputLiquid = new LiquidStack(distilledAmmonia, 20/s);

			squareSprite = false;
			drawer = new DrawMulti(
					new DrawRegion("-bottom"),
					new DrawLiquidTile(causticAmmonia, 2f),
					new DrawLiquidTile(distilledAmmonia, 2f),
					new DrawDefault(),
					new DrawGlowRegion(){{color = EniPal.redHotLight;}}
			);
		}};

		combustionReactor = new MultiPolymorphCrafter("combustion-reactor"){{
			size = 4;
			health = 4000;
			requirements(Category.crafting, with(molybdenum, 190, ruthenium, 110, irtran, 30));
			researchCost = with(molybdenum, 1080, ruthenium, 770, irtran, 220);
			rotate = true;

			consumeLiquid(distilledAmmonia, 15/s);
			consumed = Seq.with(new PolymorphPowerStack(ion, 600/s), new PolymorphPowerStack(therma, 66.66f/s));

			outputLiquids = new LiquidStack[]{new LiquidStack(oxidane, 10/s), new LiquidStack(nitrate, 2/s), new LiquidStack(nitroxide, 4/s)};

			squareSprite = false;
			rotateDraw = false;
			liquidOutputDirections = new int[]{3, 0, 1};
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawLiquidTile(nitrate, 2f),
				new DrawLiquidTile(nitroxide, 2f),
				new DrawLiquidTile(oxidane, 2f),
				new DrawArcSmelt(),
				new DrawDefault(),
				new DrawLiquidOutputs()
			);
		}};
		//units
		replicator = new PolymorphFabricator("replicator"){{
			size = 3;
			health = 1500;
			requirements(Category.units, with(molybdenum, 105, irtran, 35));
			researchCost = with(molybdenum, 500, irtran, 100);

			consumed = new PolymorphPowerStack(therma, 175/s);
			plans = Seq.with(
				new UnitPlan(scald, 30 * s, with(molybdenum, 35, irtran, 10)),
				new UnitPlan(needle, 45 * s, with(molybdenum, 20, ruthenium, 15))
			);

			squareSprite = false;
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
			squareSprite = false;
		}};
	}
}
