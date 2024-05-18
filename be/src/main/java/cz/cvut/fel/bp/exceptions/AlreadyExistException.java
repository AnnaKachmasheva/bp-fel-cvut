package cz.cvut.fel.bp.exceptions;

public class AlreadyExistException extends CustomException {

    public AlreadyExistException(String errorCode, String message) {
        super(errorCode, message);
    }

}
