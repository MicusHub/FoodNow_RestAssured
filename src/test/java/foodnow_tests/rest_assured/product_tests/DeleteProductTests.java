package foodnow_tests.rest_assured.product_tests;

import foodnow_tests.rest_assured.TestBase;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("Продукты")
@Feature("Удаление продукта")
public class DeleteProductTests extends TestBase {

    @BeforeMethod
    public void preCondition() {
        loginUser(validUser, 200);
        productId = addProduct();
    }

    @Test
    @Story("Удаление продукта")
    @Description("Успешное удаление продукта")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteProductTest() {

        int getStatusCode = given()
                .contentType(ContentType.JSON)
                .header(AUTH, "Bearer " + TOKEN)
                .when()
                .get("/products/" + productId)
                .then()
                .extract().statusCode();
        Assert.assertEquals(getStatusCode, 200, " Error message");

        int deleteStatusCode = given()
                .contentType(ContentType.JSON)
                .header(AUTH, "Bearer " + TOKEN)
                .when()
                .delete("/products/" + productId)
                .then()
                .extract().statusCode();

        Assert.assertEquals(deleteStatusCode, 204, "Failed to delete product!");

        System.out.println("Product with ID " + productId + " successfully deleted.");
    }
}

