package cz.cvut.fel.bp.exceptions;

public class PermissionDeniedException extends CustomException {

    public PermissionDeniedException(String errorCode, String message) {
        super(errorCode, message);
    }

}
