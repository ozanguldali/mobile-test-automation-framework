package util;

import org.testng.Assert;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static util.LoggingUtil.LOGGER;

public class HasMapUtil<K, V> extends HashMap<K, V> implements Map<K, V>, Cloneable, Serializable  {

    public static HasMapUtil<String, Object> context = new HasMapUtil<>();
    public static HasMapUtil<String, Object> config = new HasMapUtil<>();

    @Override
    public V get(Object key) {

        V v = null;

        try {

            v = super.get( key );

        } catch ( Exception e ) {

            LOGGER.fatal(String.format("\tThe key [ %s ] could NOT be gotten from hashMap\t\n", key));
            Assert.fail(String.format("\tThe key [ %s ] could NOT be gotten from hashMap\t\n", key));

        }

        return v;

    }

    @Override
    public V put(K key, V value) {

        V v = null;

        try {

            v = super.put(key, value);

        }  catch (Exception e) {

            LOGGER.fatal(String.format("\tThe key [ %s ] could NOT be set with value [%s] from hashMap\t\n", key, value));
            Assert.fail(String.format("\tThe key [ %s ] could NOT be set with value [%s] from hashMap\t\n", key, value));

        }

        return v;
    }

    @Override
    public boolean containsKey(Object key) {
        return super.containsKey(key);
    }

    @Override
    public void clear() {

        try {

            if ( !context.isEmpty() ) {

                super.clear();
                LoggingUtil.LOGGER.info("\tContext is successfully cleaned.\t\n");

            } else {

                LoggingUtil.LOGGER.warn("\tContext is already empty.\t\n");

            }

        } catch (Exception var2) {

            LoggingUtil.LOGGER.fatal("\tContext could NOT be cleaned; hence, current context values may cause problems...\t\n");

        }

    }

    public String getAsString(Object key) {

        try {

            return super.get( key ).toString();

        } catch ( Exception e ) {

            LOGGER.fatal( "\tThe key [ " + key + " ] could NOT convert to String\t\n" );
            Assert.fail( "\tThe key [ " + key + " ] could NOT convert to String\t\n" );

            return null;

        }

    }

    public static void setConfig(HasMapUtil<String, Object> config) {
        HasMapUtil.config = config;
    }

    public static void setContext(HasMapUtil<String, Object> context) {
        HasMapUtil.context = context;
    }
}