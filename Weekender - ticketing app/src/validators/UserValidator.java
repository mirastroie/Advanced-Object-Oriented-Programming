package validators;

import util.Error;

public class UserValidator {
    public boolean validateEmail(String email)
    {
        String regex = "([A-Za-z0-9]+(_|.|-)*)+@[a-z]+\\.[a-z]+";
        return email.matches(regex);
    }
    public boolean validatePassword(String password)
    {
        return password.matches("[A-Za-z0-9]{7,24}");
    }
    public String validate(String password, String email){
        StringBuilder errors = new StringBuilder();
        if(!validateEmail(email))
            errors.append(Error.INVALID_EMAIL);
        if(!validatePassword(password))
            errors.append(Error.INVALID_PASSWORD);
        return errors.toString();
    }
}
