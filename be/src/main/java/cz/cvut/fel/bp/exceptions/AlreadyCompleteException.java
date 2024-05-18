package cz.cvut.fel.bp.exceptions;

public class AlreadyCompleteException extends CustomException {

    public AlreadyCompleteException(String errorCode, String message) {
        super(errorCode, message);
    }

}
