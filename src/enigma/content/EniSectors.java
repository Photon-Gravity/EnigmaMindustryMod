package enigma.content;

import mindustry.type.SectorPreset;

import static enigma.content.EniPlanets.keslomin;

public class EniSectors {

	public static SectorPreset interphase, scattershot;

	public static void load(){
		//PlanetDialog.debugSelect = true     saved for reference
		interphase = new SectorPreset("interphase", keslomin, 81);
		interphase.captureWave = 12;

		scattershot = new SectorPreset("scattershot", keslomin, 65);
	}
}
