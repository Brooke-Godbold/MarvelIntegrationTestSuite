package integration;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.http.ResponseEntity;
import utils.ApiUtils;
import utils.RestUtils;
import utils.WebdriverUtils;
import webpage.CharacterComicsPOM;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GetCharacterComicsTest {

    protected WebDriver driver;

    @BeforeEach
    void Setup() {
        driver = WebdriverUtils.getDriver();
    }

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

        driver.get(CharacterComicsPOM.pageBaseUrl + characterId + "/" + characterName);

        //TODO: Variants button is not showing up in chromedriver. For comparison, go to webpage in browser and see
        // Variants Checkbox, and then run this Test and see that the Checkbox is not rendered
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.className(CharacterComicsPOM.showVariants))).click();
        List<WebElement> showMore = driver.findElements(By.className(CharacterComicsPOM.showMore));
        if (showMore.size()!=0) {
            wait.until(ExpectedConditions.elementToBeClickable(showMore.get(0))).click();
        }

        assertThat(driver.findElements(By.className(CharacterComicsPOM.comicItem)).size()).isEqualTo(expectedComics);
    }

    @AfterEach
    void TearDown() {
        driver.quit();
    }
}
