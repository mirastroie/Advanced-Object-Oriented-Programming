package util;

public class UniqueUserEmails extends MyException{
    public UniqueUserEmails(){
        super("Two users can't have the same email address.");
    }
}
