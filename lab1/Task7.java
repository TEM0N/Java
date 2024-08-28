package lab1;

import java.util.StringTokenizer;

public class Task7 {
    public static String deleteSinglesAndSpaces(String string) throws EmptyStringException {
        if (string.trim().isEmpty()) {
            throw new EmptyStringException("The source line should not be empty");
        }

        StringTokenizer str = new StringTokenizer(string, " ", false);
        StringBuilder editedString = new StringBuilder();
        StringBuilder token;
        boolean isAlpha;

        while (str.hasMoreTokens()) {
            token = new StringBuilder(str.nextToken());
            isAlpha = token.chars().allMatch(Character::isAlphabetic);
            if (token.length() != 1 || !isAlpha) {
                editedString.append(token).append(" ");
            }
        }
        return editedString.toString().trim();
    }

    public static void run(String str) {
        try {
            System.out.println(deleteSinglesAndSpaces(str));
        } catch (EmptyStringException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }
}