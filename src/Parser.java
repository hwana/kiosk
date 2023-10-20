import exception.BadInputException;

import java.util.regex.Pattern;

public class Parser {

    public static void parseNum(String number, String regex) throws Exception {
        // 구현 1.
        if (!Pattern.matches(regex, number)) {
            throw new BadInputException();
        }
    }
}
