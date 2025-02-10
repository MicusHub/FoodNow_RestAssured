package foodnow_tests.rest_assured.cart_tests;

import foodnow.dto.cart.ClearCartResponseDTO;
import foodnow_tests.rest_assured.TestBase;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("Корзина")
@Feature("Очистка корзины")
public class ClearCartTests extends TestBase {

    @BeforeMethod
    public void precondition() {
        loginUser(validUser, 200);
    }

    @Test
    @Story("Очистка корзины")
    @Description("Проверка очистки корзины")
    @Severity(SeverityLevel.NORMAL)
    public void clearCartTest() {
        String responseMessage = given()
                .contentType(ContentType.JSON)
                .header(AUTH, "Bearer " + TOKEN)
                .when()
                .delete("/cart")
                .then()
                .statusCode(200)
                .extract().asString();

        Assert.assertTrue(responseMessage.equals("Cart is empty") || responseMessage.equals("Cart cleared successfully"),
                "Сообщение об очистке корзины не совпадает");

        System.out.println("Корзина успешно очищена: " + responseMessage);
    }
}
