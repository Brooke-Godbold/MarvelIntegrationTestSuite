package utils;

import org.apache.commons.codec.digest.DigestUtils;

public class ApiUtils {

    private static final String BASE_URI = "http://gateway.marvel.com";
    private static final String CHARACTERS_API = "/v1/public/characters?";
    private static final String TIMESTAMP = "1";
    private static final String PUBLIC_API_KEY = "PUBLIC_API_KEY";
    private static final String PRIVATE_API_KEY = "PRIVATE_API_KEY";

    public static String getCharactersApi() {
        return BASE_URI + CHARACTERS_API;
    }

    public static String getTimestampKeyHash() {
        return getTimestamp() + "&" + getKey() + "&" + getHash();
    }

    public static String getTimestamp() {
        return "ts=" + TIMESTAMP;
    }

    public static String getKey() {
        return "apikey=" + System.getenv(PUBLIC_API_KEY);
    }

    public static String getHash() {
        return "hash=" + generateHash();
    }

    private static String generateHash() {
        return DigestUtils.md5Hex(TIMESTAMP + System.getenv(PRIVATE_API_KEY) + System.getenv(PUBLIC_API_KEY));
    }
}
