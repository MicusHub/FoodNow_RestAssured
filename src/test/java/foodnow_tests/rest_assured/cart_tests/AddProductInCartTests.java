package foodnow_tests.rest_assured.cart_tests;

import foodnow.dto.cart.CartItemRequestDTO;
import foodnow.dto.cart.CartItemResponseDTO;
import foodnow_tests.rest_assured.TestBase;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;

@Epic("Корзина")
@Feature("Добавление продукта в корзину")
public class AddProductInCartTests extends TestBase {

    @BeforeMethod
    public void precondition() {
        loginUser(validUser, 200);
    }

    @Test
    @Story("Добавление продукта в корзину")
    @Description("Проверка добавления случайного продукта в корзину")
    @Severity(SeverityLevel.CRITICAL)
    public void addItemToCartTest() {
        // Выбираем случайный продукт
        int[] productIds = {
                88, 89, 92, 99, 93, 67, 68, 69, 70,
                71, 72, 73, 74, 75, 76, 77, 78, 79,
                80, 83, 84, 85, 86, 87
        };
        Random random = new Random();
        productsIdsInCart = productIds[random.nextInt(productIds.length)];
        System.out.println("Selected product ID for test: " + productsIdsInCart);

        CartItemRequestDTO requestDTO = CartItemRequestDTO.builder()
                .productId(productsIdsInCart)
                .productQuantity(1)
                .build();

        CartItemResponseDTO responseDTO = given()
                .contentType(ContentType.JSON)
                .header(AUTH, "Bearer " + TOKEN)
                .body(requestDTO)
                .when()
                .post("/cart/" + requestDTO.getProductId())
                .then()
                .statusCode(200)
                .extract()
                .response()
                .as(CartItemResponseDTO.class);

        Assert.assertNotNull(responseDTO.getId(), "ID элемента в корзине не должен быть null");
        Assert.assertEquals(responseDTO.getProductId(), requestDTO.getProductId(), "ID продукта не совпадает");
        Assert.assertTrue(responseDTO.getSum() > 0, "Сумма должна быть больше 0");

        productIdInCart = responseDTO.getProductId();

        System.out.println("Product added to cart successfully with ID: " + productIdInCart);
    }

    @AfterMethod
    public void postCondition() {
        cartClearById();
    }
}
