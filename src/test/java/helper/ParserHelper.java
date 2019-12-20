package helper;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static util.LoggingUtil.LOGGER;

public class ParserHelper {

//    public static Element getJsoupElement(Document document, String key, String by) {
//
//        Element element = null;
//
//        try {
//
//            switch ( by ) {
//                case "id":
//                    element = document.getElementById( key );
//                    break;
////                case "name":
////                    element = document.getElementByTag( key );
////                    break;
////                case "xpath":
////                    by = By.xpath(pageElement);
////                    break;
////                case "className":
////                    by = By.className(pageElement);
////                    break;
////                case "cssSelector":
////                    by = By.cssSelector(pageElement);
////                    break;
////                case "tagName":
////                    by = By.tagName(pageElement);
////                    break;
////                case "linkText":
////                    by = By.linkText(pageElement);
////                    break;
////                case "partialLinkText":
////                    by = By.partialLinkText(pageElement);
////                    break;
////                default:
////                    throw new Error("Not a valid selector type: %s" + decisionVariable);
//            }
//
//        } catch ( AssertionError e ) {
//            e.printStackTrace();
//        }
//
//        return element;
//
//    }

    public static Object getJsonPathValue(JsonPath jsonPath, String key) {

        try {

            if ( !( jsonPath.get( "rReq" ) == null ) )
                return jsonPath.get( "rReq" + "." + key );

            return jsonPath.get( key );

        } catch (Exception e) {

            LOGGER.fatal( String.format( "\tThe key [ %s ] could NOT be gotten from jsonPath\t\n", key ) );
            Assert.fail( String.format( "\tThe key [ %s ] could NOT be gotten from jsonPath\t\n", key ) );

        }

        return null;

    }

}
