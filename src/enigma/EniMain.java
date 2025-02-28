package enigma;

import arc.util.Log;
import enigma.content.*;
import enigma.custom.polymorph.PolymorphUpdater;
import mindustry.mod.Mod;

public class EniMain extends Mod{

    public EniMain(){
        Log.info("Loaded Enigma constructor.");
    }


    @Override
    public void init() {
        super.init();

        PolymorphUpdater.init();
    }

    @Override
    public void loadContent(){
        Log.info("Loading Enigma content.");

        EniPolymorphTypes.load();

        EniItems.load();
        EniLiquids.load();
        EniUnits.load();

        EniBlocks.load();

        EniPlanets.load();

        EniSectors.load();

        KeslominTechTree.load();
    }
}
