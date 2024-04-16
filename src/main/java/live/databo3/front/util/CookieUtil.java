package live.databo3.front.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

/**
 * Cookie 관련된 유틸 클래스
 *
 * @author 양현성
 */
public class CookieUtil {
    public static Cookie findCookie(HttpServletRequest request,String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) {
            return null;
        }

        return Arrays.stream(cookies)
                .filter(cookie -> cookieName.equalsIgnoreCase(cookie.getName()))
                .findAny()
                .orElse(null);
    }

}
