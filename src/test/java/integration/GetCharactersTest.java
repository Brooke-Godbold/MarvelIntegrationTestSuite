package integration;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import utils.RestUtils;
import utils.ApiUtils;
import utils.AssertUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GetCharactersTest {

    @Test
    void CanGetCharactersApiReturnsAllPropertiesForAllCharactersTest() {
        ResponseEntity responseEntity = RestUtils.GetResponse(ApiUtils.getCharactersApi() + ApiUtils.getTimestampKeyHash());
        String responseBody = responseEntity.getBody().toString();

        JsonObject jsonResponse = new JsonParser().parse(responseBody).getAsJsonObject();
        JsonArray resultList = jsonResponse.get("data").getAsJsonObject().get("results").getAsJsonArray();

        resultList.forEach(
                result -> AssertUtils.getExpectedCharacterFields().forEach(
                        field -> assertThat(result.getAsJsonObject().has(field)).isTrue()
                )
        );
    }

    @Test
    void ShouldRespondWithAuthorizationErrorWhenMissingApiKeyParameter() {
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            RestUtils.GetResponse(ApiUtils.getCharactersApi() + ApiUtils.getTimestamp() + "&" + ApiUtils.getHash());
        });

        assertThat((exception).getRawStatusCode()).isEqualTo(409);
    }

    @Test
    void ShouldRespondWithAuthorizationErrorWhenMissingHashParameter() {
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            RestUtils.GetResponse(ApiUtils.getCharactersApi() + ApiUtils.getTimestamp() + "&" + ApiUtils.getKey());
        });

        assertThat((exception).getRawStatusCode()).isEqualTo(409);
    }

    @Test
    void ShouldRespondWithAuthorizationErrorWhenMissingTimestampParameter() {
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            RestUtils.GetResponse(ApiUtils.getCharactersApi() + ApiUtils.getKey() + "&" + ApiUtils.getHash());
        });

        assertThat((exception).getRawStatusCode()).isEqualTo(409);
    }

    @Test
    void ShouldRespondWithAuthorizationErrorWhenInvalidHash() {
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            RestUtils.GetResponse(ApiUtils.getCharactersApi() + ApiUtils.getTimestamp() + "&" + ApiUtils.getKey() + "&hash=invalidHash");
        });

        assertThat((exception).getRawStatusCode()).isEqualTo(401);
    }
}
