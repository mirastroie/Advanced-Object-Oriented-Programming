package util;

public class NotFound extends MyException{
    public NotFound(String object){
        super("The " + object + " doesn't exist!");
    }
}
