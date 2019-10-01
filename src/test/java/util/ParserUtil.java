package util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Assert;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static util.LoggingUtil.LOGGER;

public class ParserUtil {

    public static JsonObject jsonFileParsing(String jsonFile) {

        JsonParser jsonParser = new JsonParser();
        Object object = null;

        try {

            FileReader fileReader = new FileReader( EnvironmentUtil.PROJECT_DIR
                                                    + EnvironmentUtil.SLASH + "src" + EnvironmentUtil.SLASH + "test" + EnvironmentUtil.SLASH + "resources" + EnvironmentUtil.SLASH + "config"
                                                    + EnvironmentUtil.SLASH + jsonFile + ".json" );
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
