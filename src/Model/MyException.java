package Model;

public class MyException extends Exception {
    private final String FULL_MESSAGE;

    public MyException(String _msg) {
        FULL_MESSAGE = _msg;
    }

    public String toString() {
        return FULL_MESSAGE;
    }
}
