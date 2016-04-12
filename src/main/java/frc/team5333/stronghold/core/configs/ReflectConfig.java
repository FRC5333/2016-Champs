package frc.team5333.stronghold.core.configs;

import jaci.openrio.toast.lib.module.ModuleConfig;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectConfig extends ModuleConfig {

    Class clazz;

    public ReflectConfig(String name, Class clazz) {
        super(name);
        this.clazz = clazz;
        reload();
    }

    @Override
    public void reload() {
        super.reload();
        reload_reflection();
    }

    public void reload_reflection() {
        if (clazz == null) return;
        try {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                field.set(null, this.get(field.getName().toLowerCase(), field.get(null)));
            }

            List<String> classList = new ArrayList<>();
            for (Class<?> clazz2 : clazz.getClasses()) {
                reflect_class(clazz2, classList);
            }
        } catch (IllegalAccessException e) { }
    }

    public void reflect_class(Class<?> clazz, List<String> runningList) throws IllegalAccessException {
        runningList.add(clazz.getSimpleName().toLowerCase());

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String name = String.join(".", runningList) + "." + field.getName().toLowerCase();
            field.set(null, this.get(name, field.get(null)));
        }

        for (Class<?> clazz2 : clazz.getClasses()) {
            reflect_class(clazz2, runningList);
        }
        runningList.remove(runningList.size() - 1);
    }
}
