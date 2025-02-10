package foodnow_tests.rest_assured.product_tests;

import foodnow.dto.product.ProductRequestDTO;
import foodnow_tests.rest_assured.TestBase;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;

@Epic("Продукты")
@Feature("Добавление продукта")
public class AddProductTests extends TestBase {


    @BeforeMethod
    public void precondition() {
        loginUser(validUser, 200);
    }

    @Test
    @Story("Добавление нового продукта")
    @Description("Успешное добавление нового продукта")
    @Severity(SeverityLevel.CRITICAL)
    public void addNewProductTest() {
        ProductRequestDTO requestDTO = ProductRequestDTO.builder()
                .title("Organic Apple")
                .price(1.99)
                .productCode("PROD" + System.currentTimeMillis())
                .minQuantity("50g")
                .description("Fresh organic apples sourced directly from the farm")
                .photoLink("https://example.com/images/organic-apple.jpg")
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

        JsonPath jsonPath = new JsonPath(response);
        productId = jsonPath.getString("id");
        System.out.println("Product with ID " + productId + " successfully added.");
    }

    @Test
    @Story("Негативный тест добавления продукта")
    @Description("Попытка добавить продукт без обязательного поля 'title'")
    @Severity(SeverityLevel.NORMAL)
    public void addProductNegativeTest() {
        ProductRequestDTO requestDTO = ProductRequestDTO.builder()
                .price(1.99)
                .productCode("PROD" + System.currentTimeMillis())
                .minQuantity("50g")
                .description("Fresh organic apples sourced directly from the farm")
                .photoLink("https://example.com/images/organic-apple.jpg")
                .build();

        String responseBody = given()
                .contentType(ContentType.JSON)
                .header(AUTH, "Bearer " + TOKEN)
                .body(requestDTO)
                .when()
                .post("/products")
                .then()
                .statusCode(400)
                .extract().response().asString();

        System.out.println("Response Body: " + responseBody);
    }

    @AfterMethod
    public void postCondition() {
        if (productId != null) {
            deleteProductById(productId);
            productId = null;
        }
    }
}


