package foodnow_tests.rest_assured.product_tests;

import foodnow.dto.product.ProductDTO;
import foodnow_tests.rest_assured.TestBase;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("Продукты")
@Feature("Получение всех продуктов")
public class AllProductsTests extends TestBase {
    @Test
    @Story("Получение списка продуктов")
    @Description("Проверка получения списка всех продуктов")
    @Severity(SeverityLevel.NORMAL)
    public void getAllProductsTest() {
        ProductDTO[] products = given()
                .contentType(ContentType.JSON)
                .header(AUTH, "Bearer " + TOKEN)
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .extract().response().as(ProductDTO[].class);

        Assert.assertTrue(products.length > 0, "Список продуктов пуст!");

        for (ProductDTO product : products) {
            System.out.println("Продукт: " + product);
        }
    }
}
