package enigma.content;

import enigma.graphics.EniPal;
import mindustry.Vars;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.LegsUnit;
import mindustry.gen.Sounds;
import mindustry.gen.UnitWaterMove;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;
import mindustry.type.Weapon;

import static enigma.util.Consts.px;
import static enigma.util.Consts.s;

public class EniUnits {
	public static UnitType
		pion, kaon,
		scald, sear, evaporate, annihilate;

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
			mineTier = 1;
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
			speed = 0.9f;
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
		}};
	}
}
