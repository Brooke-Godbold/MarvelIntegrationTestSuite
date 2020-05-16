package utils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class RestUtils {

    public static ResponseEntity GetResponse(String url) {
        RestTemplate restTemplate = new RestTemplate();

        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException uriSyntaxException) {
            System.out.println("Exception Creating URI: " + uriSyntaxException);
        }

        return restTemplate.getForEntity(uri, String.class);
    }
}
