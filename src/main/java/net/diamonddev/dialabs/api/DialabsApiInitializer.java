package net.diamonddev.dialabs.api;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DialabsApiInitializer implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger("Dialabs API");

    @Override
    public void onInitialize() {
        long s = System.currentTimeMillis();

        long e = System.currentTimeMillis();
        LOGGER.info("Dialabs API has loaded. ({} ms)", e-s);
    }
}
