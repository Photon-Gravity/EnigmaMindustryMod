package enigma.content;

import arc.graphics.Color;
import enigma.graphics.EFx;
import enigma.graphics.EniPal;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.LightningBulletType;
import mindustry.gen.LegsUnit;
import mindustry.gen.Sounds;
import mindustry.gen.UnitEntity;
import mindustry.gen.UnitWaterMove;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;
import mindustry.type.Weapon;

import static enigma.util.Consts.px;
import static enigma.util.Consts.s;

public class EniUnits {
	public static UnitType
		pion, kaon, nucleon,
		scald, sear, vaporize, annul,
		             enrage,

		riot, revolt, resist, regicide,

		needle, pike, javelin, glaive,


		arrow, quiver;

	public static void load(){
		pion = new UnitType("pion"){{
			constructor = LegsUnit::create;
			speed = 1f;
			drag = 0.11f;
			hitSize = 54 * px;
			rotateSpeed = 3f;
			health = 750;
			armor = 4f;
			legStraightness = 0.3f;
			stepShake = 0f;
			outlineColor = EniPal.outline;

			mineSpeed = 6f;
			mineTier = 2;
			buildSpeed = 1f;
			itemCapacity = 50;
			fogRadius = 0f;

			legCount = 6;
			legLength = (37 + 42)*px;
			lockLegBase = true;
			legContinuousMove = false;
			legExtension = -11 * px;
			legBaseOffset = 3f;
			legMaxLength = 1.1f;
			legMinLength = 0.2f;
			legLengthScl = 0.96f;
			legForwardScl = 1.1f;
			legGroupSize = 3;
			rippleScale = 0.2f;

			legMoveSpace = 1f;
			allowLegStep = true;
			hovering = true;
			legPhysicsLayer = false;

			shadowElevation = 0.1f;
			groundLayer = Layer.legUnit - 1f;
		}};

		scald = new UnitType("scald"){{
			targetAir = true;
			speed = 1.20f;
			drag = 0.12f;
			hitSize = 53 * px;
			armor = 2;
			health = 700;
			accel = 0.2f;
			faceTarget = false;
			rotateSpeed = 4f;
			constructor = UnitWaterMove::create;
			outlineColor = EniPal.outline;

			weapons.add(new Weapon("enigma-scald-cannon"){{
				x = 18*px;
				y = -13 * px;
				shootY = 19 * px;

				mirror = true;
				rotate = true;
				rotateSpeed = 120/s;

				reload = 0.33f*s;

				shootSound = Sounds.shoot;

				bullet = new BasicBulletType(4, 125, "mine-bullet"){{
					height = 8;
					width = 8;

					trailLength = 4;
					trailWidth = 2;

					lifetime = 0.5f*s;

					backColor = trailColor = EniPal.irtran;
					frontColor = EniPal.irtranLight;
				}};
			}});

			//abilities.add(new LiquidSpeedBoostAbility());
		}};

		needle = new UnitType("needle"){{
			targetAir = false;
			speed = 1.5f;
			accel = 0.08f;
			drag = 0.04f;
			hitSize = 46 * px;
			health = 800;
			faceTarget = false;
			rotateSpeed = 4f;
			constructor = UnitEntity::create;
			outlineColor = EniPal.outline;
			flying = true;
			weapons.add(new Weapon(){{
				mirror = false;
				rotate = false;

				reload = 0.5f * s;
				shootCone = 180;
				ejectEffect = Fx.none;
				shootSound = Sounds.mineDeploy;
				x = shootY = 0f;
				bullet = new BasicBulletType(){{
					sprite = "enigma-square";

					lifetime = 1.5f * s;

					hitEffect = EFx.needleDischarge;
					shootEffect = Fx.none;
					collidesTiles = false;
					collides = false;
					keepVelocity = false;
					shrinkX = shrinkY = 0.5f;
					width = height = 64 * px;

					backColor = EniPolymorphTypes.ion.color;
					frontColor = Color.white;

					hitSound = Sounds.spark;

					spin = 3f;

					speed = 0f;
					splashDamageRadius = 55f;
					splashDamage = 100f;
					collidesAir = false;

					fragBullets = 3;
					fragRandomSpread = 360;
					fragBullet = new LightningBulletType(){{
						lightningColor = hitColor = EniPolymorphTypes.ion.color;
						lightningLength = 5;
						lightningLengthRand = 7;

						lightningType = new BulletType(0.0001f, 0f){{
							lifetime = Fx.lightning.lifetime;
							despawnEffect = Fx.none;
							status = StatusEffects.shocked;
							statusDuration = 10f;
							hittable = false;
							collidesTeam = true;
						}};
					}};
				}};
			}});
		}};

		arrow = new UnitType("arrow"){{
			targetAir = true;
			speed = 1.75f;
			accel = 0.08f;
			drag = 0.04f;
			hitSize = 46 * px;
			health = 900;
			faceTarget = false;
			rotateSpeed = 4f;
			constructor = UnitEntity::create;
			outlineColor = EniPal.outline;
			flying = true;
			maxRange = 1;
			crashDamageMultiplier = 3f;

			weapons.add(new Weapon(){{
				mirror = false;
				rotate = false;

				shootOnDeath = true;

				reload = 24f;
				shootCone = 180f;
				ejectEffect = Fx.none;
				shootSound = Sounds.bang;
				x = shootY = 0f;
				bullet = new BulletType(){{
					shootEffect = EFx.caesiumExplosionBig;
					collidesTiles = false;
					collides = false;
					status = StatusEffects.blasted;

					rangeOverride = 30f;
					hitEffect = Fx.pulverize;
					speed = 0f;
					splashDamageRadius = 55f;
					instantDisappear = true;
					splashDamage = 200f;
					killShooter = true;
					hittable = false;
					collidesAir = true;
				}};
			}});
		}};
	}
}
