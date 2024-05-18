package cz.cvut.fel.bp.exceptions;

import lombok.Getter;

/**
 * @author annak
 * @since 2024-03-24
 */
@Getter
public abstract class CustomException extends RuntimeException {

    private final String errorCode;


    public CustomException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

}
