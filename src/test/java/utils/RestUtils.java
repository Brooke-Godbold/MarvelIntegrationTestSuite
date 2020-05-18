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

        //TODO: Below code should return Paged Responses as a Collection, but currently returns empty Content
        /*
        ResponseEntity<PagedResources<JsonObject>> response = restTemplate.exchange(
                uri, HttpMethod.GET, null, new ParameterizedTypeReference<PagedResources<JsonObject>>() {}
        );
        PagedResources<JsonObject> resBody = response.getBody();
        Collection<JsonObject> collection = resBody.getContent();
         */

        return restTemplate.getForEntity(uri, String.class);
    }

}
