import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.UserResponse;
import org.junit.Test;
import rest.BurgerRestUrl;

import static org.junit.Assert.*;

public class CreateUserTest extends MainTest{

    @Test
    @DisplayName("Check creation new user")
    @Description ("Success creating new user for /api/auth/register endpoint")

    public void createNewUser() {
        UserResponse userResponse = responseRegistration.getBody().as(UserResponse.class);

        assertEquals(200, responseRegistration.getStatusCode());
        assertTrue(userResponse.isSuccess());
        assertEquals(user.getEmail(), userResponse.getUser().getEmail());
        assertEquals(user.getName(), userResponse.getUser().getName());
        assertNull(userResponse.getUser().getPassword());
        assertNotNull(userResponse.getAccessToken());
        assertNotNull(userResponse.getRefreshToken());

    }

    @Test
    @DisplayName("Check creation of existing user")
    @Description ("Creating already existing user for /api/auth/register endpoint")

    public void createExistingUser(){
        Response responseCreationUser = restBurgerSend.postRequestAndReturnResponse(user, BurgerRestUrl.CREATE_USER);
        UserResponse userResponse = responseCreationUser.getBody().as(UserResponse.class);
        assertEquals(403, responseCreationUser.getStatusCode());
        assertFalse(userResponse.isSuccess());
        assertEquals("User already exists", userResponse.getMessage());

    }
}
