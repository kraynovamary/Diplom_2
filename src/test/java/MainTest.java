import data.DataGenerator;
import io.restassured.response.Response;
import model.User;
import org.junit.Before;
import rest.BurgerRestUrl;
import rest.RestBurgerSend;

public class MainTest {
    public RestBurgerSend restBurgerSend;
    public User user;
    public Response responseRegistration;

    @Before
    public void init(){
        restBurgerSend = new RestBurgerSend();
        user = DataGenerator.generateNewUser();
        responseRegistration = restBurgerSend.postRequestAndReturnResponse(user, BurgerRestUrl.CREATE_USER);
    }
}
