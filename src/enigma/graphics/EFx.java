package enigma.graphics;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import enigma.content.EniPolymorphTypes;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;

import static arc.graphics.g2d.Draw.alpha;
import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.lineAngle;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;
import static arc.math.Mathf.rand;
import static enigma.content.EniLiquids.distilledAmmonia;
import static enigma.util.Consts.s;

public class EFx {
	public static final Effect
			ammoniaPlume = new Effect(10*s, e -> {
		color(distilledAmmonia.color.cpy().mul(0.5f).add(Color.white.cpy().mul(0.5f)));

		float z = Draw.z();
		Draw.z(111);

		rand.setSeed(e.id);

		e.scaled(e.lifetime * rand.random(0.3f, 1f), b -> {
			alpha(b.fout() * 0.8f);
			Fill.circle(e.x, e.y, 3f * b.fin() + 0.5f + Math.min(b.fin() * 8, 1));
		});
		Draw.z(z);
	}),
			caesiumExplosion = new Effect(22, e -> {
		color(EniPal.caesiumLight);

		e.scaled(6, i -> {
			stroke(3f * i.fout());
			Lines.circle(e.x, e.y, 3f + i.fin() * 15f);
		});

		color(Color.gray);

		randLenVectors(e.id, 5, 2f + 23f * e.finpow(), (x, y) -> {
			Fill.circle(e.x + x, e.y + y, e.fout() * 4f + 0.5f);
		});

		color(EniPal.caesium);
		stroke(e.fout());

		randLenVectors(e.id + 1, 4, 1f + 23f * e.finpow(), (x, y) -> {
			lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * 3f);
		});

		Drawf.light(e.x, e.y, 45f, EniPal.caesium, 0.8f * e.fout());
	}),
			caesiumExplosionBig = new Effect(22, e -> {
		color(EniPal.caesiumLight);

		e.scaled(6, i -> {
			stroke(3f * i.fout());
			Lines.circle(e.x, e.y, 3f + i.fin() * 30f);
		});

		color(Color.gray);

		randLenVectors(e.id, 10, 2f + 40f * e.finpow(), (x, y) -> {
			Fill.circle(e.x + x, e.y + y, e.fout() * 4f + 0.5f);
		});

		color(EniPal.caesium);
		stroke(e.fout());

		randLenVectors(e.id + 1, 8, 1f + 40f * e.finpow(), (x, y) -> {
			lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * 3f);
		});

		Drawf.light(e.x, e.y, 90f, EniPal.caesium, 0.8f * e.fout());
	}),
			needleDischarge = new Effect(22, e -> {
		color(EniPolymorphTypes.ion.color);

		e.scaled(6, i -> {
			stroke(3f * i.fout());
			Lines.square(e.x, e.y, 3f + i.fin() * 30f, 90 * e.fin());
		});

		color(Color.gray);

		randLenVectors(e.id, 10, 2f + 40f * e.finpow(), (x, y) -> {
			Fill.circle(e.x + x, e.y + y, e.fout() * 4f + 0.5f);
		});

		color(EniPal.caesium);
		stroke(e.fout());

		randLenVectors(e.id + 1, 8, 1f + 40f * e.finpow(), (x, y) -> {
			lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * 3f);
		});

		Drawf.light(e.x, e.y, 90f, EniPal.caesium, 0.8f * e.fout());
	})
			;
}
