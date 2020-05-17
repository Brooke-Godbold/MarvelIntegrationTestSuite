package utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

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

    private static RestTemplate restTemplate() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
        converter.setObjectMapper(mapper);

        return new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
    }
}
