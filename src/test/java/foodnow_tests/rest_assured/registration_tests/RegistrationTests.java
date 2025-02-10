package foodnow_tests.rest_assured.registration_tests;

import foodnow.dto.registration.RegistrationRequestDTO;
import foodnow.dto.registration.RegistrationResponseDTO;
import foodnow_tests.rest_assured.TestBase;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("Регистрация")
@Feature("Регистрация пользователя")
public class RegistrationTests extends TestBase {

    @Test
    @Story("Успешная регистрация")
    @Description("Проверка успешной регистрации пользователя")
    @Severity(SeverityLevel.BLOCKER)
    public void registerUserPositiveTest() {
        RegistrationRequestDTO requestDTO = RegistrationRequestDTO.builder()
                .firstName("Name1")
                .lastName("Last1")
                .email("email" + System.currentTimeMillis() + "@test.com")
                .password("Password123")
                .phone("+123" + System.currentTimeMillis())
                .build();

        RegistrationResponseDTO responseDTO = given()
                .contentType(ContentType.JSON)
                .body(requestDTO)
                .when()
                .post("/users/register")
                .then()
                .statusCode(200)
                .extract().response().as(RegistrationResponseDTO.class);

        Assert.assertNotNull(responseDTO.getId(), "ID пользователя не должен быть null");
        Assert.assertEquals(responseDTO.getFirstName(), requestDTO.getFirstName(), "Имя не совпадает");
        Assert.assertEquals(responseDTO.getLastName(), requestDTO.getLastName(), "Фамилия не совпадает");
        Assert.assertEquals(responseDTO.getEmail(), requestDTO.getEmail(), "Email не совпадает");
        Assert.assertEquals(responseDTO.getPhone(), requestDTO.getPhone(), "Телефон не совпадает");

        System.out.println("User registered successfully with ID: " + responseDTO.getId());
    }


    @Test
    @Story("Негативная регистрация")
    @Description("Проверка регистрации пользователя с пустым телом запроса")
    @Severity(SeverityLevel.NORMAL)
    public void registerUserNegativeTest() {
        String emptyRequestBody = "{}";

        String responseBody = given()
                .contentType(ContentType.JSON)
                .body(emptyRequestBody)
                .when()
                .post("/users/register")
                .then()
                .statusCode(403)
                .extract().response().asString();
        System.out.println("Response Body: " + responseBody);
    }
}
