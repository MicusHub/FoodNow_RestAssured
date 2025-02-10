package foodnow_tests.rest_assured.order_tests;

import foodnow.dto.order.OrderDTO;
import foodnow_tests.rest_assured.TestBase;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("Заказы")
@Feature("Создание нового заказа")
public class CreateNewOrderTests extends TestBase {

    @BeforeMethod
    public void precondition() {
        loginUser(validUser, 200);
    }

    @Test
    @Story("Создание заказа")
    @Description("Проверка создания нового заказа")
    @Severity(SeverityLevel.CRITICAL)
    public void createNewOrderTest() {
        OrderDTO orderDTO = given()
                .contentType(ContentType.JSON)
                .header(AUTH, "Bearer " + TOKEN)
                .body("{}")
                .when()
                .post("/order")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .as(OrderDTO.class);

        Assert.assertNotNull(orderDTO.getId(), "Order ID is missing!");
        Assert.assertEquals(orderDTO.getOrderStatus(), "PENDING", "Order status does not match!");
        Assert.assertNotNull(orderDTO.getUserId(), "User ID is missing!");

        orderId = String.valueOf(orderDTO.getId());
        System.out.println("Order with ID " + orderId + " successfully created.");
    }

    @AfterMethod
    public void postCondition() {
        clearOrder();
    }
}
