package foodnow_tests.rest_assured.login_tests;

import foodnow.dto.login.AuthRequestDTO;
import foodnow.dto.login.AuthResponseDTO;
import foodnow_tests.rest_assured.TestBase;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("Авторизация")
@Feature("Логин")
public class LoginTests extends TestBase {

    public static final AuthRequestDTO validUser = AuthRequestDTO.builder()
            .username("danileremenkoschool@gmail.com")
            .password("Slovonet123!")
            .build();

    private static final AuthRequestDTO requestDTONegative = AuthRequestDTO.builder()
            .username("danileremenkoschool@gmail.com")
            .password("adadadadadad")
            .build();

    @Test
    @Story("Успешный логин")
    @Description("Проверка успешного логина с валидными данными")
    @Severity(SeverityLevel.BLOCKER)
    public static void loginSuccessTest() {
        AuthResponseDTO dto = given()
                .contentType(ContentType.JSON)
                .body(validUser)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract().as(AuthResponseDTO.class);

        System.out.println("Access Token: " + dto.getToken().getAccessToken());
        System.out.println("User Email: " + dto.getUser().getEmail());
    }

    @Test
    @Story("Негативный логин")
    @Description("Проверка логина с невалидным паролем")
    @Severity(SeverityLevel.CRITICAL)
    public void loginNegativeTest() {
        String responseBody = given()
                .contentType(ContentType.JSON)
                .body(requestDTONegative)
                .when()
                .post(RestAssured.basePath + "/auth/login")
                .then()
                .assertThat()
                .statusCode(403)
                .extract()
                .response()
                .asString();

        System.out.println("Negative login response: " + responseBody);
    }
}



