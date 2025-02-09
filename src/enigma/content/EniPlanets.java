package enigma.content;

import arc.graphics.Color;
import arc.math.geom.Vec3;
import arc.util.noise.Simplex;
import enigma.graphics.EniPal;
import mindustry.content.Items;
import mindustry.content.Planets;
import mindustry.game.Team;
import mindustry.graphics.g3d.HexMesh;
import mindustry.graphics.g3d.HexSkyMesh;
import mindustry.graphics.g3d.MultiMesh;
import mindustry.graphics.g3d.SunMesh;
import mindustry.maps.generators.PlanetGenerator;
import mindustry.maps.planet.ErekirPlanetGenerator;
import mindustry.type.Planet;
import mindustry.world.Block;
import mindustry.world.meta.Env;

public class EniPlanets {

	public static Planet tarcomron, keslomin;

	public static void load(){
		tarcomron = new Planet("tarcomron", null, 2.5f){{
			bloom = true;
			accessible = false;

			meshLoader = () -> new SunMesh(
					this, 4,
					5, 0.3, 1.7, 1.2, 1,
					1.1f,
					EniPal.redHotDark,
					EniPal.redHot,
					EniPal.redHotLight
			);
		}};
		keslomin = new Planet("keslomin", tarcomron, 1f, 2){{
			generator = new PlanetGenerator() {
				@Override
				public float getHeight(Vec3 position) {
					return Math.max(Simplex.noise3d(seed, 8, 0.7f, 1f/0.9f, 10f + position.x, 10f + position.y, 10f + position.z)*2 - 1, 0.7f)-0.2f;
				}

				@Override
				public Color getColor(Vec3 position) {
					Block result = EniBlocks.ammoniaDeeper;
					float h = Simplex.noise3d(seed, 8, 0.7f, 1f/0.9f, 10f + position.x, 10f + position.y, 10f + position.z);

					if(h > 0.7f){
						float variation = Simplex.noise3d(seed + 10, 8, 0.7f, 1f/0.7f, 10f + position.x, 10f + position.y, 10f + position.z);
						if(variation > 0.5f){
							result = EniBlocks.biotite;
						} else if(variation > 0.25f){
							result = EniBlocks.granite;
						} else {
							result = EniBlocks.periclaseFloor;
						}
					} else if(h > 0.6f) {
						result = EniBlocks.ammonia;
					} else if(h > 0.5f) {
						result = EniBlocks.ammoniaDeep;
					}

					return result.mapColor;
				}
			};
			meshLoader = () -> new HexMesh(this, 5);
			cloudMeshLoader = () -> new MultiMesh(
					new HexSkyMesh(this, 7, 0.15f, 0.14f, 5, Color.valueOf("827f33").a(0.25f), 3, 0.42f, 1f, 0.43f),
					new HexSkyMesh(this, 8, 0.4f, 0.15f, 5, Color.valueOf("989345").a(0.25f), 3, 0.42f, 1.1f, 0.44f),
					new HexSkyMesh(this, 9, 0.6f, 0.16f, 5, Color.valueOf("b0a859").a(0.25f), 3, 0.42f, 1.2f, 0.45f)
			);
			alwaysUnlocked = true;
			landCloudColor = Color.valueOf("b0a859");
			atmosphereColor = Color.valueOf("b0a859");
			defaultEnv = Env.terrestrial | Env.oxygen;
			startSector = 81;
			atmosphereRadIn = 0.02f;
			atmosphereRadOut = 0.3f;
			orbitSpacing = 2f;
			totalRadius += 2.6f;
			lightSrcTo = 0.5f;
			lightDstFrom = 0.2f;
			clearSectorOnLose = true;
			defaultCore = EniBlocks.coreModule;
			iconColor = Color.valueOf("656520");
			enemyBuildSpeedMultiplier = 0.4f;

			//TODO disallowed for now
			allowLaunchToNumbered = false;

			//TODO SHOULD there be lighting?
			updateLighting = false;

			ruleSetter = r -> {
				r.waveTeam = Team.crux;
				r.placeRangeCheck = false;
				r.showSpawns = true;
				r.fog = true;
				r.staticFog = true;
				r.lighting = false;
				r.coreDestroyClear = true;
				r.onlyDepositCore = true;
			};
			unlockedOnLand.add(EniBlocks.coreModule);
			hiddenItems.addAll(Items.serpuloItems).addAll(Items.erekirItems).removeAll(EniItems.keslominItems);
		}};

		Planets.serpulo.hiddenItems.addAll(EniItems.keslominItems).removeAll(Items.serpuloItems);
		Planets.erekir.hiddenItems.addAll(EniItems.keslominItems).removeAll(Items.erekirItems);
	}
}
