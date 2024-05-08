package live.databo3.front.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@ControllerAdvice
public class CustomExceptionAdvice {

    @ExceptionHandler(
            value = {
                    ResponseStatusException.class
            }
    )
    public String responseStatusExceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return "errors/403";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String noHandlerFoundExceptionHandler(NoHandlerFoundException e) {
        return "errors/404";
    }
}
