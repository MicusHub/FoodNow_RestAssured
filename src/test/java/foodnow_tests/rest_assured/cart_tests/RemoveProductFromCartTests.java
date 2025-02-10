package foodnow_tests.rest_assured.cart_tests;

import foodnow_tests.rest_assured.TestBase;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("Корзина")
@Feature("Удаление продукта из корзины")
public class RemoveProductFromCartTests extends TestBase {
    @BeforeMethod
    public void precondition() {
        loginUser(validUser, 200);
    }
    @Test
    @Story("Удаление продукта из корзины")
    @Description("Проверка удаления продукта из корзины")
    @Severity(SeverityLevel.NORMAL)
    public void removeProductFromCartTest() {
        if (productIdInCart == 0) {
            System.out.println("No product ID available for cleanup.");
            return; // Пропускаем очистку
        }

        System.out.println("Attempting to clear product from cart with ID: " + productIdInCart);

        Response deleteResponse = given()
                .contentType(ContentType.JSON)
                .header(AUTH, "Bearer " + TOKEN)
                .when()
                .delete("/cart/" + productIdInCart)
                .then()
                .extract()
                .response();

        Assert.assertEquals(deleteResponse.getStatusCode(), 200, "Failed to delete product from cart with ID: " + productIdInCart);
    }
}
