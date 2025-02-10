package foodnow_tests.rest_assured.order_tests;

import foodnow.dto.order.OrderDTO;
import foodnow_tests.rest_assured.TestBase;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

@Epic("Заказы")
@Feature("Мои заказы")
public class MyOrdersTests extends TestBase {

    @BeforeMethod
    public void precondition() {
        loginUser(validUser, 200);
    }

    @Test
    @Story("Получение списка моих заказов")
    @Description("Проверка получения списка моих заказов")
    @Severity(SeverityLevel.NORMAL)
    public void getMyOrdersTest() {
        List<OrderDTO> orders = given()
                .contentType(ContentType.JSON)
                .header(AUTH, "Bearer " + TOKEN)
                .when()
                .get("/order/my")
                .then()
                .statusCode(200)
                .extract().jsonPath().getList(".", OrderDTO.class);

        Assert.assertNotNull(orders, "Список заказов не должен быть null");

        Assert.assertTrue(orders.size() > 0, "Список заказов не должен быть пустым");

        OrderDTO firstOrder = orders.get(0);
        Assert.assertNotNull(firstOrder.getId(), "ID заказа не должен быть null");
        Assert.assertNotNull(firstOrder.getOrderStatus(), "Статус заказа не должен быть null");
        Assert.assertTrue(firstOrder.getTotalSum() > 0, "Сумма заказа должна быть больше 0");

        System.out.println("Список заказов: " + orders);
        System.out.println("Первый заказ: ID = " + firstOrder.getId() +
                ", Статус = " + firstOrder.getOrderStatus() +
                ", Сумма = " + firstOrder.getTotalSum());
    }
}
