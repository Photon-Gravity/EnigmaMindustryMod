package enigma.content;

import mindustry.type.SectorPreset;

import static enigma.content.EniPlanets.keslomin;

public class EniSectors {

	public static SectorPreset interphase, scattershot, subductionBarrier;

	public static void load(){
		//PlanetDialog.debugSelect = true     saved for reference
		interphase = new SectorPreset("interphase", keslomin, 13);
		interphase.captureWave = 12;
		interphase.alwaysUnlocked = true;

		scattershot = new SectorPreset("scattershot", keslomin, 68);
		subductionBarrier = new SectorPreset("subduction-barrier", keslomin, 71);
	}
}
