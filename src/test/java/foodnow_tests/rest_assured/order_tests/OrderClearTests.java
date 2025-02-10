package foodnow_tests.rest_assured.order_tests;

import foodnow.dto.order.OrderClearResponseDTO;
import foodnow_tests.rest_assured.TestBase;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("Заказы")
@Feature("Очистка заказа")
public class OrderClearTests extends TestBase {

    @BeforeMethod
    public void precondition() {
        loginUser(validUser, 200);
        createNewOrder();
    }

    @Test
    @Story("Очистка заказа")
    @Description("Проверка очистки заказа")
    @Severity(SeverityLevel.NORMAL)
    public void clearOrderTest() {
        String responseMessage = given()
                .contentType(ContentType.JSON)
                .header(AUTH, "Bearer " + TOKEN)
                .when()
                .delete("/order/" + orderId)
                .then()
                .statusCode(200)
                .extract()
                .asString();

        Assert.assertEquals(responseMessage, "Order cleared", "Message mismatch");

        System.out.println("Order with ID " + orderId + " successfully cleared: " + responseMessage);
    }
}
