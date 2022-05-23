package data;

import model.User;
import org.apache.commons.lang3.RandomStringUtils;

public class DataGenerator {

    public static User generateNewUser() {
        String userEmail = RandomStringUtils.randomAlphabetic(10);
        String userPassword = RandomStringUtils.randomAlphabetic(10);
        String userName = RandomStringUtils.randomAlphabetic(10);
        return new User(userEmail, userPassword, userName);

    }
}
