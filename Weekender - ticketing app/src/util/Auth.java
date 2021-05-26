package util;

import entities.User;
import util.MyException;

public interface Auth {
    void signIn() throws MyException;
    void register(User user) throws MyException;
}
