package integration;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.http.ResponseEntity;
import utils.RestUtils;
import utils.ApiUtils;

public class GetCharacterComicsTest {

    protected WebDriver driver;

    @Test
    void GetCharactersApiComicPropertyTotalMatchesComicsTotalInTheUi() {
        ResponseEntity responseEntity = RestUtils.GetResponse(ApiUtils.getCharactersApi() + ApiUtils.getTimestampKeyHash());
        String responseBody = responseEntity.getBody().toString();

        JsonObject jsonResponse = new JsonParser().parse(responseBody).getAsJsonObject();
        JsonArray resultList = jsonResponse.get("data").getAsJsonObject().get("results").getAsJsonArray();

        JsonObject character = resultList.get(0).getAsJsonObject();
        String characterId = character.get("id").toString();
        String characterName = character.get("name").toString();
        int expectedComics = character.get("comics").getAsJsonObject().get("available").getAsInt();

        //Go to URL, count comics in DOM
        //Assert they are same
        driver = new HtmlUnitDriver();
        driver.get("http://marvel.com/comics/characters/" + characterId + "/" + characterName);

        driver.findElement(By.className("filterTabHeadItems.filterResultsText")).getText();

        if (driver != null) {
            driver.quit();
        }
    }
}
