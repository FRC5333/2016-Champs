package frc.team5333.webui;

import frc.team5333.stronghold.core.configs.ReflectConfig;
import jaci.openrio.toast.lib.module.ToastModule;

public class WebUI extends ToastModule {

    @Override
    public String getModuleName() {
        return "5333-WebUI";
    }

    @Override
    public String getModuleVersion() {
        return "0.1.0-champs";
    }

    @Override
    public void prestart() {
        new ReflectConfig("5333-WebUI", WebUIConfig.class);
        WebHandler.init();
    }

    @Override
    public void start() { }
}
