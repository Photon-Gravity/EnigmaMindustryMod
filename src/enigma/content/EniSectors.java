package enigma.content;

import mindustry.type.SectorPreset;

import static enigma.content.EniPlanets.keslomin;

public class EniSectors {

	public static SectorPreset interphase;

	public static void load(){
		interphase = new SectorPreset("interphase", keslomin, 81);
		interphase.captureWave = 12;
	}
}
