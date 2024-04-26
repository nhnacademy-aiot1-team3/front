package live.databo3.front.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping(value = "/error")
    public String error(HttpServletRequest request) {
        Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        String codeNumber = null;
        if (statusCode != null) {
            int status = (int) statusCode;
            if (status == HttpStatus.FORBIDDEN.value()) {
                codeNumber = "403";
            }
            else if (status == HttpStatus.NOT_FOUND.value()) {
                codeNumber = "404";

            } else if (status == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                codeNumber = "500";
            }
        }
        return "/error/"+codeNumber;
    }
}
