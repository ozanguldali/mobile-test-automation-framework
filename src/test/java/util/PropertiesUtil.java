package util;

import helper.PropertiesHelper;
import org.openqa.selenium.remote.DesiredCapabilities;

public final class PropertiesUtil extends PropertiesHelper {

    public static void setPropDesiredCapabilities(DesiredCapabilities desiredCapabilities ,String key , String device) {

        String subKey = key.substring(key.indexOf('.'));
        String value = setProperty(key, device);

        desiredCapabilities.setCapability( subKey , value );

    }

}
