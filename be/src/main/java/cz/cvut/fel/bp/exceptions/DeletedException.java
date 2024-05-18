package cz.cvut.fel.bp.exceptions;

public class DeletedException extends CustomException {

    public DeletedException(String errorCode, String message) {
        super(errorCode, message);
    }

}
