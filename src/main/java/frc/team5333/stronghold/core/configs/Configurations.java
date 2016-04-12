package frc.team5333.stronghold.core.configs;

import jaci.openrio.toast.lib.module.ModuleConfig;

public class Configurations {

    public static ModuleConfig config, joyConfig;

    public static void init() {
        config = new ReflectConfig("5333-Stronghold", ConfigMap.class);
        joyConfig = new ReflectConfig("Joysticks", JoyMap.class);
    }

}
