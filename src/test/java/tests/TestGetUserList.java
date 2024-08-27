package tests;
import core.UnsuccessfulGetResponse;
import core.UserData;
import core.UserListData;
//import io.qameta.allure.Description;
//import io.qameta.allure.Epic;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static io.restassured.RestAssured.given;
public class TestGetUserList {

    private static String URL_GENDER = "https://hr-challenge.dev.tapyou.com/api/test/users";

    private static String URL_BY_ID = "https://hr-challenge.dev.tapyou.com/api/test/user";

    @DisplayName("Тест на вытаскивание списка юзеров по любому гендеру")
    @ParameterizedTest
    @ValueSource(strings = {"any", "male", "female"})
    public void getListAnyTest(String gender) {

        Response response = given()
                .queryParam("gender", gender)
                .contentType(ContentType.JSON)
                .get(URL_GENDER);

        Assertions.assertEquals(200, response.statusCode());
        List<UserListData> users = response
                .then()
                .log()
                .all()
                .extract()
                .jsonPath()
                .getList("data", UserListData.class);
        users.stream().map(UserListData::isSuccess).forEach(Assertions::assertTrue);
    }

    @Test
    @DisplayName("Тест на вытаскивание пользователя без обязательного параметра")
    public void getNoParameterTest() {

        Response response = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL_GENDER);

        Assertions.assertEquals(400, response.statusCode());
        UnsuccessfulGetResponse unsuccessfulGetResponse = response
                .then()
                .log()
                .all()
                .extract()
                .as(UnsuccessfulGetResponse.class);
        Assertions.assertNotNull(unsuccessfulGetResponse.getError());
    }

    @DisplayName("Тест на вытаскивание списка юзеров с невалидным параметром")
    @ParameterizedTest
    @ValueSource(strings = {"an", "ma9e", "999"})
    public void getListInvalidParameterTest(String gender) {

        Response response = given()
                .queryParam("gender", gender)
                .contentType(ContentType.JSON)
                .get(URL_GENDER);

        Assertions.assertEquals(500, response.statusCode());
        UnsuccessfulGetResponse unsuccessfulGetResponse = response.then().log().all().extract().as(UnsuccessfulGetResponse.class);
        Assertions.assertNotNull(unsuccessfulGetResponse.getError());
    }


    @DisplayName("Тест на вытаскивание пользователей по айди")
    @ParameterizedTest
    @ValueSource(ints = {0, 5, 10, 15, 16, 33, 94, 212, 300, 501, 502, 503, 911})
    public void getUserByIdTest(int id) {
        Response response = given()
                .pathParam("id", id)
                .when()
                .contentType(ContentType.JSON)
                .get(URL_BY_ID + "/{id}");

        Assertions.assertEquals(200, response.statusCode(), "Unexpected status code");

        UserData userData = response
                .then()
                .log()
                .all()
                .extract()
                .as(UserData.class);

        Assertions.assertTrue(userData.isSuccess(), "isSuccess should be true for all ids");
        Assertions.assertEquals(0, userData.getErrorCode(), "errorCode should be 0 for all ids");
        Assertions.assertNull(userData.getErrorMessage(), "errorMessage should be null for all ids");

        if (userData.getUser() == null) {
            Assertions.assertNull(userData.getUser(), "User is null, but isSuccess is true. This is a valid scenario for some ids.");
        } else {
            Assertions.assertNotNull(userData.getUser(), "User should not be null when present");
            Assertions.assertEquals(id, userData.getUser().getId(), "User ID should match the requested ID");
            validateUserFields(userData.getUser());
        }
    }


    @Test
    @DisplayName("Тест на проверку пустого id")
    public void getUserNotFoundTest() {
        Response response = given().when().contentType(ContentType.JSON).get(URL_BY_ID);
        Assertions.assertEquals(404, response.statusCode());
        UnsuccessfulGetResponse unsuccessfulGetResponse = response
                .then()
                .log()
                .all()
                .extract()
                .as(UnsuccessfulGetResponse.class);
        Assertions.assertNotNull(unsuccessfulGetResponse.getError());
    }

    @DisplayName("Тест на проверку юзера с айди 0")
    @ParameterizedTest
    @ValueSource(ints = {0})
    public void getMissedUserTest(int id) {
        Response response = given()
                .pathParam("id", id)
                .when()
                .contentType(ContentType.JSON)
                .get(URL_BY_ID + "/{id}");

        Assertions.assertEquals(500, response.statusCode(), "Unexpected status code");

        UnsuccessfulGetResponse unsuccessfulGetResponse = response
                .then()
                .log()
                .all()
                .extract()
                .as(UnsuccessfulGetResponse.class);

        Assertions.assertNotNull(unsuccessfulGetResponse.getError());
        Assertions.assertNotNull(unsuccessfulGetResponse.getError());
    }

    private void validateUserFields(UserData.User user) {
        Assertions.assertNotNull(user.getName(), "User name should not be null");
        Assertions.assertNotNull(user.getGender(), "User gender should not be null");
        Assertions.assertTrue(user.getAge() > 0, "User age should be positive");
        Assertions.assertNotNull(user.getCity(), "User city should not be null");
        Assertions.assertNotNull(user.getRegistrationDate(), "User registration date should not be null");
    }
}
