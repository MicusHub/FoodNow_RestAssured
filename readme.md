📌 FoodNow REST Assured: API Testing Project

📖 Project Overview

FoodNow REST Assured is an API testing project designed to ensure the reliability and functionality of the FoodNow web application's backend. The project employs REST Assured for automated API testing and integrates TestNG and Allure for structured test execution and reporting.

✨ Key Features

✅ Automated testing of FoodNow API endpoints

✅ Verification of HTTP response codes, headers, and payloads

✅ Data validation using JSON parsing

✅ Integration with TestNG for structured test execution

✅ Allure Reporting for enhanced test result visualization

🛠️ Technologies Used

Testing Framework: REST Assured 5.5.0, TestNG 7.10.2

Build Tool: Gradle

Logging & Reporting: Allure 2.23.0

JSON Processing: Gson 2.11.0

HTTP Client: Apache HttpClient 4.5.14

Programming Language: Java 17

📂 Project Structure

FoodNow_RestAssured
├── src
│   ├── main
│   └── test
├── build.gradle
├── settings.gradle
├── gradlew
├── gradlew.bat
├── build
│   ├── allure-results
│   ├── reports
│   ├── test-results
└── README.md

⚙️ Installation and Setup

🔧 Prerequisites

Java JDK 17+: Download

Gradle: Download

Git: Download

📥 Steps

Clone the repository:

git clone https://github.com/MicusHub/FoodNow_RestAssured.git

Navigate to the project directory:

cd FoodNow_RestAssured

Build the project and install dependencies:

./gradlew build  # For Unix/macOS
gradlew build  # For Windows

Run the tests:

./gradlew test  # For Unix/macOS
gradlew test  # For Windows

🖥️ Running Tests

To execute API tests using REST Assured and TestNG:

./gradlew test

For generating an Allure report:

./gradlew allureServe

The report will be available at http://localhost:5050.

📝 Sample Test Case

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UserTests {
@Test
public void testGetUserDetails() {
Response response = given()
.baseUri("https://api.foodnow.com")
.when()
.get("/users/1")
.then()
.statusCode(200)
.extract().response();

        String username = response.jsonPath().getString("username");
        Assert.assertEquals(username, "testUser");
    }
}

🚀 CI/CD Integration

To automate test execution, integrate the project into Jenkins or GitHub Actions:

Add ./gradlew test as a test execution step.

Publish Allure Reports as artifacts for test results visualization.

🛑 Known Issues and Limitations

Some API endpoints may require authentication tokens.

Rate limiting on the API server may impact testing.

## 📄 Documentation

* [REST Assured](./screenshots/wn63-89-1.png)
* [Postman Test Run](https://drive.google.com/file/d/1V8IPwh7pYfbmQC2rEsfBjDsKVhlLnyFo/view?usp=sharing)
* [Swagger](https://oyster-app-hck73.ondigitalocean.app/api/swagger-ui/index.html#/)
* [Database Schema](https://drawsql.app/teams/vera-team/diagrams/order-food)

## 📜 License

This project is licensed under the MIT License — see [LICENSE](https://opensource.org/license/mit) for details.
