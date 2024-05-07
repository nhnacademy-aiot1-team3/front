package live.databo3.front.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

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
        return "/errors/403";
    }
}
