package com.jarhax.poweradapters;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler {

    private static Configuration config = null;

    public static int maxCapacity = 5000;
    public static int maxInput = 500;
    public static int maxOutput = 500;

    public static int worthTesla = 25;
    public static int worthRedstoneFlux = 25;
    public static int worthForgeUnits = 25;
    public static int worthMinecraftJoules = 100;

    public static void initConfig (File file) {

        config = new Configuration(file);
        syncConfig();
    }

    public static void syncConfig () {

        maxCapacity = config.getInt("converterCapacity", Configuration.CATEGORY_GENERAL, 5000, 1, Integer.MAX_VALUE, "The maximum amount of power the converter can hold.");
        maxInput = config.getInt("converterInputRate", Configuration.CATEGORY_GENERAL, 500, 1, Integer.MAX_VALUE, "The maximum amount of power the converter can accept");
        maxOutput = config.getInt("converterOutputRate", Configuration.CATEGORY_GENERAL, 500, 1, Integer.MAX_VALUE, "The maximum amount of power the converter can release");

        worthTesla = getConversionCost("Tesla", 25);
        worthRedstoneFlux = getConversionCost("RF", 25);
        worthForgeUnits = getConversionCost("FU", 25);
        worthMinecraftJoules = getConversionCost("MJ", 100);

        if (config.hasChanged()) {

            config.save();
        }
    }

    private static int getConversionCost (String name, int defaultValue) {

        return config.getInt("convert" + name, Configuration.CATEGORY_GENERAL, defaultValue, 1, 1024, "The amount of internal power one unit of " + name + " is worth.");
    }
}