package util;

public class AlreadyRegisteredUser extends MyException{
    public AlreadyRegisteredUser(){
        super("The user is already registered.");
    }
}
