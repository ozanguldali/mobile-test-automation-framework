package util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Assert;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static util.LoggingUtil.LOGGER;
import static util.PropertiesUtil.PROJECT_DIR;
import static util.PropertiesUtil.SLASH;

public class ParserUtil {

    public static JsonObject jsonFileParsing(String jsonFile) {

        JsonParser jsonParser = new JsonParser();
        Object object = null;

        try {

            FileReader fileReader = new FileReader( PROJECT_DIR
                                                    + SLASH + "src" + SLASH + "test" + SLASH + "resources" + SLASH + "config"
                                                    + SLASH + jsonFile + ".json" );
            object = jsonParser.parse( fileReader );

        } catch ( FileNotFoundException fne) {

            LOGGER.info( "\t" + fne.getMessage() + "\t\n" );
            Assert.fail( "\t" + fne.getMessage() + "\t\n" );

        }

        JsonObject jsonObject = (JsonObject) object;

        assert jsonObject != null;
        return jsonObject;

    }

}
