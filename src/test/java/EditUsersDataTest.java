import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.User;
import model.UserResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import rest.BurgerRestUrl;

import static org.junit.Assert.*;

public class EditUsersDataTest extends MainTest{
    private User userEdit;

    @Test
    @DisplayName("Check edit user data with auth")
    @Description("Basic success test for /api/auth/user endpoint")
    public void editUsersDataWithAuth(){

        Response responseAuthorization = restBurgerSend.postRequestAndReturnResponse(user, BurgerRestUrl.AUTH_USER);
        UserResponse userResponseAuthorization = responseAuthorization.getBody().as(UserResponse.class);

        userEdit = new User(RandomStringUtils.randomAlphabetic(10).toLowerCase(), RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10));
        Response responseEditUser = restBurgerSend.patchRequestAndReturnResponse(userEdit, BurgerRestUrl.EDIT_DATA_USER, userResponseAuthorization.getAccessToken());

        UserResponse userResponse = responseEditUser.getBody().as(UserResponse.class);

        assertEquals(200, responseEditUser.getStatusCode());
        assertTrue(userResponse.isSuccess());
        assertEquals(userEdit.getEmail(), userResponse.getUser().getEmail());
        assertEquals(userEdit.getName(), userResponse.getUser().getName());
        assertNull(userResponse.getUser().getPassword());

        Response responseDataUser = restBurgerSend.getRequestAndReturnResponse(BurgerRestUrl.EDIT_DATA_USER, userResponseAuthorization.getAccessToken());
        UserResponse userEditResponse = responseEditUser.getBody().as(UserResponse.class);

        assertEquals(200, responseDataUser.getStatusCode());
        assertTrue(userEditResponse.isSuccess());
        assertEquals(userEdit.getEmail(), userEditResponse.getUser().getEmail());
        assertEquals(userEdit.getName(), userEditResponse.getUser().getName());
        assertNull(userEditResponse.getUser().getPassword());
    }

    @Test
    @DisplayName("Check edit users data with no auth")
    @Description("Edit data for /api/auth/user endpoint")
    public void editUsersDataWithNoAuth(){

        userEdit = new User(RandomStringUtils.randomAlphabetic(10).toLowerCase(), RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10));
        Response responseEditUser = restBurgerSend.patchRequestAndReturnResponse(userEdit, BurgerRestUrl.EDIT_DATA_USER, "");

        UserResponse userResponse = responseEditUser.getBody().as(UserResponse.class);

        assertEquals(401, responseEditUser.getStatusCode());
        assertFalse(userResponse.isSuccess());
        assertEquals("You should be authorised", userResponse.getMessage());
    }
}
