package foodnow_tests.rest_assured.order_tests;

import foodnow.dto.order.OrderPaymentRequestDTO;
import foodnow.dto.order.OrderPaymentResponseDTO;
import foodnow_tests.rest_assured.TestBase;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("Заказы")
@Feature("Оплата заказа")
public class OrderPaymentTests extends TestBase {

    @BeforeMethod
    public void precondition() {
        loginUser(validUser, 200);
        createNewOrder();
        confirmOrder();
    }

    @Test
    @Story("Оплата заказа")
    @Description("Проверка оплаты заказа")
    @Severity(SeverityLevel.CRITICAL)
    public void payForOrderTest() {
        OrderPaymentRequestDTO requestDTO = OrderPaymentRequestDTO.builder()
                .id(Integer.parseInt(orderId))
                .paymentMethod("CREDIT_CARD")
                .build();

        OrderPaymentResponseDTO responseDTO = given()
                .contentType(ContentType.JSON)
                .header(AUTH, "Bearer " + TOKEN)
                .body(requestDTO)
                .when()
                .put("/order/paid/" + requestDTO.getId())
                .then()
                .statusCode(200)
                .extract().as(OrderPaymentResponseDTO.class);

        System.out.println("Payment URL: " + responseDTO.getPaymentUrl());

        System.out.println("Order payment confirmed: " + responseDTO);
    }
}
