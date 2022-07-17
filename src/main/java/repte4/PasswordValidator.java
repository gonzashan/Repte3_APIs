package repte4;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

    //  Password requirements: lowercase char + uppercase char + symbol < 6 char long
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{6,20}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isValid(final String password) {

        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }


}

