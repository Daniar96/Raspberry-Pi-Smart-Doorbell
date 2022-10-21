import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.*;

import static com.group17.server.SecurityFunctions.*;
import static com.group17.server.SecurityFunctions.getRandomString;

class SecurityTest {


    String plainPassword;


    @BeforeEach
    void init() {
        plainPassword = getRandomString();
    }

    @Test
    void randomStringAlwaysRandom() {
        String[] rdStrings = new String[1000];
        for (int i = 0; i < rdStrings.length; i++) {
            byte[] hashedPassword = Objects.requireNonNull(hashSaltFromPassword(plainPassword))[0];
            rdStrings[i] = new String(hashedPassword);
        }
        Assertions.assertFalse(hasDuplicate(rdStrings));
    }

    /**
     * Test that for one plain passwords there won't be a duplicate hashed password in 1000 tries
     */
    @Test
    void passwordAlwaysRandom() {
        String[] passwords = new String[1000];
        for (int i = 0; i < passwords.length; i++) {
            byte[] hashedPassword = Objects.requireNonNull(hashSaltFromPassword(plainPassword))[0];
            passwords[i] = new String(hashedPassword);
        }

        Assertions.assertFalse(hasDuplicate(passwords));
    }

    boolean hasDuplicate(String[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if ((array[i].equals(array[j])) && (i != j)) {
                    return true;
                }
            }
        }
        return false;
    }


}