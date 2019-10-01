package helper;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Assert;

import static util.LoggingUtil.LOGGER;

public abstract class EnvironmentHelper {

    protected static JsonElement getJsonElement(JsonObject jsonObject, String key) {

        try {

            return jsonObject.get( key );

        } catch (Exception e) {

            LOGGER.info( String.format( "\tThe key [%s] could NOT been extracted, because { error : [%s] }\t\n", key, e.getMessage() ) );
            Assert.fail( String.format( "\tThe key [%s] could NOT been extracted, because { error : [%s] }\t\n", key, e.getMessage() ) );

        }

        return new JsonElement() {

            @Override
            public JsonElement deepCopy() {

                return null;

            }

        };

    }

}
