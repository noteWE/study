package aven.study.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class StudyException extends RuntimeException {
    public StudyException() {
    }

    public StudyException(String message) {
        super(message);
    }

    public StudyException(String message, Throwable cause) {
        super(message, cause);
    }
}
