import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.User;
import model.UserResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import rest.BurgerRestUrl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(Parameterized.class)
public class CreateUserWithoutRequiredFieldsTest extends MainTest{
    private User user;

    public CreateUserWithoutRequiredFieldsTest(User user){
        this.user = user;
    }

    @Parameterized.Parameters
    public static Object[][] getUserData(){
        return new Object[][]{
                {new User(null, RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10))},
                {new User(RandomStringUtils.randomAlphabetic(10),null, RandomStringUtils.randomAlphabetic(10))},
                {new User(RandomStringUtils.randomAlphabetic(10),RandomStringUtils.randomAlphabetic(10), null)},
                {new User(null,null, null)},
        };
    }

    @Test
    @DisplayName("Check creation user without required fields")
    @Description("Creating user without required fields for /api/auth/register")
    public void checkUserWithoutRequiredFields(){
        Response responseRegistrationUser = restBurgerSend.postRequestAndReturnResponse(user, BurgerRestUrl.CREATE_USER);
        UserResponse userResponse = responseRegistrationUser.getBody().as(UserResponse.class);

        assertEquals(403, responseRegistrationUser.getStatusCode());
        assertFalse(userResponse.isSuccess());
        assertEquals("Email, password and name are required fields", userResponse.getMessage());

    }
}
