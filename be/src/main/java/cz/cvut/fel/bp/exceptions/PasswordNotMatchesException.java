package cz.cvut.fel.bp.exceptions;

public class PasswordNotMatchesException extends CustomException {

    public PasswordNotMatchesException(String errorCode, String message) {
        super(errorCode, message);
    }

}
