package foodnow_tests.httpclient;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import foodnow.dto.login.AuthRequestDTO;
import foodnow.dto.login.TokenDTO;
import io.qameta.allure.*;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

@Epic("Авторизация")
@Feature("Логин")
public class LoginHttpClientTests {

    @Test
    @Story("Успешный логин (строка)")
    @Description("Проверяет успешный логин с использованием строки JSON")
    @Severity(SeverityLevel.BLOCKER)
    public void LoginSuccessTest() throws IOException {
        Response response = Request.Post("https://oyster-app-hck73.ondigitalocean.app/api/auth/login")
                .bodyString("{\n" +
                        "  \"username\": \"danileremenkoschool@gmail.com\",\n" +
                        "  \"password\": \"Slovonet123!\"\n" +
                        "}", ContentType.APPLICATION_JSON)
                .execute();
        System.out.println(response);
        String responseJson = response.returnContent().toString();
        System.out.println(responseJson);

        JsonElement element = JsonParser.parseString(responseJson);
        JsonElement token = element.getAsJsonObject().get("token");
        System.out.println(token);
        Assert.assertNotNull(token, "Token should not be null");
    }

    @Test
    @Story("Успешный логин (DTO)")
    @Description("Проверяет успешный логин с использованием DTO")
    @Severity(SeverityLevel.BLOCKER)
    public void LoginSuccessTestWithDto() throws IOException {
        Gson gson = new Gson();
        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .username("danileremenkoschool@gmail.com")
                .password("Slovonet123!")
                .build();
        Response response = Request.Post("https://oyster-app-hck73.ondigitalocean.app/api/auth/login")
                .bodyString(gson.toJson(requestDTO), ContentType.APPLICATION_JSON)
                .execute();
        String responseJson = response.returnContent().toString();
        System.out.println(responseJson);
        TokenDTO authResponseDTO = gson.fromJson(responseJson, TokenDTO.class);
        String token = authResponseDTO.getAccessToken();
        System.out.println(token);
        //Assert.assertNotNull(token, "Token should not be null");

    }
}
