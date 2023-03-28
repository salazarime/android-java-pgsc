package devandroid.salazar.passwordgenerator;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class PasswordGeneratorService {
    private static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBER_CHARACTERS = "0123456789";
    private static final String SYMBOL_CHARACTERS = "@&$*+-_.,!?:;%#()[]{}\\/'\"^<>|~= ";

    private SecureRandom secureRandom = new SecureRandom();

    public List<String> generatePasswords(int lowercaseCount, int uppercaseCount, int numberCount, int symbolCount) {
        List<String> passwords = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            StringBuilder passwordBuilder = new StringBuilder();

            for (int j = 0; j < lowercaseCount; j++) {
                passwordBuilder.append(getRandomCharacter(LOWERCASE_CHARACTERS));
            }

            for (int j = 0; j < uppercaseCount; j++) {
                passwordBuilder.append(getRandomCharacter(UPPERCASE_CHARACTERS));
            }

            for (int j = 0; j < numberCount; j++) {
                passwordBuilder.append(getRandomCharacter(NUMBER_CHARACTERS));
            }

            for (int j = 0; j < symbolCount; j++) {
                passwordBuilder.append(getRandomCharacter(SYMBOL_CHARACTERS));
            }

            passwords.add(passwordBuilder.toString());
        }

        return passwords;
    }

    private char getRandomCharacter(String characters) {
        int randomIndex = secureRandom.nextInt(characters.length());
        return characters.charAt(randomIndex);
    }
}
