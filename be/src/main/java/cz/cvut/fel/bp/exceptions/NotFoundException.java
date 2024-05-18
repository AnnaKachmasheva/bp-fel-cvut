package cz.cvut.fel.bp.exceptions;

import lombok.Getter;

@Getter
public class NotFoundException extends CustomException {

    public NotFoundException(String errorCode, String message) {
        super(errorCode, message);
    }

}
