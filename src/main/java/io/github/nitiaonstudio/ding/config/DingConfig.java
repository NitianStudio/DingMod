package io.github.nitiaonstudio.ding.config;

import io.github.nitiaonstudio.ding.Ding;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;


@Config(name = Ding.MODID)
public class DingConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    public Linkage linkage = new Linkage();

    @ConfigEntry.Gui.CollapsibleObject
    public Base base = new Base();

    public static class Base {
        @ConfigEntry.BoundedDiscrete(min = 100, max = 1000000000)
        public int fix_all_factor = 700;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 1000000000)
        public int mine_speed_factor = 100;
        @ConfigEntry.BoundedDiscrete(min = 200, max = 1000000000)
        public int attack_factor = 200;
        @ConfigEntry.BoundedDiscrete(min = 300, max = 1000000000)
        public int fix_in_forge_anvil_block_mod_factor = 300;
        @ConfigEntry.BoundedDiscrete(min = 100, max = 1000000000)
        public int min_fix_in_forge_anvil_block = 100;
        @ConfigEntry.BoundedDiscrete(min = 30, max = 100)
        public int min_fix_self_in_forge_anvil_block = 100;

        @ConfigEntry.BoundedDiscrete(min = 100, max = 1000000000)
        public int thunderstorm_factor = 500;

        @ConfigEntry.BoundedDiscrete(min = 30000, max = Integer.MAX_VALUE)
        public int max_forge_anvil_value = 1000000000;

    }

    public static class Linkage {
        @ConfigEntry.BoundedDiscrete(min = 1000, max = 1000000000)
        public int magnet_radius = 10000;
    }
}
