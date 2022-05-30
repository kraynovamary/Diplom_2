import data.DataGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.OrderCreateRequest;
import model.OrderResponse;
import model.UserResponse;
import org.junit.Test;
import rest.BurgerRestUrl;

import static org.junit.Assert.*;

public class CreateOrderTest extends MainTest{

    @Test
    @DisplayName("Create new order with ingredients and with user authorization")
    @Description("Basic success test for /api/orders endpoint")
    public void newOrderWithIngredientsUserAuth(){
        Response responseAuthorization = restBurgerSend.postRequestAndReturnResponse(user, BurgerRestUrl.AUTH_USER);
        UserResponse userResponseAuthorization = responseAuthorization.getBody().as(UserResponse.class);

        OrderCreateRequest orderRequest = DataGenerator.generateNewOrder();
        Response responseNewOrder = restBurgerSend.postRequestAndReturnResponse(orderRequest, BurgerRestUrl.ORDER, userResponseAuthorization.getAccessToken());
        OrderResponse orderResponse = responseNewOrder.getBody().as(OrderResponse.class);

        assertEquals(200, responseNewOrder.getStatusCode());
        assertTrue(orderResponse.getSuccess());
        assertFalse(orderResponse.getName().isEmpty());
        assertNotNull(orderResponse.getOrder().getNumber());
    }

    @Test
    @DisplayName("Create new order without ingredients and with user authorization")
    @Description("Basic test for /api/orders endpoint")
    public void newOrderWithoutIngredientsUserAuthorization(){

        Response responseAuthorization = restBurgerSend.postRequestAndReturnResponse(user, BurgerRestUrl.AUTH_USER);
        UserResponse userResponseAuthorization = responseAuthorization.getBody().as(UserResponse.class);

        OrderCreateRequest orderRequest = new OrderCreateRequest();
        Response responseNewOrder = restBurgerSend.postRequestAndReturnResponse(orderRequest, BurgerRestUrl.ORDER, userResponseAuthorization.getAccessToken());
        OrderResponse orderResponse = responseNewOrder.getBody().as(OrderResponse.class);

        assertEquals(400, responseNewOrder.getStatusCode());
        assertFalse(orderResponse.getSuccess());
        assertEquals("Ingredient ids must be provided", orderResponse.getMessage());
    }

    @Test
    @DisplayName("Create new order with incorrect ingredients and with user authorization")
    @Description("Basic test for /api/orders endpoint")
    public void newOrderNotCorrectedIngredientsUserAuthorization(){

        Response responseAuthorization = restBurgerSend.postRequestAndReturnResponse(user, BurgerRestUrl.AUTH_USER);
        UserResponse userResponseAuthorization = responseAuthorization.getBody().as(UserResponse.class);

        OrderCreateRequest orderRequest = new OrderCreateRequest(new String[]{"test123"});
        Response responseNewOrder = restBurgerSend.postRequestAndReturnResponse(orderRequest, BurgerRestUrl.ORDER, userResponseAuthorization.getAccessToken());

        assertEquals(500, responseNewOrder.getStatusCode());
    }

    @Test
    @DisplayName("Create new order with ingredients and with no user authorization")
    @Description("Basic test for /api/orders endpoint")
    public void newOrderWithIngredientsUserNotAuthorizationTest(){

        OrderCreateRequest orderRequest = DataGenerator.generateNewOrder();
        Response responseNewOrder = restBurgerSend.postRequestAndReturnResponse(orderRequest, BurgerRestUrl.ORDER);
        OrderResponse orderResponse = responseNewOrder.getBody().as(OrderResponse.class);

        assertEquals(401, responseNewOrder.getStatusCode());
        assertFalse(orderResponse.getSuccess());
        assertEquals("You should be authorised", orderResponse.getMessage());
    }
}
