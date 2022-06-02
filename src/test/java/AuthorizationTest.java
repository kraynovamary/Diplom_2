import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.UserResponse;
import org.junit.Before;
import org.junit.Test;
import rest.BurgerRestUrl;

import static org.junit.Assert.*;

public class AuthorizationTest extends MainTest{

    public Response responseAuthorization;

    @Before
    public void init(){
        super.init();
        responseAuthorization = restBurgerSend.postRequestAndReturnResponse(user, BurgerRestUrl.AUTH_USER);
    }

    @Test
    @DisplayName("Success authorization")
    @Description("Basic success test for /api/auth/login endpoint")
    public void correctUserAuth(){
        UserResponse userResponse = responseAuthorization.getBody().as(UserResponse.class);

        assertEquals(200, responseAuthorization.getStatusCode());
        assertTrue(userResponse.isSuccess());
        assertEquals(user.getEmail(), userResponse.getUser().getEmail());
        assertEquals(user.getName(), userResponse.getUser().getName());
        assertNull(userResponse.getUser().getPassword());
        assertNotNull(userResponse.getAccessToken());
        assertNotNull(userResponse.getRefreshToken());

    }
}
