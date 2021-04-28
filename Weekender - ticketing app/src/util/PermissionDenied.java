package util;

public class PermissionDenied extends MyException{
    public PermissionDenied()
    {
        super("Permission denied! Can't go there!");
    }
}
