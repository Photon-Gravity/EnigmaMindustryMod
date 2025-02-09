package enigma;

import arc.*;
import arc.util.*;
import enigma.content.*;
import enigma.custom.polymorph.PolymorphSystemUpdater;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;

public class EniMain extends Mod{

    public EniMain(){
        Log.info("Loaded Enigma constructor.");
    }


    @Override
    public void init() {
        super.init();

        PolymorphSystemUpdater.init();
    }

    @Override
    public void loadContent(){
        Log.info("Loading Enigma content.");

        EniPolymorphTypes.load();

        EniItems.load();
        EniUnits.load();

        EniBlocks.load();

        EniPlanets.load();

        EniSectors.load();

        KeslominTechTree.load();
    }
}
