package data;

import model.OrderCreateRequest;
import model.User;
import org.apache.commons.lang3.RandomStringUtils;

public class DataGenerator {

    public static User generateNewUser() {
        String userEmail = (RandomStringUtils.randomAlphabetic(10) + "@ya.ru").toLowerCase();
        String userPassword = RandomStringUtils.randomAlphabetic(10);
        String userName = RandomStringUtils.randomAlphabetic(10);
        return new User(userEmail, userPassword, userName);
    }

    public static OrderCreateRequest generateNewOrder() {
        String[] ingredients = {"61c0c5a71d1f82001bdaaa6d","61c0c5a71d1f82001bdaaa6f"};
        return new OrderCreateRequest(ingredients);
    }
}
