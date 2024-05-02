package TuniWonders.entities;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordManager {

    // Method to hash a password
    public static String hashPassword(String plainTextPassword) {
        String hashedPassword = BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
        return hashedPassword;
    }

    // Method to verify a password
    public static boolean verifyPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }


}
