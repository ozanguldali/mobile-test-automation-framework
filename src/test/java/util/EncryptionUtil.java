package util;

import com.google.gson.JsonObject;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.util.Base64;
import java.util.HashMap;

public class EncryptionUtil {

    public static final String sdkPrivateKey = ParserUtil.jsonFileParsing( "key-bases/sdkPrivateKey" ).getAsJsonObject().toString();

    public static String toBase64URL(JsonObject jsonObject) {

        return Base64.getUrlEncoder().encodeToString( jsonObject.toString().getBytes( StandardCharsets.UTF_8 ) );

    }

    public static void generateEncryptionMap(HashMap<Object, Object> hashMap, ECPrivateKey privateKey, ECPublicKey peerPublicKey, byte[] partyUInfo, byte[] partyVInfo, String algoName, int algoKeyBitLen) {

        hashMap.put( "ecPrivateKey", privateKey );
        hashMap.put( "ecPublicKey", peerPublicKey );
        hashMap.put( "partyUInfo", partyUInfo );
        hashMap.put( "partyVInfo", partyVInfo );
        hashMap.put( "algoName", algoName );
        hashMap.put( "algoKeyBitLen", algoKeyBitLen );

    }

//    public static byte[] generateIV(JsonObject jsonObject, int ivSize, String rule) {
//
//        StringBuilder buffer = EncryptionHelper.setBufferJWE( jsonObject, rule );
//
//        byte[] iv = new byte[ ivSize ];
//
//        for ( int i = 0; i < buffer.length(); i++ ) {
//
//            char charAt = buffer.reverse().charAt( i );
//            iv[ i ] = ( byte ) Character.getNumericValue( charAt );
//
//        }
//
//        for ( int i = 3; i < iv.length ; i++ ) {
//
//            iv[ i ] = 0;
//
//        }
//
//        return iv;
//
//    }

    public static void generateJWEElements(HashMap<Object, Object> jweMap, HashMap<Object, Object> encryptionMap) {

        jweMap.put( "ecPrivateKey", encryptionMap.get( "ecPrivateKey" ) );
        jweMap.put( "ecPublicKey", encryptionMap.get( "ecPublicKey" ) );
        jweMap.put( "partyUInfo", encryptionMap.get( "partyUInfo" ) );
        jweMap.put( "partyVInfo", encryptionMap.get( "partyVInfo" ) );
        jweMap.put( "algoName", encryptionMap.get( "algoName" ) );
        jweMap.put( "algoKeyBitLen", encryptionMap.get( "algoKeyBitLen" ) );

    }

}
