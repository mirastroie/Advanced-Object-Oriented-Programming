package util;

import entities.User;
import util.MyException;

public interface Auth {
    public void signIn() throws MyException;
    public void register(User user) throws MyException;
}
