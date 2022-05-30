import data.DataGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.OrderCreateRequest;
import model.OrderGetResponse;
import model.OrderResponse;
import model.UserResponse;
import org.junit.Test;
import rest.BurgerRestUrl;

import static org.junit.Assert.*;

public class GetOrderTest extends MainTest{

    @Test
    @DisplayName("Get orders for auth user without orders")
    @Description("Basic success test for /api/orders endpoint")
    public void getOrderWithNoOrderWithUserAuth(){

        Response responseAuthorization = restBurgerSend.postRequestAndReturnResponse(user, BurgerRestUrl.AUTH_USER);
        UserResponse userResponseAuthorization = responseAuthorization.getBody().as(UserResponse.class);

        Response responseOrder = restBurgerSend.getRequestAndReturnResponse(BurgerRestUrl.ORDER, userResponseAuthorization.getAccessToken());
        OrderResponse orderResponse = responseOrder.getBody().as(OrderResponse.class);

        assertEquals(200, responseOrder.getStatusCode());
        assertTrue(orderResponse.getSuccess());
        assertTrue(orderResponse.getOrders().isEmpty());
    }

    @Test
    @DisplayName("Get orders for auth user with orders")
    @Description("Basic success test for /api/orders endpoint")
    public void getOrderWithUserAuth(){

        Response responseAuthorization = restBurgerSend.postRequestAndReturnResponse(user, BurgerRestUrl.AUTH_USER);
        UserResponse userResponseAuthorization = responseAuthorization.getBody().as(UserResponse.class);

        OrderCreateRequest orderRequest = DataGenerator.generateNewOrder();
        restBurgerSend.postRequestAndReturnResponse(orderRequest, BurgerRestUrl.ORDER, userResponseAuthorization.getAccessToken());

        Response responseOrder = restBurgerSend.getRequestAndReturnResponse(BurgerRestUrl.ORDER, userResponseAuthorization.getAccessToken());
        OrderGetResponse orderResponse = responseOrder.getBody().as(OrderGetResponse.class);

        assertEquals(200, responseOrder.getStatusCode());
        assertTrue(orderResponse.getSuccess());
        assertEquals(1, orderResponse.getOrders().size());
    }

    @Test
    @DisplayName("Get orders for not auth user")
    @Description("Basic success test for /api/orders endpoint")
    public void getOrderWithUserNotAuth(){

        Response response = restBurgerSend.getRequestAndReturnResponse(BurgerRestUrl.ORDER);

        OrderGetResponse orderResponse = response.getBody().as(OrderGetResponse.class);

        assertEquals(401, response.getStatusCode());
        assertFalse(orderResponse.getSuccess());
        assertEquals("You should be authorised", orderResponse.getMessage());
    }
}
