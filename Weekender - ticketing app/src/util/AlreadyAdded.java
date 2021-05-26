package util;

public class AlreadyAdded extends MyException{
    public AlreadyAdded(String obj){
        super("The " + obj + " is already added!");
    }
}
