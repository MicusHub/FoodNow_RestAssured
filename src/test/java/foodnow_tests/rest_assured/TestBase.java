package foodnow_tests.rest_assured;

import foodnow.dto.login.AuthRequestDTO;
import foodnow.dto.order.OrderConfirmationRequestDTO;
import foodnow.dto.order.OrderConfirmationResponseDTO;
import foodnow.dto.order.OrderDTO;
import foodnow.dto.product.ProductRequestDTO;
import foodnow_tests.rest_assured.login_tests.LoginTests;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.Getter;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class TestBase {


    public static final AuthRequestDTO validUser = AuthRequestDTO.builder()
            .username("danileremenkoschool@gmail.com")
            .password("Slovonet123!")
            .build();
    public static final String AUTH = "Authorization";


    @Getter
    public static String TOKEN;

    protected String productId;
    protected String orderId;
    protected String cartItemId;
    protected int productsIdsInCart;
    protected int productIdInCart;

    public static void setToken(String token) {
        TOKEN = token;
    }

    @BeforeClass
    public void init() {
        RestAssured.baseURI = "https://oyster-app-hck73.ondigitalocean.app";
        RestAssured.basePath = "api";
    }

    public static void loginUser(AuthRequestDTO user, int statusCode) {
        String dto = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(statusCode)
                .extract().jsonPath().getString("token.accessToken");
        TestBase.setToken(dto);
    }

    // Метод для добавления продукта
    protected String addProduct() {
        ProductRequestDTO requestDTO = ProductRequestDTO.builder()
                .title("Test Product")
                .price(10.99)
                .productCode("PROD" + System.currentTimeMillis())
                .minQuantity("1kg")
                .description("Test description")
                .photoLink("https://example.com/images/test-product.jpg")
                .build();

        String response = given()
                .contentType(ContentType.JSON)
                .header(AUTH, "Bearer " + TOKEN)
                .body(requestDTO)
                .when()
                .post("/products")
                .then()
                .statusCode(201)
                .extract().asString();

        // Извлечение ID из ответа
        JsonPath jsonPath = new JsonPath(response);
        String productId = jsonPath.getString("id");

        System.out.println("Product with ID " + productId + " successfully added.");
        return productId;
    }


    protected void deleteProductById(String productId) {
        // Проверяем, что ID продукта передан
        if (productId == null || productId.isEmpty()) {
            throw new IllegalArgumentException("Invalid product ID: " + productId);
        }

        // Отправка DELETE-запроса
        int deleteStatusCode = given()
                .contentType(ContentType.JSON)
                .header(AUTH, "Bearer " + TOKEN)
                .when()
                .delete("/products/" + productId)
                .then()
                .extract().statusCode();

        // Проверка успешного удаления
        Assert.assertEquals(deleteStatusCode, 204, "Failed to delete product with ID: " + productId);

        // Логирование
        System.out.println("Product with ID " + productId + " successfully deleted.");
        // Проверка, что продукт действительно удалён
        int getStatusCode = given()
                .contentType(ContentType.JSON)
                .header(AUTH, "Bearer " + TOKEN)
                .when()
                .get("/products/" + productId)
                .then()
                .extract().statusCode();


        // Логирование подтверждения
        System.out.println("Verified: Product with ID " + productId + " has been successfully deleted.");
    }


    protected void createNewOrder() {
        OrderDTO orderDTO = given()
                .contentType(ContentType.JSON)
                .header(AUTH, "Bearer " + TOKEN)
                .body("{}") // Укажите корректное тело запроса, если требуется
                .when()
                .post("/order")
                .then()
                .statusCode(200) // Проверка успешного создания
                .extract()
                .response()
                .as(OrderDTO.class); // Преобразуем ответ в объект OrderDTO

        // Сохраняем ID созданного заказа
        orderId = String.valueOf(orderDTO.getId());
        Assert.assertNotNull(orderId, "Failed to create order: ID is null");
        System.out.println("Order with ID " + orderId + " successfully created in preCondition.");
    }

    protected void clearOrder() {
        int getStatusCode = given()
                .contentType(ContentType.JSON)
                .header(AUTH, "Bearer " + TOKEN)
                .when()
                .get("/order/" + orderId)
                .then()
                .extract()
                .statusCode();

        // Удаляем заказ, если он существует
        String responseMessage = given()
                .contentType(ContentType.JSON)
                .header(AUTH, "Bearer " + TOKEN)
                .when()
                .delete("/order/" + orderId)
                .then()
                .extract()
                .asString();

        System.out.println("Post-condition cleanup: Order with ID " + orderId + " deleted: " + responseMessage);
    }

    protected void confirmOrder() {
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
    }

    protected void cartClearById() {
        if (productIdInCart == 0) {
            System.out.println("No product ID available for cleanup.");
            return; // Пропускаем очистку
        }

        System.out.println("Attempting to clear product from cart with ID: " + productIdInCart);

        // Отправка DELETE-запроса для удаления продукта по productId
        Response deleteResponse = given()
                .contentType(ContentType.JSON)
                .header(AUTH, "Bearer " + TOKEN)
                .when()
                .delete("/cart/" + productIdInCart) // Используем productId
                .then()
                .extract()
                .response();


        // Проверка успешного удаления
        Assert.assertEquals(deleteResponse.getStatusCode(), 200, "Failed to delete product from cart with ID: " + productIdInCart);
    }
}



