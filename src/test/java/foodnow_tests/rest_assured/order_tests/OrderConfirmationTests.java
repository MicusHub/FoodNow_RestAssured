package foodnow_tests.rest_assured.order_tests;

import foodnow.dto.order.OrderConfirmationRequestDTO;
import foodnow.dto.order.OrderConfirmationResponseDTO;
import foodnow_tests.rest_assured.TestBase;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("Заказы")
@Feature("Подтверждение заказа")
public class OrderConfirmationTests extends TestBase {

    @BeforeMethod
    public void precondition() {
        loginUser(validUser, 200);
        createNewOrder();
    }

    @Test
    @Story("Подтверждение заказа")
    @Description("Проверка подтверждения заказа")
    @Severity(SeverityLevel.CRITICAL)
    public void confirmOrderTest() {
        OrderConfirmationRequestDTO requestDTO = OrderConfirmationRequestDTO.builder()
                .id(Integer.parseInt(orderId))
                .address("123 Main Street, New York, NY")
                .deliveryTime("2024-11-28T08:47:00")
                .paymentMethod("CREDIT_CARD")
                .build();

        OrderConfirmationResponseDTO responseDTO = given()
                .contentType(ContentType.JSON)
                .header(AUTH, "Bearer " + TOKEN)
                .body(requestDTO)
                .when()
                .put("/order/confirmed")
                .then()
                .statusCode(200)
                .extract().as(OrderConfirmationResponseDTO.class);

        Assert.assertEquals(responseDTO.getAddress(), "123 Main Street, New York, NY", "Address mismatch");
        Assert.assertEquals(responseDTO.getOrderStatus(), "CONFIRMED", "Order status mismatch");
        Assert.assertEquals(responseDTO.getPaymentMethod(), "CREDIT_CARD", "Payment method mismatch");

        System.out.println("Order confirmed: " + responseDTO);
    }

    @AfterMethod
    public void postCondition() {
        clearOrder();
    }
}
