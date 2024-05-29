package common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateData {
    private Matcher matcher;
    private final String REGEX_EMAIL = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    public boolean validateEmail(String regex){
        matcher = Pattern.compile(REGEX_EMAIL).matcher(regex.toString());
        return matcher.matches();
    }
}
