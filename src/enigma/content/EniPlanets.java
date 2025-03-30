package enigma.content;

import arc.graphics.Color;
import arc.math.geom.Vec3;
import arc.util.noise.Simplex;
import enigma.graphics.EniPal;
import mindustry.content.Items;
import mindustry.content.Planets;
import mindustry.game.Schematic;
import mindustry.game.Schematics;
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
		tarcomron = new Planet("tarcomron", null, 10f){{
			bloom = true;
			accessible = false;

			orbitSpacing = 36f;

			meshLoader = () -> new SunMesh(
					this, 6,
					5, 0.3, 4.5f, 1.2, 1,
					1.1f,
					EniPal.redHotDark,
					EniPal.redHotDark.cpy().mul(0.5f).add(EniPal.redHot.cpy().mul(0.5f)),
					EniPal.redHot,
					EniPal.redHotLight.cpy().mul(0.5f).add(EniPal.redHot.cpy().mul(0.5f)),
					EniPal.redHotLight
			);
		}};
		keslomin = new Planet("keslomin", tarcomron, 1f, 2){{
			generator = new PlanetGenerator() {
				@Override
				public float getHeight(Vec3 position) {
					float noise = Simplex.noise3d(seed, 8, 0.7f, 1f/1.1f, 10f + position.x, 10f + position.y, 10f + position.z);
					return Math.max(-Math.abs(noise) + 0.3f, 0);
				}

				@Override
				public Color getColor(Vec3 position) {
					Block result = EniBlocks.ammoniaDeeper;
					float noise = Simplex.noise3d(seed, 8, 0.7f, 1f/1.1f, 10f + position.x, 10f + position.y, 10f + position.z);
					float h = -Math.abs(noise) + 0.3f;

					if(h > 0f){
						float variation = Simplex.noise3d(seed + 10, 8, 0.7f, 1f/0.7f, 10f + position.x, 10f + position.y, 10f + position.z);
						if(variation > 0.5f){
							result = EniBlocks.biotite;
						} else if(variation > 0.25f){
							result = EniBlocks.granite;
						} else {
							result = EniBlocks.periclaseFloor;
						}
					} else if(h > -0.05f) {
						result = EniBlocks.ammonia;
					} else if(h > -0.1f) {
						result = EniBlocks.ammoniaDeep;
					}

					return result.mapColor;
				}
				{
					defaultLoadout = Schematics.readBase64("bXNjaAF4nGNgYWBhZmDJS8xNZeBKzi9Kzc1PKc1JZeBOSS1OLsosKMnMz2NgYGDLSUxKzSlmYIqOZWQQSs3LTM9N1AUp14WqZ2BgBCEgAQCLuxTc");
				}
			};
			meshLoader = () -> new HexMesh(this, 5);
			cloudMeshLoader = () -> new MultiMesh(
					new HexSkyMesh(this, 7, 0.15f, 0.14f, 5, Color.valueOf("827f33").a(0.25f), 3, 0.42f, 1f, 0.43f),
					new HexSkyMesh(this, 9, 0.6f, 0.16f, 5, Color.valueOf("b0a859").a(0.25f), 3, 0.42f, 1.2f, 0.45f)
			);
			alwaysUnlocked = true;
			landCloudColor = Color.valueOf("b0a859");
			atmosphereColor = Color.valueOf("b0a859");
			defaultEnv = Env.terrestrial | Env.oxygen;
			startSector = 13;
			atmosphereRadIn = 0.02f;
			atmosphereRadOut = 0.3f;
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
