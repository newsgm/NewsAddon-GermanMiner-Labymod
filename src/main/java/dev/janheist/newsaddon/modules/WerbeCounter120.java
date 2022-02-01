package dev.janheist.newsaddon.modules;

import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.ModuleCategoryRegistry;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;

public class WerbeCounter120 extends SimpleModule {
    public static int allowed = 3;
    public static String end = "0";


    @Override
    public String getDisplayName() {
        return "120-Werbung";
    }

    @Override
    public String getDisplayValue() {

        int ei = Integer.parseInt("" + end);
        if(ei <= 0) {
            return "Erlaubt";
        } else {
            return "Erlaubt in " + end + " Sekunden";
        }

    }

    @Override
    public String getDefaultValue() {
        return String.valueOf(0);
    }

    @Override
    public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.MAP);
    }

    @Override
    public void loadSettings() {

    }

    @Override
    public String getSettingName() {
        return "120-Counter";
    }

    @Override
    public String getControlName() {
        return "120-Counter";
    }

    @Override
    public String getDescription() {
        return "Zeigt an, ob Werbung geschaltet werden darf (120)";
    }

    @Override
    public int getSortingId() {
        return 0;
    }

    @Override
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_INFO;
    }
}
