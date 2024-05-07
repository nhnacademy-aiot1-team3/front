package live.databo3.front.auth.exception;

public class MissingRefreshTokenException extends RuntimeException{
    private static final String MESSAGE = "리프레시 토큰이 존재 하지 않습니다.";
    public MissingRefreshTokenException() {
        super(MESSAGE);
    }
}
