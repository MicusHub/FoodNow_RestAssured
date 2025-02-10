package foodnow_tests.rest_assured.cart_tests;

import foodnow.dto.cart.CartItemDTO;
import foodnow_tests.rest_assured.TestBase;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

@Epic("Корзина")
@Feature("Получение всех продуктов в корзине")
public class AllProductsInCart extends TestBase {

    @BeforeMethod
    public void precondition() {
        loginUser(validUser, 200);
    }

    @Test
    @Story("Получение списка продуктов")
    @Description("Проверка получения списка всех продуктов в корзине")
    @Severity(SeverityLevel.NORMAL)
    public void getAllProductsInCartTest() {
        System.out.println("Токен для авторизации: " + TOKEN);
        System.out.println("Отправляем запрос GET /cart");

        Response response = given()
                .contentType(ContentType.JSON)
                .header(AUTH, "Bearer " + TOKEN)
                .when()
                .get("/cart")
                .then()
                .extract()
                .response();

        if (response.getStatusCode() == 404) {
            String message = response.jsonPath().getString("message");
            Assert.assertEquals(message, "There are no products in the cart", "Сообщение о пустой корзине некорректно");
            System.out.println("Корзина пуста. Сообщение сервера: " + message);
        } else {
            Assert.assertEquals(response.getStatusCode(), 200, "Некорректный статус ответа");
            List<CartItemDTO> cartItems = response.jsonPath().getList(".", CartItemDTO.class);

            Assert.assertNotNull(cartItems, "Список товаров не должен быть null");

            if (cartItems.size() > 0) {
                CartItemDTO firstItem = cartItems.get(0);
                Assert.assertNotNull(firstItem.getId(), "ID товара не должен быть null");
                Assert.assertTrue(firstItem.getProductQuantity() > 0, "Количество продукта должно быть больше 0");
                Assert.assertTrue(firstItem.getSum() > 0, "Сумма должна быть больше 0");
            }

            System.out.println("Товары в корзине: " + cartItems);
        }
    }
}

