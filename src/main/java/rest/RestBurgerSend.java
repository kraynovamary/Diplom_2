package rest;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RestBurgerSend extends RestAssuredClient {

    @Step
    public <T> Response postRequestAndReturnResponse(T requestBody, BurgerRestUrl url) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(requestBody)
                .when()
                .post(url.getId());
    }

    @Step
    public <T> Response postRequestAndReturnResponse(T requestBody, BurgerRestUrl url, String token) {

        return given()
                .spec(getBaseSpec())
                .header("authorization", token)
                .and()
                .body(requestBody)
                .when()
                .post(url.getId());
    }

    @Step
    public <T> Response patchRequestAndReturnResponse(T requestBody, BurgerRestUrl url, String token) {

        return given()
                .spec(getBaseSpec())
                .header("authorization", token)
                .and()
                .body(requestBody)
                .when()
                .patch(url.getId());
    }

    @Step
    public Response getRequestAndReturnResponse(BurgerRestUrl url, String token) {

        return given()
                .spec(getBaseSpec())
                .header("authorization", token)
                .when()
                .get(url.getId());
    }

    @Step
    public Response getRequestAndReturnResponse(BurgerRestUrl url) {

        return given()
                .spec(getBaseSpec())
                .when()
                .get(url.getId());
    }

    @Step
    public <T> Response deleteUserByIdAndReturnResponse(T requestBody, int id, BurgerRestUrl url) {

        return given()
                .spec(getBaseSpec())
                .and()
                .body(requestBody)
                .when()
                .delete(url.getId() + id);
    }
}
