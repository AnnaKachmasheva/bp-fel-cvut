package cz.cvut.fel.bp.exceptions.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author annak
 * @since 2024-03-24
 */
@Data
public class ErrorDetails {

    private String errorCode;
    private String message;
    private String timestamp;

    public ErrorDetails(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

}
