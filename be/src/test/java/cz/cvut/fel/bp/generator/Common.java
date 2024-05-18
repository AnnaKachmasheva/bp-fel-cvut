package cz.cvut.fel.bp.generator;

import java.time.LocalDate;
import java.util.Random;

/**
 * @author annak
 * @since 2024-03-16
 */
public class Common {

    private static Random random =  new Random();


    static Long generateRandomId() {
        return random.nextLong();
    }

    static String generateRandomEmail() {
        String firstName = generateRandomString(6).toLowerCase();
        String lastName = generateRandomString(6).toLowerCase();
        String domain = generateRandomString(6).toLowerCase() + ".com";
        return firstName + "." + lastName + "@" + domain;
    }

    static String generateRandomString(int length) {
        StringBuilder builder = new StringBuilder();
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < length; i++) {
            builder.append(characters.charAt(random.nextInt(characters.length())));
        }

        return builder.toString();
    }

    static boolean generateRandomBoolean() {
        return random.nextBoolean();
    }

    static LocalDate generateRandomDate() {
        return LocalDate.now().minusDays(random.nextInt());
    }
}
