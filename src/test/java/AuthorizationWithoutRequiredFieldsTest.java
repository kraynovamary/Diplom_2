import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.User;
import model.UserResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import rest.BurgerRestUrl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(Parameterized.class)
public class AuthorizationWithoutRequiredFieldsTest extends MainTest{
    private User user;

    public AuthorizationWithoutRequiredFieldsTest(User user){
        this.user = user;
    }

    @Parameterized.Parameters
    public static Object[][] getUserData(){
        return new Object[][]{
                {new User(null, RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10))},
                {new User(RandomStringUtils.randomAlphabetic(10),null, RandomStringUtils.randomAlphabetic(10))},
                {new User(null,null, null)},
        };
    }

    @Test
    @DisplayName("Auth with incorrect login or pass")
    @Description("Error test for /api/auth/login endpoint")
    public void authorizationWithoutRequiredFields(){
        Response responseAuthorizationUser = restBurgerSend.postRequestAndReturnResponse(user, BurgerRestUrl.AUTH_USER);
        UserResponse userResponse = responseAuthorizationUser.getBody().as(UserResponse.class);
        assertEquals(401, responseAuthorizationUser.getStatusCode());
        assertFalse(userResponse.isSuccess());
        assertEquals("email or password are incorrect", userResponse.getMessage());
    }
}
